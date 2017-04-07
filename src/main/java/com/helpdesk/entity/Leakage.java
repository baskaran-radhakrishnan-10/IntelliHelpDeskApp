package com.helpdesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_defect_leakage")
public class Leakage extends GeneralEntity{

	private static final long serialVersionUID = -1968065884219305808L;
	
	@Column(name = "DL_ID",unique = true, nullable = false)
	private int dlid;
	
	@Column(name = "DL_PROJECT")
	private String dlproject;
	
	@Column(name = "FAT_BUG_CNT")
	private int fatbugcnt;

	@Column(name = "UAT_BUG_CNT")
	private int uatbugcnt;

	@Column(name = "LEAKAGE_PER")
	private int leakper;
	
	@Column(name = "LEAK_MONTH_YEAR")
	private int dlmonyear;
	
	public int getDefectLeakID() {
		return dlid;
	}

	public void setDefectLeakID(int dlid) {
		this.dlid = dlid;
	}

	public String getDlProject() {
		return dlproject;
	}

	public void setDlProject(String dlproject) {
		this.dlproject = dlproject;
	}
	
	public int getFatBugCnt() {
		return fatbugcnt;
	}

	public void setFatBugCnt(int fatbugcnt) {
		this.fatbugcnt = fatbugcnt;
	}
	
	public int getUatBugCnt() {
		return uatbugcnt;
	}

	public void setUatBugCntCnt(int uatbugcnt) {
		this.uatbugcnt = uatbugcnt;
	}
	
	public int getDefectLeakPer() {
		return leakper;
	}

	public void setDefectLeakPer(int leakper) {
		this.leakper = leakper;
	}

	public int getDlMonthYr() {
		return dlmonyear;
	}

	public void setDlMonthYr(int dlmonyear) {
		this.dlmonyear = dlmonyear;
	}
	
	
		
}

