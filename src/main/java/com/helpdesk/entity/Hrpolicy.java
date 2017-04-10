package com.helpdesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_hr_policy")
public class Hrpolicy extends GeneralEntity{

	private static final long serialVersionUID = -1968065884219305808L;
	
	@Column(name = "POLICY_ID",unique = true, nullable = false)
	private int policyid;
	
	@Column(name = "POLICY_KEY_WORD")
	private String policykey;
		
	@Column(name = "POLICY_DESCRIPTION",length=4000)
	private String policydesc;
	
	
	public int getPolicyId() {
		return policyid;
	}

	public void setPolicyId(int policyid) {
		this.policyid = policyid;
	}

	public String getPolicyKey() {
		return policykey;
	}

	public void setPolicyKey(String policykey) {
		this.policykey = policykey;
	}
	
	public String getPolicyDesc() {
		return policydesc;
	}

	public void setPolicyDesc(String policydesc) {
		this.policydesc = policydesc;
	}
	
}

