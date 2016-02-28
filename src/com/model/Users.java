package com.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="Users")
public class Users {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="UserID")
	private int userID;
	
	@Column(length=25,name="IDNo")
	private String idNo;
	
	@Column(length=25,name="FirstName")
	private String firstName;
	
	@Column(length=25,name="LastName")
	private String lastName;
	
	@Column(length=25,name="UserName")
	private String username;
	
	@Column(name="PictureUrl",insertable=false, updatable = true, nullable = false,
	columnDefinition="varchar(250) default 'resources/img/avatar.png'")
	private String pictureUrl;
	
	@OneToMany(mappedBy="users", fetch = FetchType.LAZY)
	private Set<AccountType> accountType;

	@OneToMany(mappedBy="users", fetch = FetchType.LAZY)
	private Set<Password> password;
	
	@OneToMany(mappedBy="users", fetch = FetchType.LAZY)
	private Set<ProfessorProfile> professorProfile;
	
	@OneToMany(mappedBy="users", fetch = FetchType.LAZY)
	private Set<Classlist> ClassList;

	public Set<Classlist> getClassList() {
		return ClassList;
	}
	public void setClassList(Set<Classlist> ClassList) {
		this.ClassList = ClassList;
	}
	public Set<ProfessorProfile> getProfessorProfile() {
		return professorProfile;
	}
	public void setProfessorProfile(Set<ProfessorProfile> professorProfile) {
		this.professorProfile = professorProfile;
	}
	public Set<Password> getPassword() {
		return password;
	}
	public void setPassword(Set<Password> password) {
		this.password = password;
	}
	public Set<AccountType> getAccountType() {
		return accountType;
	}
	public void setAccountType(Set<AccountType> accountType) {
		this.accountType = accountType;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	
	
	public Users(){}
	
	public Users(String idNo,String firstName,String lastName,String userName){
		setIdNo(idNo);
		setFirstName(firstName);
		setLastName(lastName);
		setUsername(userName);
	}
	public Users(String idNo,String firstName,String lastName){
		setIdNo(idNo);
		setFirstName(firstName);
		setLastName(lastName);
	}
	public Users(String pictureUrl,int id){
		setPictureUrl(pictureUrl);
		setUserID(id);
	}
	
	public Users(String username){
		setUsername(username);
	}


}
