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
public class Classlist {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ClassID")
	private int classID;
	
	@ManyToOne
	@JoinColumn(name="AssignID")
	private FacultyAssign facultyAssign;
	
	@ManyToOne
	@JoinColumn(name="UserID")
	private Users users;
	
	
	@OneToMany(mappedBy="classlist", fetch=FetchType.LAZY)
	private List<Attendance>attendance;

	public List<Attendance> getAttendance() {
		return attendance;
	}

	public void setAttendance(List<Attendance> attendance) {
		this.attendance = attendance;
	}

	public int getClassID() {
		return classID;
	}

	public void setClassID(int classID) {
		this.classID = classID;
	}

	public FacultyAssign getFacultyAssign() {
		return facultyAssign;
	}

	public void setFacultyAssign(FacultyAssign facultyAssign) {
		this.facultyAssign = facultyAssign;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
	
	
	public Classlist (){}
	
	public Classlist(Users users, FacultyAssign facultyassign){
		setUsers(users);
		setFacultyAssign(facultyassign);
	}
	
}
