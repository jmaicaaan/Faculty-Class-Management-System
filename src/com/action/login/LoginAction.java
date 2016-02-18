package com.action.login;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.HibernateUtil.LoginHelper;
import com.helper.Utilities;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements SessionAware {

	
	private Users usersModel = new Users();
	private Map<String, Object> userSession;
	
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		userSession.clear();
		userSession.remove(Utilities.user_sessionName);
		
		LoginHelper loginHelper = new LoginHelper();
		try{
			String username = usersModel.getUsername(),
					password = usersModel.getPassword().get(0).getPassword();

			usersModel = loginHelper.loginUser(username, password);
			
			if(usersModel == null){
				return INPUT;
			}
//			loginHelper.getUserDetails(usersModel.getUserID());
			userSession.put(Utilities.user_sessionName, usersModel);
			return SUCCESS;
		}catch(Exception e){
			return INPUT;
		}
	
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.userSession = session;
	}
	
	public Users getUsersModel() {
		return usersModel;
	}
	public void setUsersModel(Users usersModel) {
		this.usersModel = usersModel;
	}


}
