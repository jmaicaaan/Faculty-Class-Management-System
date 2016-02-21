package com.action.scheduling;

import java.util.ArrayList;
import java.util.List;

import com.HibernateUtil.SchedulingHelper;
import com.model.FacultyAssign;
import com.opensymphony.xwork2.ActionSupport;

public class Assign_Faculty extends ActionSupport{
	
	private List<FacultyAssign> fModel = new ArrayList<FacultyAssign>();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		SchedulingHelper s_helper = new SchedulingHelper();
		s_helper.addFacultyAssign(fModel);

		return SUCCESS;
	}
	
	public void setfModel(List<FacultyAssign> fModel) {
		this.fModel = fModel;
	}
	
}
