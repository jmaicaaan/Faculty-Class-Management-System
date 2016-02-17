package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Expertise {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int expertID;
	
	
	@ManyToOne
	@JoinColumn(name="SubjID")
	private Subjects subjects;
	
	
	@ManyToOne
	@JoinColumn(name="PPID")
	private ProfessorProfile professorProfile;
	
	public int getExpertID() {
		return expertID;
	}
	public void setExpertID(int expertID) {
		this.expertID = expertID;
	}
	public Subjects getSubjects() {
		return subjects;
	}
	public void setSubjects(Subjects subjects) {
		this.subjects = subjects;
	}
	public ProfessorProfile getProfessorProfile() {
		return professorProfile;
	}
	public void setProfessorProfile(ProfessorProfile professorProfile) {
		this.professorProfile = professorProfile;
	}
	public Expertise(){}
	
	public Expertise(Subjects subjects,ProfessorProfile professorProfile)
	{
		setSubjects(subjects);
		setProfessorProfile(professorProfile);
	}

	

	
}
