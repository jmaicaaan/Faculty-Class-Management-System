package com.action.profiling;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.HibernateUtil.ProfilingHelper;
import com.helper.Utilities;
import com.model.Expertise;
import com.model.ProfessorProfile;
import com.model.Subjects;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class Delete_Subject extends ActionSupport implements SessionAware{
	
	private Subjects subject = new Subjects();
	private Map<String, Object> userSession;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		Users uModel = (Users) userSession.get(Utilities.user_sessionName);
		ProfilingHelper p_helper = new ProfilingHelper();
		ProfessorProfile professorProfile = new ProfessorProfile();
		
		professorProfile.setPpID(uModel.getUserID());
		Expertise expertise = new Expertise(subject, professorProfile);
		p_helper.deleteExpertise(expertise);
		
		
		return SUCCESS;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.userSession = session;
	}

	public Subjects getSubject() {
		return subject;
	}

	public void setSubject(Subjects subject) {
		this.subject = subject;
	}
	
	

	
	
	
}
