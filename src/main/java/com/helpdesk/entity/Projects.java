package com.helpdesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_projects")
public class Leakage extends GeneralEntity{

	private static final long serialVersionUID = -1968065884219305808L;
	
	@Column(name = "PROJ_ID",unique = true, nullable = false)
	private int projid;
	
	@Column(name = "PROJECT_NAME")
	private String projname;
	
	@Column(name = "CLIENT_NAME")
	private String clientname;

	public int getProjectID() {
		return projid;
	}

	public void setProjectID(int projid) {
		this.projid = projid;
	}

	public String getProjectName() {
		return projname;
	}

	public void setProjectName(String projname) {
		this.projname = projname;
	}
	
	public String getClientName() {
		return clientname;
	}

	public void setClientName(String clientname) {
		this.clientname = clientname;
	}
	
			
}

