package com.action.attendance;

import java.util.ArrayList;
import java.util.List;

import com.HibernateUtil.AttendanceHelper;
import com.helper.AttendanceHelperClass;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class Save_Attendance extends ActionSupport {
	
	List<Users> absentList = new ArrayList<Users>();
	List<Users> lateList = new ArrayList<Users>();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		try {
			absentList.forEach(i -> {
				System.out.println(i.getIdNo());
			});
			
			lateList.forEach(i -> {
				System.out.println(i.getIdNo());
			});
			
			AttendanceHelper a_helper = new AttendanceHelper();
			AttendanceHelperClass a_helper_class = new AttendanceHelperClass();
			
			
			return SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			return INPUT;
		}
	}
	
	public void setAbsentList(List<Users> absentList) {
		this.absentList = absentList;
	}
	public void setLateList(List<Users> lateList) {
		this.lateList = lateList;
	}
}
