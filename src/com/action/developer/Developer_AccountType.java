package com.action.developer;

import java.util.ArrayList;
import java.util.List;

import com.HibernateUtil.DeveloperHelper;
import com.model.AccountType;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class Developer_AccountType extends ActionSupport {
	
	private List<Users> users = new ArrayList<Users>();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		DeveloperHelper session_Helper = new DeveloperHelper();
		try{
			for(Users uModel : users){
				for(AccountType acType : uModel.getAccountType()){
					AccountType accountType = new AccountType(acType.getAccountType(), uModel);
					session_Helper.addAccountType(accountType,uModel);
				}
			}
		}catch(Exception e){
			return ERROR; 
		}
		
		return SUCCESS;
	}

	public List<Users> getUsers() {
		return users;
	}
	public void setUsers(List<Users> users) {
		this.users = users;
	}
	
	
	
}
