package com.helpdesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_info_burst")
public class Infoburst extends GeneralEntity{

	private static final long serialVersionUID = -1968065884219305808L;
	
	@Column(name = "INBURST_ID",unique = true, nullable = false)
	private int burstid;
	
	@Column(name = "INBURST_KEY_WORD")
	private String burstkey;
		
	@Column(name = "INBURST_DESCRIPTION")
	private String burstdesc;
	
	
	public int getBurstId() {
		return burstid;
	}

	public void setBurstId(int burstid) {
		this.burstid = burstid;
	}

	public String getBurstKey() {
		return burstkey;
	}

	public void setBurstKey(String burstkey) {
		this.burstkey = burstkey;
	}
	
	public String getBurstDesc() {
		return burstdesc;
	}

	public void setBurstDesc(String burstdesc) {
		this.burstdesc = burstdesc;
	}
	
}

