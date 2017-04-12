package com.helpdesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "tbl_chat_history")
public class ChatHistory {
	
	@Id
	@GeneratedValue(generator = "nosicSeq")
	@SequenceGenerator(name = "nosicSeq", sequenceName = "NOSIC_SEQ", allocationSize = 1)
	@Column(name = "gkey", unique = true, nullable = false)
	private int gkey;
	
	@Column(name = "user_query_time")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime queryTime;
	
	@Column(name = "system_answer_time")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime sysAnswerTime;
	
	@Column(name = "user_query")
	private  String userQuery;
	
	@Column(name = "system_answer",length=2000)
	private  String sysAnswer;

	@Column(name = "user_id")
	private  Integer userId;
	
	@Column(name = "user_query_time_stamp")
	private Long queryTimeStamp;
	
	@Column(name = "system_answer_time_stamp")
	private Long sysAnswerTimeStamp;
	
	@Column(name = "is_sysresponse_json")
	private  Character jsonSysResponse;

	public int getGkey() {
		return gkey;
	}

	public void setGkey(int gkey) {
		this.gkey = gkey;
	}

	public DateTime getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(DateTime queryTime) {
		this.queryTime = queryTime;
	}

	public DateTime getSysAnswerTime() {
		return sysAnswerTime;
	}

	public void setSysAnswerTime(DateTime sysAnswerTime) {
		this.sysAnswerTime = sysAnswerTime;
	}

	public String getUserQuery() {
		return userQuery;
	}

	public void setUserQuery(String userQuery) {
		this.userQuery = userQuery;
	}

	public String getSysAnswer() {
		return sysAnswer;
	}

	public void setSysAnswer(String sysAnswer) {
		this.sysAnswer = sysAnswer;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Long getQueryTimeStamp() {
		return queryTimeStamp;
	}

	public void setQueryTimeStamp(Long queryTimeStamp) {
		this.queryTimeStamp = queryTimeStamp;
	}

	public Long getSysAnswerTimeStamp() {
		return sysAnswerTimeStamp;
	}

	public void setSysAnswerTimeStamp(Long sysAnswerTimeStamp) {
		this.sysAnswerTimeStamp = sysAnswerTimeStamp;
	}

	public Character getJsonSysResponse() {
		return jsonSysResponse;
	}

	public void setJsonSysResponse(Character jsonSysResponse) {
		this.jsonSysResponse = jsonSysResponse;
	}

}
