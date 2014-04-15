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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * POJO类company
 */
@Entity
@Table(name = "GE_RMS_COMPANY")
public class Company  {
	private static final long serialVersionUID = 1L;

	/** 属性机构代码(ComCode) */
	private String comCode;

	/** 属性机构中文名称(ComCName) */
	private String comCName;

	/** 属性机构英文名称(ComEName) */
	private String comEName;

	/** 属性地址中文名称(AddressCName) */
	private String addressCName;

	/** 属性地址英文名称(AddressEName) */
	private String addressEName;

	/** 属性邮编(PostCode) */
	private String postCode;

	/** 属性电话(PhoneNumber) */
	private String phoneNumber;

	/** 属性传真(FaxNumber) */
	private String faxNumber;

	/** 属性上级机构代码(UpperComCode) */
	private String upperComCode;

	/** 属性归属保险公司名称(InsurerName) */
	private String insurerName;

	/** 属性机构类型(ComType) */
	private String comType;

	/** 属性经理(Manager) */
	private String manager;

	/** 属性会计(Accountant) */
	private String accountant;

	/** 属性备注(Remark) */
	private String remark;

	/** 属性最新机构代码(NewComCode) */
	private String newComCode;

	/** 属性效力状态(ValidStatus) */
	private String validStatus;

	/** 属性账户归属机构代码(AcntUnit) */
	private String acntUnit;

	/** 属性专项代码(ArticleCode) */
	private String articleCode;

	/** 属性标志字段(Flag) */
	private String flag;

	private String isinSured;
	
	
	/** 属性employes */
	private List<Employe> employes = new ArrayList<Employe>(0);

	/**
	 * 类company的默认构造方法
	 */
	public Company() {
	}

	/**
	 * 属性机构代码(ComCode)的getter方法
	 */
	@Id
	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性机构代码(ComCode)的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性机构中文名称(ComCName)的getter方法
	 */

	@Column(name = "COMCNAME")
	public String getComCName() {
		return this.comCName;
	}

	/**
	 * 属性机构中文名称(ComCName)的setter方法
	 */
	public void setComCName(String comCName) {
		this.comCName = comCName;
	}

	/**
	 * 属性机构英文名称(ComEName)的getter方法
	 */

	@Column(name = "COMENAME")
	public String getComEName() {
		return this.comEName;
	}

	/**
	 * 属性机构英文名称(ComEName)的setter方法
	 */
	public void setComEName(String comEName) {
		this.comEName = comEName;
	}

	/**
	 * 属性地址中文名称(AddressCName)的getter方法
	 */

	@Column(name = "ADDRESSCNAME")
	public String getAddressCName() {
		return this.addressCName;
	}

	/**
	 * 属性地址中文名称(AddressCName)的setter方法
	 */
	public void setAddressCName(String addressCName) {
		this.addressCName = addressCName;
	}

	/**
	 * 属性地址英文名称(AddressEName)的getter方法
	 */

	@Column(name = "ADDRESSENAME")
	public String getAddressEName() {
		return this.addressEName;
	}

	/**
	 * 属性地址英文名称(AddressEName)的setter方法
	 */
	public void setAddressEName(String addressEName) {
		this.addressEName = addressEName;
	}

	/**
	 * 属性邮编(PostCode)的getter方法
	 */

	@Column(name = "POSTCODE")
	public String getPostCode() {
		return this.postCode;
	}

	/**
	 * 属性邮编(PostCode)的setter方法
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * 属性电话(PhoneNumber)的getter方法
	 */

	@Column(name = "PHONENUMBER")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**
	 * 属性电话(PhoneNumber)的setter方法
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * 属性传真(FaxNumber)的getter方法
	 */

	@Column(name = "FAXNUMBER")
	public String getFaxNumber() {
		return this.faxNumber;
	}

	/**
	 * 属性传真(FaxNumber)的setter方法
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**
	 * 属性上级机构代码(UpperComCode)的getter方法
	 */

	@Column(name = "UPPERCOMCODE")
	public String getUpperComCode() {
		return this.upperComCode;
	}

	/**
	 * 属性上级机构代码(UpperComCode)的setter方法
	 */
	public void setUpperComCode(String upperComCode) {
		this.upperComCode = upperComCode;
	}

	/**
	 * 属性归属保险公司名称(InsurerName)的getter方法
	 */

	@Column(name = "INSURERNAME")
	public String getInsurerName() {
		return this.insurerName;
	}

	/**
	 * 属性归属保险公司名称(InsurerName)的setter方法
	 */
	public void setInsurerName(String insurerName) {
		this.insurerName = insurerName;
	}

	/**
	 * 属性机构类型(ComType)的getter方法
	 */

	@Column(name = "COMTYPE")
	public String getComType() {
		return this.comType;
	}

	/**
	 * 属性机构类型(ComType)的setter方法
	 */
	public void setComType(String comType) {
		this.comType = comType;
	}

	/**
	 * 属性经理(Manager)的getter方法
	 */

	@Column(name = "MANAGER")
	public String getManager() {
		return this.manager;
	}

	/**
	 * 属性经理(Manager)的setter方法
	 */
	public void setManager(String manager) {
		this.manager = manager;
	}

	/**
	 * 属性会计(Accountant)的getter方法
	 */

	@Column(name = "ACCOUNTANT")
	public String getAccountant() {
		return this.accountant;
	}

	/**
	 * 属性会计(Accountant)的setter方法
	 */
	public void setAccountant(String accountant) {
		this.accountant = accountant;
	}

	/**
	 * 属性备注(Remark)的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性备注(Remark)的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性最新机构代码(NewComCode)的getter方法
	 */

	@Column(name = "NEWCOMCODE")
	public String getNewComCode() {
		return this.newComCode;
	}

	/**
	 * 属性最新机构代码(NewComCode)的setter方法
	 */
	public void setNewComCode(String newComCode) {
		this.newComCode = newComCode;
	}

	/**
	 * 属性效力状态(ValidStatus)的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性效力状态(ValidStatus)的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性账户归属机构代码(AcntUnit)的getter方法
	 */

	@Column(name = "ACNTUNIT")
	public String getAcntUnit() {
		return this.acntUnit;
	}

	/**
	 * 属性账户归属机构代码(AcntUnit)的setter方法
	 */
	public void setAcntUnit(String acntUnit) {
		this.acntUnit = acntUnit;
	}

	/**
	 * 属性专项代码(ArticleCode)的getter方法
	 */

	@Column(name = "ARTICLECODE")
	public String getArticleCode() {
		return this.articleCode;
	}

	/**
	 * 属性专项代码(ArticleCode)的setter方法
	 */
	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	/**
	 * 属性标志字段(Flag)的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志字段(Flag)的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性是否可保字段(Flag)的getter方法
	 */
	@Column(name = "ISINSURED")
	public String getIsinSured() {
		return isinSured;
	}

	/**
	 * 属性是否可保字段(Flag)的setter方法
	 */
	public void setIsinSured(String isinSured) {
		this.isinSured = isinSured;
	}

	/**
	 * 属性employes的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "company")
	@XmlTransient
	@JSONField(serialize = false)
	public List<Employe> getEmployes() {
		return this.employes;
	}

	/**
	 * 属性employes的setter方法
	 */
	public void setEmployes(List<Employe> employes) {
		this.employes = employes;
	}



}
