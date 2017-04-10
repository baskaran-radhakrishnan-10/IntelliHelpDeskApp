package com.helpdesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_banding")
public class Banding extends GeneralEntity {

	private static final long serialVersionUID = -1968065884219305808L;
	
	@Column(name = "BAND",nullable = false)
	private int bandid;
	
	@Column(name = "MIN_CTC")
	private int minctc;
		
	@Column(name = "MAX_CTC")
	private int maxctc;
	
	@Column(name = "BAND_ROLE")
	private String brole;

	@Column(name = "BAND_SKILLSET")
	private String bskillset;

	@Column(name = "YROFEXPERIENCE")
	private int yrsexp;
	
	public int getBandId() {
		return bandid;
	}

	public void setBandId(int bandid) {
		this.bandid = bandid;
	}

	public int getMinCtc() {
		return minctc;
	}

	public void setMinCtc(int minctc) {
		this.minctc = minctc;
	}
	public int getMaxCtc() {
		return maxctc;
	}

	public void setMaxCtc(int maxctc) {
		this.maxctc = maxctc;
	}
	public String getBandRole() {
		return brole;
	}

	public void setBandRole(String brole) {
		this.brole = brole;
	}

	public String getBandSkillset() {
		return bskillset;
	}

	public void setBandSkillset(String bskillset) {
		this.bskillset = bskillset;
	}
	
	public int getYearofExp() {
		return yrsexp;
	}

	public void setYearofExp(int yrsexp) {
		this.yrsexp = yrsexp;
	}
}

