package com.helpdesk.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_risk_details")
public class Risks extends GeneralEntity{

	private static final long serialVersionUID = -1968065884219305808L;
	
	@Column(name = "RISKID",unique = true, nullable = false)
	private int riskid;
	
	@Column(name = "RISKITEM", nullable = false)
	private String riskitem;
	
	@Column(name = "RISK_PROJECT")
	private String rproject;

	@Column(name = "RISK_OWNER")
	private String rowner;

	@Column(name = "DTCOMMUNICATED")
	private Date dtcomm;

	@Column(name = "STATUS")
	private String rstatus;
	
	@Column(name = "MITIGATIONPLAN")
	private String rmitplan;
	
	
	public int getRiskID() {
		return riskid;
	}

	public void setRiskID(int riskid) {
		this.riskid = riskid;
	}

	public String getRiskItem() {
		return riskitem;
	}

	public void setRiskItem(String riskitem) {
		this.riskitem = riskitem;
	}
	public String getRiskProj() {
		return rproject;
	}

	public void setRiskProj(String rproject) {
		this.rproject = rproject;
	}
	
	public String getRiskOwner() {
		return rowner;
	}

	public void setRiskOwner(String rowner) {
		this.rowner = rowner;
	}

	public Date getCommunicatedDate() {
		return dtcomm;
	}

	public void setCommunicatedDate(Date dtcomm) {
		this.dtcomm = dtcomm;
	}
	
	public String getRiskStatus() {
		return rstatus;
	}

	public void setRiskStatus(String rstatus) {
		this.rstatus = rstatus;
	}
	
	public String getRiskMitPlan() {
		return rmitplan;
	}

	public void setRiskMitPlan(String rmitplan) {
		this.rmitplan = rmitplan;
	}
		
}

