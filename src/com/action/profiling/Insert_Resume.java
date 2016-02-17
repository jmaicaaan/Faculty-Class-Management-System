package com.action.profiling;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.HibernateUtil.ProfilingHelper;
import com.helper.FileModel;
import com.helper.Utilities;
import com.model.ProfessorProfile;
import com.model.Resume;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class Insert_Resume extends ActionSupport implements ModelDriven<FileModel>, ServletRequestAware, SessionAware{
	
	
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private FileModel rModel = new FileModel();
	private Resume resume=new Resume();

	private Map<String, Object> userSession;
	
	
	@Override
	public String execute() throws Exception 
	{
		ProfilingHelper session_Helper=new ProfilingHelper();
		Users uModel = new Users();
		uModel = (Users)userSession.get(Utilities.user_sessionName);
		
		String serverPath = request.getServletContext().getRealPath("/");
		rModel.doUpload(serverPath);
		
		ProfessorProfile professorProfile=new ProfessorProfile();
		professorProfile.setPpID(uModel.getUserID());
		resume.setProfessorProfile(professorProfile);
		resume.setResumeUrl(rModel.getUrl());
		
		session_Helper.addResume(resume, professorProfile);
		return SUCCESS;
	}
	
	
	
	@Override
	public FileModel getModel() {
		return rModel;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.userSession = session;
	}
	

}
