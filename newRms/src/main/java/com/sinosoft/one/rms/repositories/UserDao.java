package com.sinosoft.one.rms.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.rms.model.Employe;

public interface UserDao extends PagingAndSortingRepository<Employe, String>{
	
	@SQL("select * from GE_RMS_EMPLOYE where userCode = ?1")
	Employe findUserById(String userCode);
	
	//根据userCode查询User，模糊查询
	@SQL("select * from GE_RMS_EMPLOYE where userCode like ?1")
	Page<Employe> findUserByUserCode(String userCode,Pageable pageable);

	//根据comCode查询User，模糊查询
	@SQL("select * from GE_RMS_EMPLOYE where comCode like ?1")
	Page<Employe> findUserByComCode(String comCode,Pageable pageable);
	
	//根据机构ID和用户ID查询用户，模糊查询
	@SQL("select * from GE_RMS_EMPLOYE where comCode like ?1 and userCode like ?2")
	Page<Employe> findUserByComCodeUserCode(String comCode,String userCode,Pageable pageable);
	
	//根据用户ID查询机构ID  
	@Query(value = "select comcode from GE_RMS_EMPLOYE where userCode = ?1",nativeQuery = true)
	String findComCodeByUserCode(String userCode);
	
	@Query(" from Employe e where e.userCode like ?2 and e.userCode  in(select userCode from UserGroup where group.groupID=?1 )")
	Page<Employe> findGroupMember(String groupid,String userCode,Pageable pageable);
	
	@Query(" from Employe e where e.userCode like ?2 and e.userCode not in(select userCode from UserGroup where group.groupID=?1 )")
	Page<Employe> finduser(String groupid,String userCode,Pageable pageable);
}
