package com.helpdesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.equiniti.persistance_api.audit.api.IAuditLog;

@Entity
@Table(name = "tbl_banding")
public class Banding extends GeneralEntity implements IAuditLog{

	private static final long serialVersionUID = -1968065884219305808L;
	
	@Column(name = "BAND",nullable = false)
	private int bandid;
	
	@Column(name = "MIN_CTC")
	private int minctc;
		
	@Column(name = "MAX_CTC")
	private int maxctc;
	
	@Column(name = "ROLE")
	private String role;

	@Column(name = "SKILLSET")
	private String skillset;

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
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSkillset() {
		return skillset;
	}

	public void setSkillset(String skillset) {
		this.skillset = skillset;
	}
	
	public int getYearofExp() {
		return yrsexp;
	}

	public void setYearofExp(int yrsexp) {
		this.yrsexp = yrsexp;
	}
}

