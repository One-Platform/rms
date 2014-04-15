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

import com.alibaba.fastjson.annotation.JSONField;

/**
 * POJO类roleTask
 */
@Entity
@Table(name = "GE_RMS_ROLETASK")
public class RoleTask implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性角色功能ID(RoleTaskID) */
	private String roleTaskID;

	/** 属性taskAuth */
	private TaskAuth taskAuth;

	/** 属性role */
	private Role role;

	/** 属性是否有效(IsValidate) */
	private String isValidate;

	/** 属性标志字段(Flag) */
	private String flag;

	/**
	 * 类roleTask的默认构造方法
	 */
	public RoleTask() {
	}

	/**
	 * 属性角色功能ID(RoleTaskID)的getter方法
	 */
	@Id
	@Column(name = "ROLETASKID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	public String getRoleTaskID() {
		return this.roleTaskID;
	}

	/**
	 * 属性角色功能ID(RoleTaskID)的setter方法
	 */
	public void setRoleTaskID(String roleTaskID) {
		this.roleTaskID = roleTaskID;
	}

	/**
	 * 属性taskAuth的getter方法
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TASKAUTHID")
	public TaskAuth getTaskAuth() {
		return this.taskAuth;
	}

	/**
	 * 属性taskAuth的setter方法
	 */
	public void setTaskAuth(TaskAuth taskAuth) {
		this.taskAuth = taskAuth;
	}

	/**
	 * 属性role的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLEID")
	@JSONField(serialize=false)
	public Role getRole() {
		return this.role;
	}

	/**
	 * 属性role的setter方法
	 */
	public void setRole(Role role) {
		this.role = role;
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

}
