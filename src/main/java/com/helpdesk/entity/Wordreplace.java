package com.helpdesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_word_replace")
public class Wordreplace extends GeneralEntity {

	private static final long serialVersionUID = -1968065884219305808L;
	
	@Column(name = "act_string",nullable = false)
	private String actstr;
	
	@Column(name = "replace_string",nullable = false)
	private String replacestr;
		
	
	public String getActString() {
		return actstr;
	}

	public void setActString(String actstr) {
		this.actstr = actstr;
	}

	public String getReplaceString() {
		return replacestr;
	}

	public void setReplaceString(String replacestr) {
		this.replacestr = replacestr;
	}
	
	
}

