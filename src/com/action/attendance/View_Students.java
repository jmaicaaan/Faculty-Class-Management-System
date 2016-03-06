package com.action.attendance;

import java.util.ArrayList;
import java.util.List;

import com.HibernateUtil.AttendanceHelper;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class View_Students extends ActionSupport {
	 
	private List<Users> uList = new ArrayList<Users>();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		AttendanceHelper a_helper = new AttendanceHelper();
		try {
			uList = a_helper.getAllStudents();
			return SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return INPUT;
		}
		
		
	}
	
	public List<Users> getuList() {
		return uList;
	}
}
