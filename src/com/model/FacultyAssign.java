package com.model;

import java.util.List;
import java.util.Set;

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
public class FacultyAssign {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="AssignID")
	private int assignID;
	
	@ManyToOne
	@JoinColumn(name="PPID")
	private ProfessorProfile professorProfile;
	
	@ManyToOne
	@JoinColumn(name="CID")
	private Schedule schedule;

	@OneToMany(mappedBy="facultyAssign", fetch = FetchType.LAZY)
	private Set<FacultyAssign> facultyAssign;
	
	@OneToMany(mappedBy="facultyAssign", fetch = FetchType.LAZY)
	private Set<Classlist> classList;
	
	public int getAssignID() {
		return assignID;
	}
	public void setAssignID(int assignID) {
		this.assignID = assignID;
	}
	public ProfessorProfile getProfessorProfile() {
		return professorProfile;
	}
	public void setProfessorProfile(ProfessorProfile professorProfile) {
		this.professorProfile = professorProfile;
	}
	public Schedule getSchedule() {
		return schedule;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	public Set<FacultyAssign> getFacultyAssign() {
		return facultyAssign;
	}
	public void setFacultyAssign(Set<FacultyAssign> facultyAssign) {
		this.facultyAssign = facultyAssign;
	}
	public Set<Classlist> getClassList() {
		return classList;
	}
	public void setClassList(Set<Classlist> classList) {
		this.classList = classList;
	}
	public FacultyAssign(){}
	
	public FacultyAssign(ProfessorProfile professorProfile,Schedule schedule){
		setProfessorProfile(professorProfile);
		setSchedule(schedule);
		
	}
}
