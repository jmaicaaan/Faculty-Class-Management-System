package com.action.attendance;

import java.util.ArrayList;
import java.util.List;

import com.HibernateUtil.AttendanceHelper;
import com.model.Attendance;
import com.model.Classlist;
import com.model.FacultyAssign;
import com.model.Schedule;
import com.opensymphony.xwork2.ActionSupport;

public class View_HighChart extends ActionSupport {
	
	private List<Attendance> aList = new ArrayList<Attendance>();
	private Schedule schedObj = new Schedule();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		FacultyAssign fObcj = new FacultyAssign();
		AttendanceHelper a_helper = new AttendanceHelper();
		
		try {
			int aID = a_helper.getAssignID(schedObj.getSubjects().getCourseCode(), schedObj.getSection());
			fObcj.setAssignID(aID);
			List<Classlist> cList = a_helper.viewClassList(fObcj);
			aList =  a_helper.getHighCharts(cList);
			
			return SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return INPUT;
		}
		
	}
	
	public List<Attendance> getaList() {
		return aList;
	}
	
	public void setSchedObj(Schedule schedObj) {
		this.schedObj = schedObj;
	}
}
