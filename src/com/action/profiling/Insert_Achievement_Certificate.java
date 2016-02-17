package com.action.profiling;

import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.HibernateUtil.ProfilingHelper;
import com.helper.Utilities;
import com.model.Achievement_Certificate_Model;
import com.model.Achievements;
import com.model.ProfessorProfile;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class Insert_Achievement_Certificate extends ActionSupport 
		implements ModelDriven<Achievement_Certificate_Model>, ServletContextAware, SessionAware {
	
	private Achievement_Certificate_Model acModel = new Achievement_Certificate_Model();
	
	
	private ServletContext context;
	private Map<String, Object> userSession;
	
	@Override
	public String execute() throws Exception {
		
		ProfilingHelper p_Helper = new ProfilingHelper();
		ProfessorProfile professorProfile=new ProfessorProfile();
		Users uModel = new Users();
		try
		{
			
			context = ServletActionContext.getServletContext();
			String serverPath = context.getRealPath("/");
			System.out.println(serverPath);
			acModel.doUpload(serverPath);
			
			
			uModel = (Users) userSession.get(Utilities.user_sessionName);
			
			professorProfile.setPpID(uModel.getUserID());
			
			Achievements achievements = new 
						Achievements(acModel.getAchievement_Certificate_Name(), acModel.getAchievement_Certificate_Url(),professorProfile);
			
			p_Helper.addAchievements(achievements);
		}
		catch(Exception e)
		{
			return INPUT;
			
		}
		
		return SUCCESS;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.userSession = session;
	}

	@Override
	public Achievement_Certificate_Model getModel() {
		// TODO Auto-generated method stub
		return acModel;
	}

	@Override
	public void setServletContext(ServletContext context) {
		// TODO Auto-generated method stub
		this.context = context;
	}
	
	

}
