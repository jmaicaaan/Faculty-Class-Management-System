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
import com.helper.SchedulingHelperClass;
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
//	private Set<ProfessorProfile> p_profSet = new HashSet<ProfessorProfile>();
	
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		Users uModel = (Users)userSession.get(Utilities.user_sessionName);
		SchedulingHelper s_helper = new SchedulingHelper();
		List<Schedule> schedList = new ArrayList<Schedule>();
		Set<Expertise> comptSet = new HashSet<Expertise>(); //Subjects with preferred.
		Map<Subjects, Set<ProfessorProfile>> displayMap = new HashMap<Subjects, Set<ProfessorProfile>>(); 
		
		schedList = HelperClass.readUploadedSubjects(fModel.getFile());
		
		for(Schedule sObj : schedList){
			s_helper.addSchedule(sObj);
			Set<Expertise> temp = s_helper.getExpertise(sObj.getSubjects());
			if(!temp.isEmpty()){
				comptSet.addAll(temp);
			}
		}
		
		Set<ProfessorProfile> pSet = s_helper.compatibleProfessors(comptSet);

		displayMap = SchedulingHelperClass.createDisplayMap(pSet);
		SchedulingHelperClass.displayMap(displayMap);
		fModel.setResponse(displayMap);
//		
//		for(Schedule sObj : schedList){
//			s_helper.addSchedule(sObj);
////			Set<Expertise> temp = s_helper.getExpertise(sObj.getSubjects());
////			
//////			comptSet = s_helper.getExpertise(sObj.getSubjects()) != null ? s_helper.getExpertise(sObj.getSubjects()) : new HashSet<>();
//////			for(Expertise e : temp){
//////				System.out.print(e.getSubjects().getCourseCode());
//////				for(Schedule s : e.getSubjects().getSchedule()){
//////					System.out.println(" " + s.getTime() + " = " + s.getSection());
//////				}
//////				System.out.println();
//////			}
////			
////			if(!temp.isEmpty()){
////				comptSet.addAll(temp);
////			}
//		}
		
		
		
		
		//Don't move this to the top!
		
//		Set<ProfessorProfile> pSet = s_helper.compatibleProfessors(comptSet);
//		
//		displayMap = SchedulingHelperClass.createDisplayMap(pSet);
//		SchedulingHelperClass.displayMap(displayMap);
//		fModel.setResponse(displayMap);
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
