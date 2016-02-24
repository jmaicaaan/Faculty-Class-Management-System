package com.action.login;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.HibernateUtil.LoginHelper;
import com.helper.HelperClass;
import com.helper.Utilities;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class Update_CurrentSessionAction extends ActionSupport implements SessionAware{
	
	private Users uModel = new Users();
	private Map<String, Object> userSession;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		try{
			uModel = (Users) userSession.get(Utilities.user_sessionName);
			LoginHelper loginHelper = new LoginHelper();
			
			if(uModel.getUsername().equals(Utilities.adminUsername)){
				uModel = HelperClass.Admin();
			}else{
				uModel = loginHelper.getUserDetails(uModel.getUserID());
			}
		}catch(Exception e){
			return INPUT;
		}
		return SUCCESS;
	}
	
	
	public Users getUser() {
		return uModel;
	}
	public void setUser(Users user) {
		this.uModel = user;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.userSession = session;
	}
	
}
