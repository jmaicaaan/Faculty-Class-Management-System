package com.action.scheduling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.interceptor.SessionAware;

import com.HibernateUtil.SchedulingHelper;
import com.helper.FileModel;
import com.helper.HelperClass;
import com.helper.Utilities;
import com.model.Expertise;
import com.model.ProfessorProfile;
import com.model.Schedule;
import com.model.Subjects;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class Upload_Subjects extends ActionSupport implements ModelDriven<FileModel>, SessionAware {
	
	private FileModel fModel = new FileModel();
	private Map<String, Object> userSession;
	private Set<ProfessorProfile> p_profSet = new HashSet<ProfessorProfile>();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		Users uModel = (Users)userSession.get(Utilities.user_sessionName);
		SchedulingHelper s_helper = new SchedulingHelper();
		List<Schedule> schedList = new ArrayList<Schedule>();
		Set<Expertise> comptSet = new HashSet<Expertise>();
		
		
		schedList = HelperClass.readUploadedSubjects(fModel.getFile());
		
		for(Schedule sObj : schedList){
			s_helper.addSchedule(sObj);
			List<Expertise> temp = s_helper.getExpertise(sObj.getSubjects());
			if(!temp.isEmpty()){
				comptSet.addAll(temp);
			}
		}
//		Map<Subjects, Set<ProfessorProfile>> displayMap = new HashMap<Subjects, Set<ProfessorProfile>>();

		Set<ProfessorProfile> pSet = s_helper.compatibleProfessors(comptSet);
		
		for(ProfessorProfile pp : pSet){
			System.out.println(pp.getUsers().getUsername());
			for(Expertise ex : pp.getExpertise()){
				System.out.println(ex.getSubjects().getCourseCode());
			}
		}
		
		return SUCCESS;
	}
	
	
	public Set<ProfessorProfile> getP_profSet() {
		return p_profSet;
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
