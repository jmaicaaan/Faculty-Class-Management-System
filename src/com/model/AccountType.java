package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class AccountType {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ATID")
	private int atID;
	
	@Column(name="AccountType",length=30)
	private String accountType;
	
	@ManyToOne
	@JoinColumn(name="UserID")
	private Users users;
	

	public int getAtID() {
		return atID;
	}

	public void setAtID(int atID) {
		this.atID = atID;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public AccountType(){}
	
	
	public AccountType(String type,Users users)
	{
		setAccountType(type);
		setUsers(users);
	}
	
}
