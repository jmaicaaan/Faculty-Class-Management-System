package com.action.scheduling;

import java.util.ArrayList;
import java.util.List;

import com.HibernateUtil.SchedulingHelper;
import com.model.FacultyAssign;
import com.opensymphony.xwork2.ActionSupport;

public class Generate_AssignedFaculty extends ActionSupport {
	
	private List<FacultyAssign> faList = new ArrayList<FacultyAssign>();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		SchedulingHelper s_helper = new SchedulingHelper();
		
		faList = s_helper.viewAssignedFaculty();
		
		faList.forEach(i -> {
			System.out.println(i.getProfessorProfile().getUsers().getUsername());
			System.out.println(i.getSchedule().getSubjects().getCourseCode());
		});
		
		
		
		
		return SUCCESS;
	}
	
	public List<FacultyAssign> getFaList() {
		return faList;
	}
	
	
}
