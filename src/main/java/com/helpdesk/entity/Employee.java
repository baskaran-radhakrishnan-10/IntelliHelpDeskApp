package com.helpdesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tbl_employee")
public class Employee extends GeneralEntity{

	private static final long serialVersionUID = -1968065884219305808L;
	
	@Column(name = "EMPLOYEE_ID",unique = true, nullable = false)
	private int empid;
	
	@Column(name = "EMPLOYEE_NAME", nullable = false)
	private String empname;
	
	@Column(name = "EMP_ROLE", nullable = false)
	private String emprole;

	@Column(name = "EMP_PROJECT")
	private String empproj;

	@Column(name = "EMP_SKILLSET")
	private String empskill;

	@Column(name = "EMP_EMAIL")
	private String empemail;
	
	@Column(name = "EMP_MGR_ID")
	private int empmgrid;
	
	@Column(name = "EMP_DOJ")
	private Date empdoj;
	
	@Column(name = "EMP_DOL")
	private Date empdol;
	
	@Column(name = "EMP_CTC_PM")
	private int empctc;
	

	public int getEmdId() {
		return empid;
	}

	public void setEmdId(int empid) {
		this.empid = empid;
	}

	public String getEmpName() {
		return empname;
	}

	public void setEmpName(String empname) {
		this.empname = empname;
	}
	public String getEmpRole() {
		return emprole;
	}

	public void setEmpRole(String emprole) {
		this.emprole = emprole;
	}
	public String getEmpProject() {
		return empproj;
	}

	public void setEmpProject(String empproj) {
		this.empproj = empproj;
	}

	public String getEmpSkillset() {
		return empskill;
	}

	public void setEmpSkillset(String empskill) {
		this.empskill = empskill;
	}
	
	public String getEmpEmail() {
		return empemail;
	}

	public void setEmpEmail(String empemail) {
		this.empemail = empemail;
	}
	
	public int getEmpManagerID() {
		return empmgrid;
	}

	public void setEmpManagerID(int empmgrid) {
		this.empmgrid = empmgrid;
	}
	
	public Date getEmpDOJ() {
		return empdoj;
	}

	public void setEmpDOJ(Date empdoj) {
		this.empdoj = empdoj;
	}
	
	public Date getEmpDOL() {
		return empdol;
	}

	public void setEmpDOL(Date empdol) {
		this.empdol = empdol;
	}
	
	public int getEmpCTC() {
		return empctc;
	}

	public void setEmpCTC(int empctc) {
		this.empctc = empctc;
	}
}

