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
 * POJO类excPower
 */
@Entity
@Table(name = "GE_RMS_EXCPOWER")
public class ExcPower implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性除外权限ID(ExcPowerID) */
	private String excPowerID;

	/** 属性task */
	private Task task;

	/** 属性userPower */
	private UserPower userPower;

	/** 属性是否有效(IsValidate) */
	private String isValidate;

	/**
	 * 类excPower的默认构造方法
	 */
	public ExcPower() {
	}

	/**
	 * 属性除外权限ID(ExcPowerID)的getter方法
	 */
	@Id
	@Column(name = "EXCPOWERID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	public String getExcPowerID() {
		return this.excPowerID;
	}

	/**
	 * 属性除外权限ID(ExcPowerID)的setter方法
	 */
	public void setExcPowerID(String excPowerID) {
		this.excPowerID = excPowerID;
	}

	/**
	 * 属性task的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TASKID", nullable = false)
	public Task getTask() {
		return this.task;
	}

	/**
	 * 属性task的setter方法
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	/**
	 * 属性userPower的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POWERID", nullable = false)
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
