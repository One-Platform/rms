package com.sinosoft.one.rms.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类roleDesignateId
 */
@Embeddable
public class RoleDesignateId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性角色ID(RoleID) */
	private String roleID;

	/** 属性机构代码(ComCode) */
	private String comCode;

	/**
	 * 类roleDesignateId的默认构造方法
	 */
	public RoleDesignateId() {
	}

	/**
	 * 属性角色ID(RoleID)的getter方法
	 */

	@Column(name = "ROLEID")
	public String getRoleID() {
		return this.roleID;
	}

	/**
	 * 属性角色ID(RoleID)的setter方法
	 */
	public void setRoleID(String roleID) {
		this.roleID = roleID;
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

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof RoleDesignateId)) {
			return false;
		}
		RoleDesignateId castOther = (RoleDesignateId) other;

		return ((this.getRoleID() == castOther.getRoleID()) || (this
				.getRoleID() != null && castOther.getRoleID() != null && this
				.getRoleID().equals(castOther.getRoleID())))
				&& ((this.getComCode() == castOther.getComCode()) || (this
						.getComCode() != null && castOther.getComCode() != null && this
						.getComCode().equals(castOther.getComCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getRoleID() == null ? 0 : this.getRoleID().hashCode());
		result = 37 * result
				+ (getComCode() == null ? 0 : this.getComCode().hashCode());
		return result;
	}

}
