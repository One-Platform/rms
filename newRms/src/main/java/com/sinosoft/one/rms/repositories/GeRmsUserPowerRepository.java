package com.sinosoft.one.rms.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.rms.model.UserPower;

public interface GeRmsUserPowerRepository extends PagingAndSortingRepository<UserPower, String>{
	
	//根据用户ID查询机构ID
	@SQL("select comCode from GE_RMS_USERPOWER where userCode = ?1 and isValidate = '1'")
	List<String> findComCodeByUserCode(String userCode);
	
	//根据用户ID和机构ID查询用户权限ID
	@SQL("select userpowerId from GE_RMS_USERPOWER where userCode = ?1 and comCode = ?2 and isValidate = '1'")
	String findIdByUserCodeComCode(String userCode ,String comCode);
	
	//根据userCode查询出用户权限ID
	@SQL("select userPowerId from GE_RMS_USERPOWER where userCode = ?1 and isValidate = '1'")
	List<String> findUserPowerIdByUserCode(String userCode);
		
	
	
}
