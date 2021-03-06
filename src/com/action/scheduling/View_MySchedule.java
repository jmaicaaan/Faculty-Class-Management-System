package com.action.scheduling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.HibernateUtil.SchedulingHelper;
import com.helper.Utilities;
import com.model.FacultyAssign;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class View_MySchedule extends ActionSupport implements SessionAware {

	private Map<String, Object> userSession;
	private List<FacultyAssign> mySchedule = new ArrayList<FacultyAssign>();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		SchedulingHelper s_helper = new SchedulingHelper();
		Users user = (Users) userSession.get(Utilities.user_sessionName);
		
		mySchedule = s_helper.viewMySchedule(user.getUsername());
		
		return SUCCESS;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.userSession = session;
	}
	
	public List<FacultyAssign> getMySchedule() {
		return mySchedule;
	}
}
