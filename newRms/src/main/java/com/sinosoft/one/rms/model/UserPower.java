package com.sinosoft.one.rms.model;
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

/**
 * POJO类userPower
 */
@Entity
@Table(name = "GE_RMS_USERPOWER")
public class UserPower implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性用户权限ID(UserPowerID) */
	private String userPowerID;

	/** 属性机构代码(ComCode) */
	private String comCode;

	/** 属性员工代码(UserCode) */
	private String userCode;

	/** 属性是否有效(IsValidate) */
	private String isValidate;

	/** 属性标志字段(Flag) */
	private String flag;

	/** 属性excPowers */
	private List<ExcPower> excPowers = new ArrayList<ExcPower>(0);

	/** 属性busPowers */
	private List<BusPower> busPowers = new ArrayList<BusPower>(0);

	/** 属性userGroups */
	private List<UserGroup> userGroups = new ArrayList<UserGroup>(0);

	/**
	 * 类userPower的默认构造方法
	 */
	public UserPower() {
	}

	/**
	 * 属性用户权限ID(UserPowerID)的getter方法
	 */
	@Id
	@Column(name = "USERPOWERID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	public String getUserPowerID() {
		return this.userPowerID;
	}

	/**
	 * 属性用户权限ID(UserPowerID)的setter方法
	 */
	public void setUserPowerID(String userPowerID) {
		this.userPowerID = userPowerID;
	}

	/**
	 * 属性机构代码(ComCode)的getter方法
	 */

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
	 * 属性员工代码(UserCode)的getter方法
	 */

	@Column(name = "USERCODE")
	public String getUserCode() {
		return this.userCode;
	}

	/**
	 * 属性员工代码(UserCode)的setter方法
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
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
	 * 属性excPowers的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userPower")
	@Fetch(FetchMode.SUBSELECT)
	public List<ExcPower> getExcPowers() {
		return this.excPowers;
	}

	/**
	 * 属性excPowers的setter方法
	 */
	public void setExcPowers(List<ExcPower> excPowers) {
		this.excPowers = excPowers;
	}

	/**
	 * 属性busPowers的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userPower")
	@Fetch(FetchMode.SUBSELECT)
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
	 * 属性userGroups的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userPower")
	@Fetch(FetchMode.SUBSELECT)
	public List<UserGroup> getUserGroups() {
		return this.userGroups;
	}

	/**
	 * 属性userGroups的setter方法
	 */
	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

}
