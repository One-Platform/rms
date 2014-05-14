package com.sinosoft.one.rms.repositories;


import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.rms.model.UserGroup;

public interface GeRmsUserGroupRepository extends PagingAndSortingRepository<UserGroup, String>{
	
	//根据用户ID查询用户与组关系表ID
	@SQL("select userGropuId from GE_RMS_USERGROUP where userCode = ?1 and isValidate = '1'")
	List<String> findUserGroupIdByUserCode(String userCode);
	
	//删除相应的用户与组关系记录
	@SQL("delete from GE_RMS_USERGROUP where userGropuId in (?1)")
	void deleteUserPower(List<String> userGropuIds);
	
	@SQL("delete from GE_RMS_USERGROUP where groupid = ?1 and userCode= ?2 and USERPOWERID= (select USERPOWERID from GE_RMS_USERPOWER where userCode= ?2 and comCode= ?3)")
	void deleteGroupMember(String groupid ,String userCode,String comCode);
}
