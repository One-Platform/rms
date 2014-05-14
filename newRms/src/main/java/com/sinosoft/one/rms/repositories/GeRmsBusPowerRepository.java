package com.sinosoft.one.rms.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.rms.model.BusPower;

public interface GeRmsBusPowerRepository extends PagingAndSortingRepository<BusPower, String>{
	
	//根据用户权限ID和数据规则ID，查询人员数据权限ID
	@SQL("select busPowerId from GE_RMS_BUSPOWER where userPowerId = ?1 and dataRuleId in(?2) and isValidate = '1'")
	List<String> findBusPowerIdByUserPowerIdTaskId(String userPowerId,List<String> dataRuleIds);
	
	//根据用户权限ID，查询busPowerId
	@SQL("select busPowerId from GE_RMS_BUSPOWER where userPowerId = ?1 and isValidate = '1'")
	List<String> findBusPowerIdByUserPowerId(String userPowerId);
	
	//删除相应的记录，根据ID
	@SQL("delete from GE_RMS_BUSPOWER where busPowerId in (?1)")
	void deleteBusPower(List<String> userPowerIds);
	
	
	
}
