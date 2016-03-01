package com.action.attendance;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.HibernateUtil.AttendanceHelper;
import com.helper.AttendanceHelperClass;
import com.helper.Utilities;
import com.model.Classlist;
import com.model.FacultyAssign;
import com.model.ProfessorProfile;
import com.model.Schedule;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class Add_Student extends ActionSupport implements SessionAware{
	
	private Users users = new Users();
	private Schedule schedObj = new Schedule();
	private Map<String, Object> userSession;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		AttendanceHelper a_helper = new AttendanceHelper();
		AttendanceHelperClass a_helper_class = new AttendanceHelperClass();
		FacultyAssign facultyAssign = new FacultyAssign();
		
		ProfessorProfile professorProfile = new ProfessorProfile();
		Users userObj = (Users) userSession.get(Utilities.user_sessionName); //Professor
		
		try {
			
			professorProfile.setPpID(userObj.getUserID());
			int fid = a_helper.getAssignID(schedObj.getSubjects().getCourseCode(), schedObj.getSection(), professorProfile);
			facultyAssign.setAssignID(fid);
			
			if(a_helper.isStudentNotAdded(users)){
				a_helper_class.Student(users);
			}
			a_helper.addClassList(new Classlist(users, facultyAssign));
			
			return SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return INPUT;
		}
	}
	
	public void setUsers(Users users) {
		this.users = users;
	}
	
	public void setSchedObj(Schedule schedObj) {
		this.schedObj = schedObj;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.userSession = session;
	}
}
