package com.helpdesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_key_refer")
public class Keywordrefer extends GeneralEntity {

	private static final long serialVersionUID = -1968065884219305808L;
	
	@Column(name = "KEYWORD1")
	private String key1;
	
	@Column(name = "KEYWORD2")
	private String key2;
	
	@Column(name = "KEYWORD3")
	private String key3;	

	@Column(name = "KEYWORD4")
	private String key4;
	
	@Column(name = "KEYWORD5")
	private String key5;
	
	@Column(name = "KEYWORD6")
	private String key6;
	
	@Column(name = "DYNAWORD1")
	private String dyna1;
	
	@Column(name = "DYNAWORD2")
	private String dyna2;
	
	@Column(name = "TABLENAME")
	private String tablename;
	
	@Column(name = "FIELDNAME")
	private String fieldname;
	
	@Column(name = "WHERETCOND")
	private String where1;
	
	@Column(name = "WHERECOND")
	private String where2;
	
	public String getKeyword1() {
		return key1;
	}

	public void setKeyword1(String key1) {
		this.key1 = key1;
	}
	
	public String getKeyword2() {
		return key2;
	}

	public void setKeyword2(String key2) {
		this.key2 = key2;
	}
	
	public String getKeyword3() {
		return key3;
	}

	public void setKeyword3(String key3) {
		this.key3 = key3;
	}
	
	public String getKeyword4() {
		return key4;
	}

	public void setKeyword4(String key4) {
		this.key4 = key4;
	}
	
	public String getKeyword5() {
		return key5;
	}

	public void setKeyword5(String key5) {
		this.key5 = key5;
	}
	
	public String getKeyword6() {
		return key6;
	}

	public void setKeyword6(String key6) {
		this.key6 = key6;
	}
	
	public String getDynamicword1() {
		return dyna1;
	}

	public void setDynamicword1(String dyna1) {
		this.dyna1 = dyna1;
	}
	
	public String getDynamicword2() {
		return dyna2;
	}

	public void setDynamicword2(String dyna2) {
		this.dyna2 = dyna2;
	}
	
	public String getTableName() {
		return tablename;
	}

	public void setTableName(String tablename) {
		this.tablename = tablename;
	}
	
	public String getFieldName() {
		return fieldname;
	}

	public void setFieldName(String fieldname) {
		this.fieldname = fieldname;
	}
	
	public String getWhereSpecialCond() {
		return where1;
	}

	public void setWhereSpecialCond(String where1) {
		this.where1 = where1;
	}
	
	public String getWhereCond() {
		return where2;
	}

	public void setWhereCond(String where2) {
		this.where2 = where2;
	}
}

