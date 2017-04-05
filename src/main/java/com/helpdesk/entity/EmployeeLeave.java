package com.helpdesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.equiniti.persistance_api.audit.api.IAuditLog;

@Entity
@Table(name = "tbl_emp_leave_details")
public class EmployeeLeave extends GeneralEntity implements IAuditLog{

	private static final long serialVersionUID = -1968065884219305808L;
	
	@Column(name = "EMPLOYEE_ID",unique = true, nullable = false)
	private int lempid;
	
	@Column(name = "CL_USED")
	private int clused;
	
	@Column(name = "EL_USED")
	private int elused;
	
	@Column(name = "SL_USED")
	private int slused;

	@Column(name = "CL_BALANCE")
	private int clbal;

	@Column(name = "EL_BALANCE")
	private int elbal;

	@Column(name = "SL_BALANCE")
	private int slbal;
		
	public int getEmdId() {
		return lempid;
	}

	public void setEmdId(int lempid) {
		this.lempid = lempid;
	}

	public int getClUsed() {
		return clused;
	}

	public void setClUsed(int clused) {
		this.clused = clused;
	}
	
	public int getElUsed() {
		return elused;
	}

	public void setElUsed(int elused) {
		this.elused = elused;
	}
	
	public int getSlUsed() {
		return slused;
	}

	public void setSlUsed(int slused) {
		this.slused = slused;
	}
	
	public int getClbalance() {
		return clbal;
	}

	public void setClbalance(int clbal) {
		this.clbal = clbal;
	}
	
	public int getElbalance() {
		return elbal;
	}

	public void setElbalance(int elbal) {
		this.elbal = elbal;
	}
	
	public int getSlbalance() {
		return slbal;
	}

	public void setSlbalance(int slbal) {
		this.slbal = slbal;
	}
}

