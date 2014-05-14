package com.sinosoft.one.rms.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.rms.model.Group;

public interface GeRmsGroupRepository extends PagingAndSortingRepository<Group, String>{
	
	
	//----------------------------------------//
//	@SQL("select * from GE_RMS_GROUP where comCode in(?1,'*')")
//	Page<Group> findGroup(String comCode ,Pageable pageable);
//	
//	@SQL("select * from GE_RMS_GROUP where comCode = ?1 and name like ?2")
//	Page<Group> findGroupByName(String comCode ,String name,Pageable pageable);
//	
//	@SQL("select * from GE_RMS_GROUP where name =?1 and comCode=?2 ")
//	Group findGroupIdbyName(String name,String comCode);
//	
//	//根据机构ID查询用户组
//	@SQL("select * from GE_RMS_GROUP where comCode in(?1,'*')")
//	List<Group> findGroupByComCode(String comCode);
//	
//	@SQL("select * from GE_RMS_GROUP where GROUPID =?1 and comCode=?2 ")
//	Group findGroupbyIdComCode(String groupid,String comCode);
	
	
	//---------------------------------------不使用默认用户组-------------------、、
	
	@SQL("select * from GE_RMS_GROUP ")
	Page<Group> findGroup(Pageable pageable);
	
	@SQL("select * from GE_RMS_GROUP where name like ?2")
	Page<Group> findGroupByName( String name,Pageable pageable);
	
	@SQL("select * from GE_RMS_GROUP where name =?1  ")
	Group findGroupIdbyName(String name );
	
	//根据机构ID查询用户组
	@SQL("select * from GE_RMS_GROUP  ")
	List<Group> findGroups( );
	
	
}
