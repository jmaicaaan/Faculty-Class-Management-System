package com.action.profiling;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.HibernateUtil.ProfilingHelper;
import com.helper.Utilities;
import com.model.ProfessorProfile;
import com.model.Projects;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class Insert_Project extends ActionSupport implements SessionAware {
	
	private Projects pModel = new Projects();
	private Map<String, Object> userSession;
	
	@Override
	public String execute() throws Exception 
	{
		ProfilingHelper session_Helper=new ProfilingHelper();
		Users uModel = new Users();
		try
		{
			System.out.println(pModel.getProjectName());
			uModel = (Users) userSession.get(Utilities.user_sessionName);
			
			ProfessorProfile professorProfile=new ProfessorProfile();
			professorProfile.setPpID(uModel.getUserID());
			pModel.setProfessorProfile(professorProfile);
			
			
			session_Helper.addProjects(pModel);
		}
		catch(Exception e)
		{
			return INPUT;
		}
		return SUCCESS;
	}


//	@Override
//	public Projects getModel() {
//		// TODO Auto-generated method stub
//		return pModel;
//	}
	
	

	public Projects getpModel() {
		return pModel;
	}


	public void setpModel(Projects pModel) {
		this.pModel = pModel;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.userSession = session;
	}
	
}
