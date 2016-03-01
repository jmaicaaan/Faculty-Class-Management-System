package com.action.attendance;

import com.HibernateUtil.AttendanceHelper;
import com.model.Classlist;
import com.opensymphony.xwork2.ActionSupport;

public class Delete_Student extends ActionSupport {
	
	private Classlist clObj = new Classlist();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		AttendanceHelper a_helper = new AttendanceHelper();
		
		try {
			a_helper.deleteStudent(clObj);
			
			return SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			return INPUT;
		}

	}
	
	public void setClObj(Classlist clObj) {
		this.clObj = clObj;
	}
}
