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
	
	@Column(name = "LOOKUP_DESC",nullable = false,length=2000)
	private String ldesc;
		
	@Column(name = "TABLE_NAME")
	private String ltname;
	
	@Column(name = "COLUMN_NAME")
	private String lcname;
	
	@Column(name = "ACTIVEYN")
	private String lactive;
	
	public String getLookupType() {
		return ltype;
	}

	public void setLookupType(String ltype) {
		this.ltype = ltype;
	}

	public String getLookupDesc() {
		return ldesc;
	}

	public void setLookupDesc(String ldesc) {
		this.ldesc = ldesc;
	}
	
	public String getTableName() {
		return ltname;
	}

	public void setTableName(String ltname) {
		this.ltname = ltname;
	}
	
	public String getFieldName() {
		return lcname;
	}

	public void setFieldName(String lcname) {
		this.lcname = lcname;
	}
	
	public String getLookupAct() {
		return lactive;
	}

	public void setLookupAct(String lactive) {
		this.lactive = lactive;
	}
}

