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
//	private Set<ProfessorProfile> p_profSet = new HashSet<ProfessorProfile>();
	static List<Expertise> expList = new ArrayList<Expertise>();

	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		Users uModel = (Users)userSession.get(Utilities.user_sessionName);
		SchedulingHelper s_helper = new SchedulingHelper();
		List<Schedule> schedList = new ArrayList<Schedule>();
		List<Expertise> expList = new ArrayList<Expertise>();
		
		schedList = HelperClass.readUploadedSubjects(fModel.getFile());
		
		//init 
		s_helper.renewScheduleTable();
		
		for(Schedule sObj : schedList){
			if(s_helper.is_ISSubject(sObj.getSubjects())){
				s_helper.addSchedule(sObj);
				
				expList.addAll(s_helper.getTableExpertise(sObj));
			}
		}
		fModel.setResponse(expList);
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
