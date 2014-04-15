package com.sinosoft.one.rms.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.rms.model.Company;

public interface CompanyDao extends PagingAndSortingRepository<Company, String>{
	
	//根据Uppercomcode查询出comCode集合
	@SQL("select comCode from GE_RMS_COMPANY where Uppercomcode = ?1")
	List<String> findComCodeByUppercomcode(String Uppercomcode);
	
	//查询出全部机构的ID
	@SQL("select comCode from GE_RMS_COMPANY")
	List<String> findComCodeAll();

}
