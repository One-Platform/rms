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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * POJO类taskAuth
 */
@Entity
@Table(name = "GE_RMS_TASK_AUTH")
public class TaskAuth implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性功能授权ID(TaskAuthID) */
	private String taskAuthID;

	/** 属性task */
	private Task task;

	/** 属性机构代码(ComCode) */
	private String comCode;

	/** 属性操作人员(OperateUser) */
	private String operateUser;

	/** 属性标志字段(Flag) */
	private String flag;

	/** 属性roleTasks */
	private List<RoleTask> roleTasks = new ArrayList<RoleTask>(0);

	/**
	 * 类taskAuth的默认构造方法
	 */
	public TaskAuth() {
	}

	/**
	 * 属性功能授权ID(TaskAuthID)的getter方法
	 */
	@Id
	@Column(name = "TASKAUTHID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	public String getTaskAuthID() {
		return this.taskAuthID;
	}

	/**
	 * 属性功能授权ID(TaskAuthID)的setter方法
	 */
	public void setTaskAuthID(String taskAuthID) {
		this.taskAuthID = taskAuthID;
	}

	/**
	 * 属性task的getter方法
	 */
	@ManyToOne(fetch = FetchType.EAGER)
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

	/**
	 * 属性roleTasks的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taskAuth")
	@JSONField(serialize=false)
	public List<RoleTask> getRoleTasks() {
		return this.roleTasks;
	}

	/**
	 * 属性roleTasks的setter方法
	 */
	public void setRoleTasks(List<RoleTask> roleTasks) {
		this.roleTasks = roleTasks;
	}

}
