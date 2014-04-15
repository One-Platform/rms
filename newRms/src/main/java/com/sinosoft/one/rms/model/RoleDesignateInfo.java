package com.sinosoft.one.rms.model;

import java.util.Date;


/**
 * 指派信息封装类
 * @author Administrator
 */
public class RoleDesignateInfo {
	private String roleName;

	private String roleId;
	/** 属性指派机构*/
	private String comCode;
	
	private String comCName;
	
	/** 属性创建日期(CreateTime) */
	private Date createTime;

	/** 属性创建人员(CreateUser) */
	private String createUser;

	/** 属性操作日期(OperateTime) */
	private Date operateTime;

	/** 属性操作人员(OperateUser) */
	private String operateUser;
	
	/** 属性类型(OperateUser) */
	private String type;
	
	public RoleDesignateInfo(){
		
	}
	
	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

//	public Role getRole() {
//		return role;
//	}
//
//	public void setRole(Role role) {
//		this.role = role;
//	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getComCName() {
		return comCName;
	}

	public void setComCName(String comCName) {
		this.comCName = comCName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}
