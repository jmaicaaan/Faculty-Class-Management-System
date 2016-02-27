package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="Password")
public class Password {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PassID")
	private int passID;
	
	@Column(name="Password",insertable=false, updatable = true, nullable = false,
			columnDefinition="varchar(250) default '+91NMyl5hSlOZMu+6dXukQ=='")
	private String password;
	
	@ManyToOne
	@JoinColumn(name="UserID")
	private Users users;
	
	
	private String newPassword;
	
	private String newPassword_Verify;
	

	public int getPassID() {
		return passID;
	}

	public void setPassID(int passID) {
		this.passID = passID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getNewPassword_Verify() {
		return newPassword_Verify;
	}
	public void setNewPassword_Verify(String newPassword_Verify) {
		this.newPassword_Verify = newPassword_Verify;
	}

	
	
	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Password(){}
	
	public Password(Users users)
	{
		//setPassword(password);
		setUsers(users);
	}
	
	
}
