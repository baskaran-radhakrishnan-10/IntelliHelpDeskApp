package com.helpdesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_mst_lookup")
public class Lookup extends GeneralEntity{

	private static final long serialVersionUID = -1968065884219305808L;
	
	@Column(name = "LOOKUP_TYPE",nullable = false)
	private String ltype;
	
	@Column(name = "LOOKUP_CODE",nullable = false)
	private String lcode;
		
	@Column(name = "LOOKUP_VALUE",nullable = false)
	private String ldesc;
	
	@Column(name = "ACTIVEYN",nullable = false)
	private String lactive;
	
	public String getLookupType() {
		return ltype;
	}

	public void setLookupType(String ltype) {
		this.ltype = ltype;
	}

	public String getLookupCode() {
		return lcode;
	}

	public void setLookupCode(String lcode) {
		this.lcode = lcode;
	}
	
	public String getLookupDesc() {
		return ldesc;
	}

	public void setLookupDesc(String ldesc) {
		this.ldesc = ldesc;
	}
	
	public String getLookupAct() {
		return lactive;
	}

	public void setLookupAct(String lactive) {
		this.lactive = lactive;
	}
}

