package com.action.login;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.helper.Utilities;
import com.opensymphony.xwork2.ActionSupport;

public class LogoutUser extends ActionSupport implements SessionAware {
	
	private Map<String, Object> userSession;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Trying to logout...");
		userSession.clear();
		userSession.remove(Utilities.user_sessionName);
		
		userSession.forEach( (k,v) -> System.out.println("Key= " + k + " Value= " + v));
		System.out.println(userSession.size());
	
		return SUCCESS;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.userSession = session;
	}
}
