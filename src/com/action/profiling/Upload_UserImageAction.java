package com.action.profiling;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.HibernateUtil.ProfilingHelper;
import com.helper.Utilities;
import com.model.UploadUserImageModel;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class Upload_UserImageAction extends ActionSupport 
			implements ModelDriven<UploadUserImageModel>, ServletRequestAware, SessionAware {
	
	
	private UploadUserImageModel upiModel = new UploadUserImageModel();
	private Map<String, Object> userSession;
	private HttpServletRequest request;
	
	@Override
	public String execute() throws Exception {
	
		ProfilingHelper session_Profiling= new ProfilingHelper();
	
		Users uModel = new Users();
		String serverPath = request.getServletContext().getRealPath("/");
		upiModel.doUpload(serverPath);
		uModel = (Users) userSession.get(Utilities.user_sessionName);
		uModel.setPictureUrl(upiModel.getUrl());
		session_Profiling.updatePicture(uModel);
		
		return SUCCESS;
	}
	
	@Override
	public UploadUserImageModel getModel() {
		// TODO Auto-generated method stub
		return upiModel;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.userSession = session;
	}
}
