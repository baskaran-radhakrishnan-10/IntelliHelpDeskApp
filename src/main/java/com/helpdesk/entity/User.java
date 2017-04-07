package com.helpdesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_users")
public class User extends GeneralEntity{

	private static final long serialVersionUID = -1968065884219305808L;
	
	@Column(name = "name", nullable = false)
	private  String userFullName;
	
	@Column(name = "user_id", unique = true,nullable = false)
	private  String userId;
	
	@Column(name = "password" ,nullable = false)
	private  String password;

	@Column(name = "email_id", unique = true,nullable = false)
	private  String emailId;

	@OneToOne
	@JoinColumn(name = "role_id")
	private  Roles roleId;

	@Column(name = "is_active")
	private  Character active;

	@Column(name = "first_login")
	private  Character firstLogin;
	
	@Column(name = "is_deleted")
	private  Character deleted;
	
	/*@Column(name = "deleted_time")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime deletedTime;

	public DateTime getDeletedTime() {
		return deletedTime;
	}

	public void setDeletedTime(DateTime deletedTime) {
		this.deletedTime = deletedTime;
	}*/



	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Roles getRoleId() {
		return roleId;
	}

	public void setRoleId(Roles roleId) {
		this.roleId = roleId;
	}

	public Character getActive() {
		return active;
	}

	public void setActive(Character active) {
		this.active = active;
	}

	public Character getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(Character firstLogin) {
		this.firstLogin = firstLogin;
	}

	public Character getDeleted() {
		return deleted;
	}

	public void setDeleted(Character deleted) {
		this.deleted = deleted;
	}

	/*@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("User Full Name : ").append(userFullName).append(" -- ").append("User Id : ").append(userId).append(" -- ");
		buffer.append("Email Id : ").append(emailId).append(" -- ").append("Role Id : ").append(roleId.getRoleName());
		return buffer.toString();
	}*/
}
