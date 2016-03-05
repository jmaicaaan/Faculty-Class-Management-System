package com.action.attendance;

import java.util.ArrayList;
import java.util.List;

import com.HibernateUtil.AttendanceHelper;
import com.model.Attendance;
import com.model.Schedule;
import com.opensymphony.xwork2.ActionSupport;

public class View_Attendance extends ActionSupport{
	
	private Schedule schedObj = new Schedule();
	private List<Attendance> aList = new ArrayList<Attendance>();
	private String date;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		AttendanceHelper a_helper = new AttendanceHelper();
		Attendance attendance = new Attendance();
		int assignID = 0;
		attendance.setDate(date);
		assignID = a_helper.getAssignID(schedObj.getSubjects().getCourseCode(), schedObj.getSection());
		aList = a_helper.viewAttendance(attendance.getDate(), assignID);
		
		aList.forEach(i -> {
			System.out.println(i.getAttendance() + i.getClasslist().getUsers().getUsername());
		});
		
		return SUCCESS;
	}
	
	public void setSchedObj(Schedule schedObj) {
		this.schedObj = schedObj;
	}
	
	public List<Attendance> getaList() {
		return aList;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
}
