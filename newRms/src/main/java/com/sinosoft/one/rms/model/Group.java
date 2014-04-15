package com.sinosoft.one.rms.model;
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

/**
 * POJO类group
 */
@Entity
@Table(name = "GE_RMS_GROUP")
public class Group implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性用户组ID(GroupID) */
	private String groupID;

	/** 属性名称(Name) */
	private String name;

	/** 属性描述(Des) */
	private String des;

	/** 属性机构代码(ComCode) */
	private String comCode;

	/** 属性是否有效(IsValidate) */
	private String isValidate;

	/** 属性标志字段(Flag) */
	private String flag;

	/** 属性创建日期(CreateTime) */
	private Date createTime;

	/** 属性创建人员(CreateUser) */
	private String createUser;

	/** 属性操作日期(OperateTime) */
	private Date operateTime;

	/** 属性操作人员(OperateUser) */
	private String operateUser;

	/** 属性userGroups */
	private List<UserGroup> userGroups = new ArrayList<UserGroup>(0);

	/** 属性groupRoles */
	private List<GroupRole> groupRoles = new ArrayList<GroupRole>(0);

	/**
	 * 类group的默认构造方法
	 */
	public Group() {
	}

	/**
	 * 属性用户组ID(GroupID)的getter方法
	 */
	@Id
	@Column(name = "GROUPID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	public String getGroupID() {
		return this.groupID;
	}

	/**
	 * 属性用户组ID(GroupID)的setter方法
	 */
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	/**
	 * 属性名称(Name)的getter方法
	 */

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	/**
	 * 属性名称(Name)的setter方法
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 属性描述(Des)的getter方法
	 */

	@Column(name = "DES")
	public String getDes() {
		return this.des;
	}

	/**
	 * 属性描述(Des)的setter方法
	 */
	public void setDes(String des) {
		this.des = des;
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
	 * 属性userGroups的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "group")
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

	/**
	 * 属性groupRoles的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "group")
	@Fetch(FetchMode.SUBSELECT)
	public List<GroupRole> getGroupRoles() {
		return this.groupRoles;
	}

	/**
	 * 属性groupRoles的setter方法
	 */
	public void setGroupRoles(List<GroupRole> groupRoles) {
		this.groupRoles = groupRoles;
	}

}
