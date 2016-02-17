package com.action.profiling;

import com.HibernateUtil.ProfilingHelper;
import com.model.Resume;
import com.opensymphony.xwork2.ActionSupport;

public class Delete_Resume extends ActionSupport {
	
	private Resume rModel = new Resume();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println(rModel.getRID());
		ProfilingHelper p_helper = new ProfilingHelper();
		try {
			p_helper.deleteResume(rModel);
			return SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			return INPUT;
		}
		
	}
	
	public void setrModel(Resume rModel) {
		this.rModel = rModel;
	}
}	
