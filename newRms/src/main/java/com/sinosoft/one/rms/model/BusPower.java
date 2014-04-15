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
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * POJO类busPower
 */
@Entity
@Table(name = "GE_RMS_BUSPOWER")
public class BusPower implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性数据权限ID(BusPowerID) */
	private String busPowerID;

	/** 属性task */
	private Task task;

	/** 属性userPower */
	private UserPower userPower;

	/** 属性dataRule */
	private DataRule dataRule;

	/** 属性数据规则参数(DataRuleParam) */
	private String dataRuleParam;

	/** 属性是否有效(IsValidate) */
	private String isValidate;

	/**
	 * 类busPower的默认构造方法
	 */
	public BusPower() {
	}

	/**
	 * 属性数据权限ID(BusPowerID)的getter方法
	 */
	@Id
	@Column(name = "BUSPOWERID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	public String getBusPowerID() {
		return this.busPowerID;
	}

	/**
	 * 属性数据权限ID(BusPowerID)的setter方法
	 */
	public void setBusPowerID(String busPowerID) {
		this.busPowerID = busPowerID;
	}

	/**
	 * 属性task的getter方法
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TASKID")
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
	@JoinColumn(name = "USERPOWERID")
	@XmlTransient
	@JSONField(serialize=false)
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
	 * 属性dataRule的getter方法
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DATARULEID", nullable = false)
	public DataRule getDataRule() {
		return this.dataRule;
	}

	/**
	 * 属性dataRule的setter方法
	 */
	public void setDataRule(DataRule dataRule) {
		this.dataRule = dataRule;
	}

	/**
	 * 属性数据规则参数(DataRuleParam)的getter方法
	 */

	@Column(name = "DATARULEPARAM")
	public String getDataRuleParam() {
		return this.dataRuleParam;
	}

	/**
	 * 属性数据规则参数(DataRuleParam)的setter方法
	 */
	public void setDataRuleParam(String dataRuleParam) {
		this.dataRuleParam = dataRuleParam;
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
