package com.action.developer;

import java.util.ArrayList;
import java.util.List;

import com.HibernateUtil.DeveloperHelper;
import com.helper.HelperClass;
import com.model.AccountType;
import com.model.Password;
import com.model.ProfessorProfile;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class DeveloperAction extends ActionSupport {
	
	private List<Users> users = new ArrayList<Users>();
	
	@Override
	public String execute() throws Exception {
		
		DeveloperHelper session_Helper = new DeveloperHelper();	
		try{
			for (Users uModel : users){ 
				
				System.out.println(uModel.getUsername());
				
				//add user
				uModel.setUsername(HelperClass.CreateUsername(uModel.getFirstName(), uModel.getLastName()));
				
				// check if user is not existing
				if(session_Helper.addUser(uModel))
				{
					Password password = new Password(uModel);
					session_Helper.addPassword(password);
					
					for(AccountType acType : uModel.getAccountType()){
						AccountType accountType = new AccountType(acType.getAccountType(), uModel);
						session_Helper.addAccountType(accountType,uModel);
					}
					
					ProfessorProfile professorProfile=new ProfessorProfile(uModel);
					session_Helper.addProfessorProfile(professorProfile);
				}
			
			}
		}catch(Exception e){
			return INPUT;
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
