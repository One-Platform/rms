package com.sinosoft.one.rms.model;
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * POJO类dataRule
 */
@Entity
@Table(name = "GE_RMS_DATARULE")
public class DataRule implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性数据规则ID(DataRuleID) */
	private String dataRuleID;

	/** 属性描述(Des) */
	private String des;

	/** 属性规则(Rule) */
	private String rule;

	/** 属性是否有效(IsValidate) */
	private String isValidate;

	/** 属性busPowers */
	private List<BusPower> busPowers = new ArrayList<BusPower>(0);

	/** 属性busDataInfos */
	private List<BusDataInfo>busDataInfos=new ArrayList<BusDataInfo>();
	/**
	 * 类dataRule的默认构造方法
	 */
	public DataRule() {
	}

	/**
	 * 属性数据规则ID(DataRuleID)的getter方法
	 */
	@Id
	@Column(name = "DATARULEID")
	public String getDataRuleID() {
		return this.dataRuleID;
	}

	/**
	 * 属性数据规则ID(DataRuleID)的setter方法
	 */
	public void setDataRuleID(String dataRuleID) {
		this.dataRuleID = dataRuleID;
	}

	/**
	 * 属性描述(Desc)的getter方法
	 */

	@Column(name = "Des")
	public String getDes() {
		return this.des;
	}

	/**
	 * 属性描述(Desc)的setter方法
	 */
	public void setDes(String des) {
		this.des = des;
	}

	/**
	 * 属性规则(Rule)的getter方法
	 */

	@Column(name = "RULE")
	public String getRule() {
		return this.rule;
	}

	/**
	 * 属性规则(Rule)的setter方法
	 */
	public void setRule(String rule) {
		this.rule = rule;
	}

	/**
	 * 属性是否有效(IsValidate)的getter方法
	 */

	@Column(name = "ISVALIDATE")
	public String getIsValidate() {
		return this.isValidate;
	}

	/**
	 * 属性是否有效(IsValidate)的setter方法
	 */
	public void setIsValidate(String isValidate) {
		this.isValidate = isValidate;
	}

	/**
	 * 属性busPowers的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dataRule")
	@XmlTransient
	@JSONField(serialize=false)
	public List<BusPower> getBusPowers() {
		return this.busPowers;
	}

	/**
	 * 属性busPowers的setter方法
	 */
	public void setBusPowers(List<BusPower> busPowers) {
		this.busPowers = busPowers;
	}

	/**
	 * 属性busDataInfos的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "dataRule")
	public List<BusDataInfo> getBusDataInfos() {
		return busDataInfos;
	}

	/**
	 * 属性busDataInfos的setter方法
	 */
	public void setBusDataInfos(List<BusDataInfo> busDataInfos) {
		this.busDataInfos = busDataInfos;
	}

	
}
