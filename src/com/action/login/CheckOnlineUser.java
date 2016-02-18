package com.action.login;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.helper.Utilities;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class CheckOnlineUser extends ActionSupport implements SessionAware {
	
	private Map<String, Object> userSession;
	
	/** NO getter method for the reason that it doesn't need to return the model data. Just check **/
	private Users usersModel = new Users();
	private boolean has_User = false;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		//Get currentSession
		try{
			usersModel = (Users) userSession.get(Utilities.user_sessionName);
			
			if(usersModel.getUsername() == null || usersModel.getUsername().isEmpty()){
				return INPUT;
			}
			System.out.println("Checking... ");
			System.out.println(usersModel.getUsername());
			
		}catch(Exception e){
			return INPUT;
		}
		has_User = true;
		return SUCCESS;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.userSession = session;
	}
	
	public boolean isHas_User() {
		return has_User;
	}
	
	public void setHas_User(boolean has_User) {
		this.has_User = has_User;
	}
	
	public void setUsersModel(Users usersModel) {
		this.usersModel = usersModel;
	}
}
