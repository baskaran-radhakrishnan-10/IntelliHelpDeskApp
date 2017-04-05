package com.helpdesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "tbl_chat_history")
public class ChatHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	
	@Column(name = "system_answer")
	private  String sysAnswer;

	@OneToOne
	@JoinColumn(name = "user")
	private  User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
