package com.sinosoft.one.rms.model;
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * POJO类userGroup
 */
@Entity
@Table(name = "GE_RMS_USERGROUP")
public class UserGroup implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性组用户ID(UserGropuID) */
	private String userGropuID;

	/** 属性group */
	private Group group;

	/** 属性userPower */
	private UserPower userPower;

	/** 属性员工代码(UserCode) */
	private String userCode;

	/** 属性是否有效(IsValidate) */
	private String isValidate;

	/**
	 * 类userGroup的默认构造方法
	 */
	public UserGroup() {
	}

	/**
	 * 属性组用户ID(UserGropuID)的getter方法
	 */
	@Id
	@Column(name = "USERGROPUID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	public String getUserGropuID() {
		return this.userGropuID;
	}

	/**
	 * 属性组用户ID(UserGropuID)的setter方法
	 */
	public void setUserGropuID(String userGropuID) {
		this.userGropuID = userGropuID;
	}

	/**
	 * 属性group的getter方法
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "GROUPID", nullable = false)
	public Group getGroup() {
		return this.group;
	}

	/**
	 * 属性group的setter方法
	 */
	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * 属性userPower的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERPOWERID", nullable = false)
	public UserPower getUserPower() {
		return this.userPower;
	}

	/**
	 * 属性userPower的setter方法
	 */
	public void setUserPower(UserPower userPower) {
		this.userPower = userPower;
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

}
