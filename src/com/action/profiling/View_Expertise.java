package com.action.profiling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.struts2.interceptor.SessionAware;

import com.HibernateUtil.ProfilingHelper;
import com.helper.Utilities;
import com.model.Expertise;
import com.model.ProfessorProfile;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class View_Expertise extends ActionSupport implements SessionAware{
	
	private Map<String, Object> userSession;
	
	private Set<Expertise> eSet = new HashSet<Expertise>();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		Users uModel = (Users) userSession.get(Utilities.user_sessionName);
		ProfessorProfile professorProfile = new ProfessorProfile();
		ProfilingHelper p_helper = new ProfilingHelper();
		
		try {
			professorProfile.setPpID(uModel.getUserID());
			eSet = p_helper.viewExpertise(professorProfile);
		} catch (Exception e) {
			return INPUT;
		}
		return SUCCESS;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.userSession = session;
	}
	
	public Set<Expertise> geteSet() {
		return eSet;
	}
	

}
