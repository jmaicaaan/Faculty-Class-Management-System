package com.action.attendance;

import java.util.ArrayList;
import java.util.List;

import com.HibernateUtil.AttendanceHelper;
import com.model.Attendance;
import com.model.Classlist;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class View_StudentHighChart extends ActionSupport {
	
	private Users userObj = new Users();
	private List<Attendance> aList = new ArrayList<Attendance>();

	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println(userObj.getUsername() + 1);
		AttendanceHelper a_helper = new AttendanceHelper();

		List<Classlist> cList = a_helper.getSubjects(userObj);
		aList = a_helper.studentViewAttendance(cList);
		
		return SUCCESS;
	}
	
	public void setUserObj(Users userObj) {
		this.userObj = userObj;
	}
	
	public List<Attendance> getaList() {
		return aList;
	}
}
