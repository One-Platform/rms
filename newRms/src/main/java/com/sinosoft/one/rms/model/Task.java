package com.sinosoft.one.rms.model;
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType; 
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * POJO类task
 */
@Entity
@Table(name = "GE_RMS_TASK")
public class Task implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性功能ID(TaskID) */
	private String taskID;


	/** 属性名称(Name) */
	private String name;

	/** 属性菜单名称(MenuName) */
	private String menuName;

	/** 属性菜单URL(MenuURL) */
	private String menuURL;

	/** 属性描述(Des) */
	private String des;

	/** 属性是否有效(IsValidate) */
	private String isValidate;

	/** 属性标志字段(Flag) */
	private String flag;

	/** 属性系统标志字段(sysFalg) */
	private String sysFlag;
	
	/** 属性是否为菜单字段(sysFalg) */
	private String isAsMenu;
	
	/** 属性排序字段(sort) */
	private String sort;
	
	/** 属性是否可配置规则字段(isConfigDataRule) */
	private String isConfigDataRule;
	
	/** 属性excPowers */
	private List<ExcPower> excPowers = new ArrayList<ExcPower>(0);

	/** 属性busPowers */
	private List<BusPower> busPowers = new ArrayList<BusPower>(0);

	/** 属性taskAuths */
	private List<TaskAuth> taskAuths = new ArrayList<TaskAuth>(0);

	/** 子节点*/
	private List<Task> children = new ArrayList<Task>();
	
	/** 父节点*/
	private Task parent;

	/**
	 * 类task的默认构造方法
	 */
	public Task() {
	}

	/**
	 * 属性功能ID(TaskID)的getter方法
	 */
	@Id
	@Column(name = "TASKID")
	public String getTaskID() {
		return this.taskID;
	}

	/**
	 * 属性功能ID(TaskID)的setter方法
	 */
	public void setTaskID(String taskID) {
		this.taskID = taskID;
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
	 * 属性菜单名称(MenuName)的getter方法
	 */

	@Column(name = "MENUNAME")
	public String getMenuName() {
		return this.menuName;
	}

	/**
	 * 属性菜单名称(MenuName)的setter方法
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * 属性菜单URL(MenuURL)的getter方法
	 */

	@Column(name = "MENUURL")
	public String getMenuURL() {
		return this.menuURL;
	}

	/**
	 * 属性菜单URL(MenuURL)的setter方法
	 */
	public void setMenuURL(String menuURL) {
		this.menuURL = menuURL;
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
	 * 属性系统标志字段(sysFlag)的getter方法
	 */
	@Column(name = "SYSFLAG")
	public String getSysFlag() {
		return sysFlag;
	}
	
	/**
	 * 属性系统标志字段(sysFlag)的setter方法
	 */
	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}
	
	/**
	 * 属性是否为菜单字段(isAsMenu)的getter方法
	 */
	@Column(name = "ISASMENU")
	public String getIsAsMenu() {
		return isAsMenu;
	}

	/**
	 * 属性是否为菜单字段(isAsMenu)的setter方法
	 */
	public void setIsAsMenu(String isAsMenu) {
		this.isAsMenu = isAsMenu;
	}
	
	/**
	 * 属性排序字段(sort)的getter方法
	 */
	@Column(name = "SORT")
	public String getSort() {
		return sort;
	}

	/**
	 * 属性排序字段(sort)的setter方法
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * 属性是否可配置规则字段(isConfigDataRule)的getter方法
	 */
	@Column(name = "ISCOFIGDATARULE")
	public String getIsConfigDataRule() {
		return isConfigDataRule;
	}

	/**
	 * 属性是否可配置规则字段(isConfigDataRule)的setter方法
	 */
	public void setIsConfigDataRule(String isConfigDataRule) {
		this.isConfigDataRule = isConfigDataRule;
	}
	
//	/**
//	 * 属性父功能ID(ParentID)的getter方法
//	 */
//
//	@Column(name = "PARENTID")
//	public String getParentID() {
//		return this.parentID;
//	}
//
//	/**
//	 * 属性父功能ID(ParentID)的setter方法
//	 */
//	public void setParentID(String parentID) {
//		this.parentID = parentID;
//	}

	/**
	 * 属性excPowers的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "task")
	@XmlTransient
	@JSONField(serialize=false)
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
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "task")
	@XmlTransient
	@JSONField(serialize=false)
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
	 * 属性taskAuths的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "task")
	@XmlTransient
	@JSONField(serialize=false)
	public List<TaskAuth> getTaskAuths() {
		return this.taskAuths;
	}

	/**
	 * 属性taskAuths的setter方法
	 */
	public void setTaskAuths(List<TaskAuth> taskAuths) {
		this.taskAuths = taskAuths;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "parent")
	@Fetch(FetchMode.SUBSELECT)
	 public List<Task> getChildren() {
		return children;
	}

	public void setChildren(List<Task> children) {
		this.children = children;
	}

	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "PARENTID")
	@JsonIgnore
	@XmlTransient
	public Task getParent() {
		return parent;
	}

	public void setParent(Task parent) {
		this.parent = parent;
	}

}
