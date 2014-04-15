package com.sinosoft.one.rms.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类roleDesignate
 */
@Entity
@Table(name = "GE_RMS_ROLE_DESIGNATE")
public class RoleDesignate implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性网点编号 */
	private RoleDesignateId id;

	/** 属性role */
	private Role role;

	/** 属性创建日期(CreateTime) */
	private Date createTime;

	/** 属性创建人员(CreateUser) */
	private String createUser;

	/** 属性操作日期(OperateTime) */
	private Date operateTime;

	/** 属性操作人员(OperateUser) */
	private String operateUser;

	/** 属性标志字段(Flag) */
	private String flag;

	/**
	 * 类roleDesignate的默认构造方法
	 */
	public RoleDesignate() {
	}

	/**
	 * 属性网点编号的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "roleID", column = @Column(name = "ROLEID")),
			@AttributeOverride(name = "comCode", column = @Column(name = "COMCODE")) })
	public RoleDesignateId getId() {
		return this.id;
	}

	/**
	 * 属性网点编号的setter方法
	 */
	public void setId(RoleDesignateId id) {
		this.id = id;
	}

	/**
	 * 属性role的getter方法
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLEID", nullable = false, insertable = false, updatable = false)
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
	 * 属性创建日期(CreateTime)的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATETIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * 属性创建日期(CreateTime)的setter方法
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 属性创建人员(CreateUser)的getter方法
	 */

	@Column(name = "CREATEUSER")
	public String getCreateUser() {
		return this.createUser;
	}

	/**
	 * 属性创建人员(CreateUser)的setter方法
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 属性操作日期(OperateTime)的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "OPERATETIME")
	public Date getOperateTime() {
		return this.operateTime;
	}

	/**
	 * 属性操作日期(OperateTime)的setter方法
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * 属性操作人员(OperateUser)的getter方法
	 */

	@Column(name = "OPERATEUSER")
	public String getOperateUser() {
		return this.operateUser;
	}

	/**
	 * 属性操作人员(OperateUser)的setter方法
	 */
	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
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
