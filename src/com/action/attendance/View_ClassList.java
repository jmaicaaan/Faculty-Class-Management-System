package com.action.attendance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.HibernateUtil.AttendanceHelper;
import com.helper.Utilities;
import com.model.Classlist;
import com.model.FacultyAssign;
import com.model.ProfessorProfile;
import com.model.Schedule;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class View_ClassList extends ActionSupport implements SessionAware{
	
	private Map<String, Object> userSession;
	private Schedule schedObj = new Schedule();
	private List<Classlist> classList = new ArrayList<Classlist>();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		AttendanceHelper a_helper = new AttendanceHelper();
		ProfessorProfile professorProfile = new ProfessorProfile();
		FacultyAssign fa = new FacultyAssign();
		
		System.out.println(schedObj.getSection());
		
		try {
			Users uModel = (Users) userSession.get(Utilities.user_sessionName);
			professorProfile.setPpID(uModel.getUserID());
			fa.setAssignID(a_helper.getAssignID(schedObj.getSubjects().getCourseCode(), 
					schedObj.getSection(), professorProfile));
			
			classList = a_helper.viewClassList(fa);
			
			return SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return INPUT;
		}
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.userSession = session;
	}
	
	public void setSchedObj(Schedule schedObj) {
		this.schedObj = schedObj;
	}
	
	public List<Classlist> getClassList() {
		return classList;
	}
}
