package com.sinosoft.one.rms.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.rms.model.Role;

public interface GeRmsRoleRepository extends PagingAndSortingRepository<Role, String> {

	@SQL("select * from GE_RMS_ROLE where name like ?1")
	Page<Role> findRoleByName(String name, Pageable pageable);

	@SQL("select * from GE_RMS_ROLE where roleId in (select roleid from GE_RMS_ROLE_DESIGNATE where comCode= ?1 or comCode='*')")
	Page<Role> findRole(String comCode,Pageable pageable);
	
	
	@SQL("select * from GE_RMS_ROLE where roleId in (select roleid from GE_RMS_ROLE_DESIGNATE where comCode= ?1 or comCode='*') and name like ?2")
	Page<Role> findRoleByName(String comCode,String name,Pageable pageable);
	
	@SQL("select comCode from GE_RMS_ROLE_DESIGNATE where roleId = ?1")
	List<String> findRoleTypById(String roleId);
	
	@SQL("delete GE_RMS_ROLETASK t where t.roleID=?1 and t.taskAuthid in (select taskauthid from ge_rms_task_auth a where a.comCode='*' or a.comcode=?2)")
	void deleteRoleTask(String roleId,String comCode);
	
	@SQL("update ge_rms_role_designate t set  t.comcode=?1 where  t.comcode='*'  and t.roleid =?2")
	void updateRoleToDefault(String comCode,String roleId);
	
	@SQL("update ge_rms_role_designate t set t.comcode='*' where (t.comcode=?1 or t.comcode='*' ) and t.roleid =?2")
	void updateRoleToAll(String comCode,String roleId);
	
	@SQL("delete ge_rms_role_designate t where t.comcode!=?1 and t.roleid =?2")
	void deleteRoleDesignate(String comCode,String roleId);
}
