package com.action.extras;

import com.HibernateUtil.GenericHelper;
import com.helper.HelperClass;
import com.helper.SendEmail;
import com.helper.Utilities;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class Edit_Password extends ActionSupport{
	
	private Users uObj = new Users();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		try {
			GenericHelper g_helper = new GenericHelper();
			String password = uObj.getUsername().length() > 5 ? 
					Utilities.password : Utilities.password1;
			
			if(g_helper.edit_password(uObj)){
				SendEmail.sendForgotPassword(password);
			}
					
					
			
			
			return SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			return INPUT;
		}
		
		
	}
	
	public void setuObj(Users uObj) {
		this.uObj = uObj;
	}
}
