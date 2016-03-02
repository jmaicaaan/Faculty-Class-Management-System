package com.action.secretary;

import java.util.ArrayList;
import java.util.List;

import com.HibernateUtil.SecretaryHelper;
import com.model.ProfessorProfile;
import com.opensymphony.xwork2.ActionSupport;

public class View_Professors extends ActionSupport{
	
	List<ProfessorProfile> pList = new ArrayList<ProfessorProfile>();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		SecretaryHelper s_helper = new SecretaryHelper();
		
		pList = s_helper.view_Professors();
		
		
		return SUCCESS;
	}
	public List<ProfessorProfile> getpList() {
		return pList;
	}
	
}
