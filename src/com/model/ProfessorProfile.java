package com.model;

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
public class ProfessorProfile {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PPID")
	private int ppID;
	
	@ManyToOne
	@JoinColumn(name="UserID")
	private Users users;
	
	@OneToMany(mappedBy="ProfessorProfile", fetch = FetchType.LAZY)
	private Set<Achievements> achievements;

	
	@OneToMany(mappedBy="ProfessorProfile", fetch = FetchType.LAZY)
	private Set<Resume> resume;
	
	@OneToMany(mappedBy="professorProfile", fetch = FetchType.LAZY)
	private Set<Researches> researches;
	
	@OneToMany(mappedBy="professorProfile", fetch = FetchType.LAZY)
	private Set<Projects> projects;
	
	
	@OneToMany(mappedBy="professorProfile", fetch = FetchType.LAZY)
	private Set<FacultyAssign> facultyAssign;
	
	@OneToMany(mappedBy="professorProfile", fetch = FetchType.LAZY)
	private Set<Expertise> expertise;
	
	public ProfessorProfile(){}
	
	
	public ProfessorProfile(Users users)
	{
		setUsers(users);
	}
	
	public int getPpID() {
		return ppID;
	}

	public void setPpID(int ppID) {
		this.ppID = ppID;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}


	public Set<Achievements> getAchievements() {
		return achievements;
	}


	public void setAchievements(Set<Achievements> achievements) {
		this.achievements = achievements;
	}


	public Set<Resume> getResume() {
		return resume;
	}


	public void setResume(Set<Resume> resume) {
		this.resume = resume;
	}


	public Set<Researches> getResearches() {
		return researches;
	}


	public void setResearches(Set<Researches> researches) {
		this.researches = researches;
	}


	public Set<Projects> getProjects() {
		return projects;
	}


	public void setProjects(Set<Projects> projects) {
		this.projects = projects;
	}


	public Set<FacultyAssign> getFacultyAssign() {
		return facultyAssign;
	}


	public void setFacultyAssign(Set<FacultyAssign> facultyAssign) {
		this.facultyAssign = facultyAssign;
	}


	public Set<Expertise> getExpertise() {
		return expertise;
	}


	public void setExpertise(Set<Expertise> expertise) {
		this.expertise = expertise;
	}

	
	
}
