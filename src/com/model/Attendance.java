package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Attendance {
	
	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="AttendID")
	private int attendID;
	
	@Column(name="Attendance",length=4)
	private String attendance;
	
	@Column(name="Date",length=15)
	private String date;
	
	@Column(name="Remarks",length=40, columnDefinition="varchar(40) default 'None'")
	private String remarks;
	
	@ManyToOne
	@JoinColumn(name="ClassID")
	private Classlist classlist;

	@Transient
	private double noOfLives;

	@Transient
	private int noOfLates;
	

	@Transient
	private int noOfAbsences;
	public double getNoOfLives() {
		return noOfLives;
	}

	public void setNoOfLives(double noOfLives) {
		this.noOfLives = noOfLives;
	}

	public int getAttendID() {
		return attendID;
	}

	public void setAttendID(int attendID) {
		this.attendID = attendID;
	}

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Classlist getClasslist() {
		return classlist;
	}

	public void setClasslist(Classlist classlist) {
		this.classlist = classlist;
	}
	public int getNoOfLates() {
		return noOfLates;
	}

	public void setNoOfLates(int noOfLates) {
		this.noOfLates = noOfLates;
	}

	public int getNoOfAbsences() {
		return noOfAbsences;
	}

	public void setNoOfAbsences(int noOfAbsences) {
		this.noOfAbsences = noOfAbsences;
	}
	
	
	public Attendance(){}
	
	public Attendance(String attendance,String date,String remarks,Classlist classlist)
	{
		setAttendance(attendance);
		setDate(date);
		setRemarks(remarks);
		setClasslist(classlist);
	}
	

}
