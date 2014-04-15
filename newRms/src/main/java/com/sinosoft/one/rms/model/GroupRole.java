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
 * POJO类groupRole
 */
@Entity
@Table(name = "GE_RMS_GROUPROLE")
public class GroupRole implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性用户组角色ID(GroupRoleID) */
	private String groupRoleID;

	/** 属性group */
	private Group group;

	/** 属性role */
	private Role role;

	/** 属性是否有效(IsValidate) */
	private String isValidate;

	/**
	 * 类groupRole的默认构造方法
	 */
	public GroupRole() {
	}

	/**
	 * 属性用户组角色ID(GroupRoleID)的getter方法
	 */
	@Id
	@Column(name = "GROUPROLEID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	public String getGroupRoleID() {
		return this.groupRoleID;
	}

	/**
	 * 属性用户组角色ID(GroupRoleID)的setter方法
	 */
	public void setGroupRoleID(String groupRoleID) {
		this.groupRoleID = groupRoleID;
	}

	/**
	 * 属性group的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUPID")
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
	 * 属性role的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLEID")
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

}
