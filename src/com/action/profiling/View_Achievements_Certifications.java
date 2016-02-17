package com.action.profiling;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.struts2.interceptor.SessionAware;

import com.HibernateUtil.ProfilingHelper;
import com.helper.Utilities;
import com.model.Achievements;
import com.model.ProfessorProfile;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class View_Achievements_Certifications extends ActionSupport implements SessionAware{
	
	private Map<String, Object> userSession;
	private Set<Achievements> aSet = new HashSet<Achievements>();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		ProfilingHelper p_helper = new ProfilingHelper();
		ProfessorProfile p_profile = new ProfessorProfile();
		Users uModel = new Users();
		
		try{
		
			uModel = (Users) userSession.get(Utilities.user_sessionName);
			p_profile.setPpID(uModel.getUserID());
			
			aSet = p_helper.viewAchievements(p_profile)
						.stream().collect(Collectors.toSet());		
		}catch(Exception e){
			return INPUT;
		}
		return SUCCESS;
	}
	
	public Set<Achievements> getaSet() {
		return aSet;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.userSession = session;
	}
	

	
	
	
	
}
