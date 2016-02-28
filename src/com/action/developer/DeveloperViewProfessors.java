package com.action.developer;

import java.util.ArrayList;
import java.util.List;

import com.HibernateUtil.DeveloperHelper;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class DeveloperViewProfessors extends ActionSupport {
	
	private List<Users> users = new ArrayList<Users>();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		DeveloperHelper session_Helper = new DeveloperHelper();
		users = session_Helper.viewAllProfessors();
		
		return SUCCESS;
		
	}

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}
	
	
	
}
