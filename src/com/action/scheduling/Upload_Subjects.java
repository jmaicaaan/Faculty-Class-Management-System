package com.action.scheduling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.HibernateUtil.SchedulingHelper;
import com.helper.FileModel;
import com.helper.HelperClass;
import com.helper.Utilities;
import com.model.Expertise;
import com.model.Schedule;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class Upload_Subjects extends ActionSupport implements ModelDriven<FileModel>, SessionAware {
	
	private FileModel fModel = new FileModel();
	private Map<String, Object> userSession;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		Users uModel = (Users)userSession.get(Utilities.user_sessionName);
		SchedulingHelper s_helper = new SchedulingHelper();
		List<Schedule> schedList = new ArrayList<Schedule>();
		List<Expertise> exp = new ArrayList<Expertise>();
		schedList = HelperClass.readUploadedSubjects(fModel.getFile());
		
		for(Schedule sObj : schedList){
			s_helper.addSchedule(sObj);
			System.out.println(sObj.getSubjects().getCourseCode());
			exp.addAll(s_helper.matchExpertise(sObj.getSubjects()));
//			subj.addAll(s_helper.matchExpertise(sObj.getSubjects()));
//			subj.add(s_helper.matchExpertise(sObj))
//			s_helper.compatibleExpertise(sObj.getSubjects());
		}
		s_helper.compatibleProfessors(exp).forEach(i -> 
			System.out.println(i.getUsers().getLastName() + i.getUsers().getFirstName()));
//		s_helper.displaySchedules(schedList).forEach(i -> System.out.println(i.getSubjects().getCourseCode()));
		return SUCCESS;
	}

	@Override
	public FileModel getModel() {
		// TODO Auto-generated method stub
		return fModel;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.userSession = session;
	}
	
}
