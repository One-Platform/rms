package com.sinosoft.one.rms.model;

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

@Entity
@Table(name = "GE_RMS_RULE_BUSDATAINFO")
public class BusDataInfo  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 属性业务数据信息ID(BusDataInfoID) */
	private String busDataInfoID;
	
	/** 属性数据规则 */
	private DataRule dataRule;
	
	/** 属性业务数据表(BusDataTable) */
	private String busDataTable;
	
	/** 属性业务数据列(BusDataColumn) */
	private String busDataColumn;
	

	/**
	 * 类BusDataInfo的默认构造方法
	 */
	public BusDataInfo(){
		
	}
	
	/**
	 * 属性业务数据信息ID(BusDataInfoID) 的getter方法
	 */
	@Id
	@Column(name = "BUSDATAINFOID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	public String getBusDataInfoID() {
		return busDataInfoID;
	}

	/**
	 * 属性业务数据信息ID(BusDataInfoID) 的Setter方法
	 */
	public void setBusDataInfoID(String busDataInfoID) {
		this.busDataInfoID = busDataInfoID;
	}

	/**
	 * 属性dataRule的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DATARULEID")
	@XmlTransient
	public DataRule getDataRule() {
		return dataRule;
	}

	/**
	 * 属性dataRule的Setter方法
	 */
	public void setDataRule(DataRule dataRule) {
		this.dataRule = dataRule;
	}

	/**
	 * 属性业务数据表(BusDataTable)的getter方法
	 */
	@Column(name = "BUSDATATABLE")
	public String getBusDataTable() {
		return busDataTable;
	}

	/**
	 * 属性业务数据列(BusDataTable)的Setter方法
	 */
	public void setBusDataTable(String busDataTable) {
		this.busDataTable = busDataTable;
	}
	
	/**
	 * 属性业务数据列(BusDataColumn)的getter方法
	 */
	@Column(name = "BUSDATACOLUMN")
	public String getBusDataColumn() {
		return busDataColumn;
	}

	/**
	 * 属性业务数据列(BusDataColumn)的Setter方法
	 */
	public void setBusDataColumn(String busDataColumn) {
		this.busDataColumn = busDataColumn;
	}
	
}
