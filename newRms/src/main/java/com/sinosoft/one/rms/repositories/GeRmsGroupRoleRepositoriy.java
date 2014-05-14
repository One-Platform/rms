package com.sinosoft.one.rms.repositories;


import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.rms.model.GroupRole;

public interface GeRmsGroupRoleRepositoriy extends
		PagingAndSortingRepository<GroupRole, String> {

	//--------------------------------------//
	//删除不指派这个机构的的用户组角色数据
	@SQL("delete ge_rms_grouprole gr where gr.groupid  in (select groupid from ge_rms_group where comcode!=?1)")
	void deleteGroupRoleByComCode(String comCode);
	
	//根据用户组ID查询用户组角色ID
	@SQL("select roleId from GE_RMS_GROUPROLE where groupId = ?1")
	List<String> findRoleIdByGroupId(String groupId);
	
	@SQL("delete ge_rms_grouprole t where t.roleid in (?2) and t.groupid in(select groupid from ge_rms_group where comcode in (?1) )")
	void deleteGroupRolebyidsComCodes(List<String> comCodes,List<String> roleIds);
}
