package com.action.profiling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.struts2.interceptor.SessionAware;

import com.HibernateUtil.ProfilingHelper;
import com.helper.Utilities;
import com.model.ProfessorProfile;
import com.model.Resume;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class View_Resume extends ActionSupport implements SessionAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private Set<Resume> rSet = new HashSet<Resume>();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		ProfilingHelper p_helper = new ProfilingHelper();
		ProfessorProfile p_profile = new ProfessorProfile();
		Users uModel = new Users();
		try {
			uModel = (Users) userSession.get(Utilities.user_sessionName);
			p_profile.setPpID(uModel.getUserID());
			rSet = p_helper.viewResume(p_profile)
						.stream().collect(Collectors.toSet());
			
		} catch (Exception e) {
			// TODO: handle exception
			return INPUT;
		}
		return SUCCESS;
	}
	
	public Set<Resume> getrSet() {
		return rSet;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.userSession = session;
	}
}
