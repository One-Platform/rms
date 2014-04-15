package com.sinosoft.one.rms.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;



import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.rms.model.Task;

public interface GeRmsTaskRepository extends PagingAndSortingRepository<Task, String>{
	
	//根据功能ID查询父功能ID
	@SQL("select parentId from GE_RMS_TASK where taskId=?1 and isValidate='1'")
	String findParentIdByTaskId(String taskId);
	
	//根据功能ID查询功能对象
	@SQL("select * from GE_RMS_TASK where taskId in (?1) and isValidate='1'")
	List<Task>findTaskByTaskIds(List<String> taskIds);
	
	//根据功能ID查询功能对象
	@SQL("select * from GE_RMS_TASK where taskId in (?1) and isValidate='1' and sysFlag in('RMS',?2)")
	List<Task>findTaskByTaskIds(List<String> taskIds,String sysFlag);
	
	//根据powerId和comCode查出相应的功能
	@SQL("select * from GE_RMS_TASK where taskId not in(select taskId from GE_RMS_EXCPOWER where powerId = ?1) and taskId in(select taskId from GE_RMS_TASK_AUTH where comCode = ?2 or comCode = '*') and isValidate='1'")
	List<Task> findTaskByPowerIdComCode(String powerId , String comCode);
	
	
		
}
