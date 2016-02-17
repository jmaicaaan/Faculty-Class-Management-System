package com.action.profiling;

import java.util.ArrayList;
import java.util.List;

import com.HibernateUtil.ProfilingHelper;
import com.helper.HelperClass;
import com.model.Expertise;
import com.model.ProfessorProfile;
import com.model.Subjects;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class Update_UserProfileAction extends ActionSupport {
	
	private Users uModel = new Users();
	private List<Subjects> subjects = new ArrayList<Subjects>();
	
	@Override
	public String execute() throws Exception {

		ProfilingHelper p_helper = new ProfilingHelper();
		ProfessorProfile professorProfile = new ProfessorProfile();
		Expertise expertise = null;

			//Update profile
		uModel.setUsername(HelperClass.CreateUsername(uModel.getFirstName(), uModel.getLastName()));
		p_helper.updateUserProfile(uModel);
		
		professorProfile.setUsers(uModel);

		for(Subjects subj: subjects){
			
			professorProfile.setPpID(uModel.getUserID());
			expertise = new Expertise(subj, professorProfile);
			p_helper.addExpertise(expertise, professorProfile);
		}
		
		return SUCCESS;
		
	}
	
	
	public Users getuModel() {
		return uModel;
	}
	public void setuModel(Users uModel) {
		this.uModel = uModel;
	}
	
	public List<Subjects> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<Subjects> subjects) {
		this.subjects = subjects;
	}
}
