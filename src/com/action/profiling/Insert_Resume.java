package com.action.profiling;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.HibernateUtil.GenericHelper;
import com.HibernateUtil.ProfilingHelper;
import com.helper.AuditLogUtil;
import com.helper.FileModel;
import com.helper.Utilities;
import com.model.AuditLog;
import com.model.ProfessorProfile;
import com.model.Resume;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class Insert_Resume extends ActionSupport implements ModelDriven<FileModel>, ServletRequestAware, SessionAware{


	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;
	private FileModel rModel = new FileModel();
	private Map<String, Object> userSession;


	@Override
	public String execute() throws Exception 
	{
		ProfilingHelper session_Helper = new ProfilingHelper();
		Users uModel = new Users();
		Resume resume = new Resume();
		GenericHelper g_helper = new GenericHelper();
		uModel = (Users) userSession.get(Utilities.user_sessionName);

		try {
			
			String serverPath = request.getServletContext().getRealPath("/");
			if(rModel.hasUploadedResume(serverPath)){
				
				ProfessorProfile professorProfile = new ProfessorProfile();
				professorProfile.setPpID(uModel.getUserID());
				resume.setProfessorProfile(professorProfile);
				resume.setResumeUrl(rModel.getUrl());

				session_Helper.addResume(resume, professorProfile);
				AuditLog auditLog = new AuditLog(AuditLogUtil.addAction, AuditLogUtil.resumeType, uModel);
				g_helper.AddAuditLog(auditLog);
				
				return SUCCESS;
			}else{
				return INPUT;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return INPUT;
		}
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
