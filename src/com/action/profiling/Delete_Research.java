package com.action.profiling;

import com.HibernateUtil.ProfilingHelper;
import com.model.Researches;
import com.opensymphony.xwork2.ActionSupport;

public class Delete_Research extends ActionSupport {
	
	private Researches rModel = new Researches();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub

		ProfilingHelper p_helper = new ProfilingHelper();
		try {
			p_helper.deleteResearches(rModel);
			return SUCCESS;
		} catch (Exception e) {
			return INPUT;
		}
	}
	
	public void setrModel(Researches rModel) {
		this.rModel = rModel;
	}
}
