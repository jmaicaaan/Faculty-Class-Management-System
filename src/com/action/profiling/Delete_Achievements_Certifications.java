package com.action.profiling;

import com.HibernateUtil.ProfilingHelper;
import com.model.Achievements;
import com.opensymphony.xwork2.ActionSupport;

public class Delete_Achievements_Certifications extends ActionSupport {
	
	private Achievements aModel = new Achievements();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		ProfilingHelper p_helper = new ProfilingHelper();
		try{
			p_helper.deleteAchievements(aModel);
			return SUCCESS;
		}catch(Exception e){
			return INPUT;
		}
		
		
		
	}
	public void setaModel(Achievements aModel) {
		this.aModel = aModel;
	}
}
