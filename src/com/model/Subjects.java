package com.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Subjects {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SubjID")
	private int subjID;
	
	@Column(name="CourseCode",length=10)
	private String courseCode;
	
	@Column(name="Description",length=60)
	private String description;
	
	@Column(name="Units",length=10)
	private String units;
	
	
	@OneToMany(mappedBy="subjects", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Schedule> schedule;
	
	@OneToMany(mappedBy="subjects", fetch = FetchType.EAGER)
	private Set<Expertise> expertise;
	
	
	public Subjects(){}
	
	public Subjects(String courseCode,String description,String units)
	{
		setCourseCode(courseCode);
		setDescription(description);
		setUnits(units);
	}
	
	
	public int getSubjID() {
		return subjID;
	}
	public void setSubjID(int subjID) {
		this.subjID = subjID;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}

	public Set<Schedule> getSchedule() {
		return schedule;
	}

	public void setSchedule(Set<Schedule> schedule) {
		this.schedule = schedule;
	}

	public Set<Expertise> getExpertise() {
		return expertise;
	}

	public void setExpertise(Set<Expertise> expertise) {
		this.expertise = expertise;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return courseCode;
	}
	
	
}
