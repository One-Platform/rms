package com.sinosoft.one.rms.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.rms.model.RoleTask;

public interface GeRmsRoleTaskRepository extends PagingAndSortingRepository<RoleTask, String>{

	//根据角色ID查询相应的授权ID
	@SQL("select taskAuthId from GE_RMS_ROLETASK where roleId = ?1")
	List<String> findTaskAuthIdByRoleId(String roleId);
	
}
