package com.helpdesk.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_schedule_deviation")
public class Deviation extends GeneralEntity{

	private static final long serialVersionUID = -1968065884219305808L;
	
	@Column(name = "SD_ID",unique = true, nullable = false)
	private int sdid;
	
	@Column(name = "SD_PROJECT")
	private String sdproject;
	
	@Column(name = "PLANNED_ST_DATE")
	private Date planstdt;

	@Column(name = "PLANNED_END_DT")
	private Date planenddt;

	@Column(name = "ACTUAL_ST_DATE")
	private Date actstdt;

	@Column(name = "ACTUAL_END_DT")
	private Date actenddt;
	
	@Column(name = "DEVIATION_PER")
	private int devper;
	
	@Column(name = "SD_MONTH_YEAR")
	private int sdmonyear;
	
	public int getDeviationID() {
		return sdid;
	}

	public void setDeviationID(int sdid) {
		this.sdid = sdid;
	}

	public String getSdProject() {
		return sdproject;
	}

	public void setSdProject(String sdproject) {
		this.sdproject = sdproject;
	}
	
	public Date getPlanStartDt() {
		return planstdt;
	}

	public void setPlanStartDt(Date planstdt) {
		this.planstdt = planstdt;
	}
	
	public Date getPlanEndDt() {
		return planenddt;
	}

	public void setPlanEndDt(Date planenddt) {
		this.planenddt = planenddt;
	}
	
	public Date getActStartDt() {
		return actstdt;
	}

	public void setActStartDt(Date actstdt) {
		this.actstdt = actstdt;
	}
	
	public Date getActEndDt() {
		return actenddt;
	}

	public void setActEndDt(Date actenddt) {
		this.actenddt = actenddt;
	}
	
	public int getDevPer() {
		return devper;
	}

	public void setDevPer(int devper) {
		this.devper = devper;
	}

	public int getMonthYr() {
		return sdmonyear;
	}

	public void setMonthYr(int sdmonyear) {
		this.sdmonyear = sdmonyear;
	}
	
	
		
}

