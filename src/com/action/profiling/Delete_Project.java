package com.action.profiling;

import com.HibernateUtil.ProfilingHelper;
import com.model.Projects;
import com.opensymphony.xwork2.ActionSupport;

public class Delete_Project extends ActionSupport {
	
	private Projects pModel = new Projects();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println(pModel.getPrID());
		ProfilingHelper p_helper = new ProfilingHelper();
		try {
			p_helper.deleteProjects(pModel);
			return SUCCESS;
		} catch (Exception e) {
			return INPUT;
		}
	}
	
	public void setpModel(Projects pModel) {
		this.pModel = pModel;
	}
}
