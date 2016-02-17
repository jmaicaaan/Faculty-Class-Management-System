package com.action.profiling;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.struts2.interceptor.SessionAware;

import com.HibernateUtil.ProfilingHelper;
import com.helper.Utilities;
import com.model.ProfessorProfile;
import com.model.Projects;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class View_Projects extends ActionSupport implements SessionAware{
	
	private Map<String, Object> userSession;
	private Set<Projects> pSet = new HashSet<Projects>();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		ProfilingHelper p_helper = new ProfilingHelper();
		ProfessorProfile p_profile = new ProfessorProfile();
		Users uModel = new Users();
		try{
			uModel = (Users) userSession.get(Utilities.user_sessionName);
			p_profile.setPpID(uModel.getUserID());
			
			//Much better to read?
			pSet = p_helper.viewProjects(p_profile)
						.stream().collect(Collectors.toSet());
			
			
		}catch(Exception e){
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	public Set<Projects> getpSet() {
		return pSet;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.userSession = session;
	}
}
