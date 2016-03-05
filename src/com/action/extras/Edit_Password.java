package com.action.extras;

import com.helper.HelperClass;
import com.helper.SendEmail;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class Edit_Password extends ActionSupport{
	
	private Users user = new Users();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		String password = HelperClass.encrypt("qwerty");
		
		SendEmail.sendForgotPassword(password);
		return SUCCESS;
	}
	
	public Users getUser() {
		return user;
	}
	
	public void setUser(Users user) {
		this.user = user;
	}
}
