package com.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Schedule {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CID")
	private int cID;
	
	@Column(name="Room",length=10)
	private String room;
	
	@Column(name="Day",length=10)
	private String day;
	
	@Column(name="Section",length=10)
	private String section;
	
	@Column(name="Time",length=30)
	private String time;
	
	@ManyToOne
	@JoinColumn(name="SubjID")
	private Subjects subjects;
	
	
	@OneToMany(mappedBy="schedule", fetch = FetchType.EAGER)
	private List<FacultyAssign> facultyAssign;
	
	
	
	public List<FacultyAssign> getFacultyAssign() {
		return facultyAssign;
	}

	public void setFacultyAssign(List<FacultyAssign> facultyAssign) {
		this.facultyAssign = facultyAssign;
	}

	public Subjects getSubjects() {
		return subjects;
	}

	public void setSubjects(Subjects subjects) {
		this.subjects = subjects;
	}

	public int getcID() {
		return cID;
	}

	public void setcID(int cID) {
		this.cID = cID;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	
	public Schedule(){}
	
	public Schedule(String room,String day,String time,String section,Subjects subjects)
	{
		setRoom(room);
		setDay(day);
		setTime(time);
		setSection(section);
		setSubjects(subjects);
	}
	
	
	
	

}
