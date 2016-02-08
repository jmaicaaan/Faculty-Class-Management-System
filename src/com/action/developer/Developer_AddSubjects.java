package com.action.developer;

import java.util.ArrayList;
import java.util.List;

import com.HibernateUtil.DeveloperHelper;
import com.model.Subjects;
import com.opensymphony.xwork2.ActionSupport;

public class Developer_AddSubjects extends ActionSupport {
	
	
	private List<Subjects> subjects = new ArrayList<Subjects>();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		DeveloperHelper session_Helper = new DeveloperHelper();
		try{
			for(Subjects subj : subjects){
				session_Helper.addSubjects(subj);
			}
		}catch(Exception e){
			return INPUT;
		}
		return SUCCESS;
	}
	
	public List<Subjects> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<Subjects> subjects) {
		this.subjects = subjects;
	}
	
	
}
