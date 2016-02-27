package com.action.attendance;

import java.util.ArrayList;
import java.util.List;

import com.HibernateUtil.AttendanceHelper;
import com.helper.AttendanceHelperClass;
import com.helper.FileModel;
import com.model.Classlist;
import com.model.FacultyAssign;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class Insert_ClassList extends ActionSupport implements ModelDriven<FileModel> {
	
	private FileModel fModel = new FileModel();

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		AttendanceHelperClass a_helperClass = new AttendanceHelperClass();
		AttendanceHelper a_helper = new AttendanceHelper();
		FacultyAssign facAssign = new FacultyAssign();
		List<Users> uList = new ArrayList<Users>();
		String subject = fModel.getRequest().split(",")[0],
				section = fModel.getRequest().split(",")[1];
		
	    int assignID = a_helper.getAssignID(subject, section);
	    facAssign.setAssignID(assignID);
	    
		
		uList = a_helperClass.readUploadedClasslist(fModel.getFile());
		
		uList.forEach(i -> {
			if(a_helper.isStudentNotAdded(i)){
				a_helper.addStudent(a_helperClass.Student(i));
			}
		
			a_helper.addClassList(new Classlist(i, facAssign));
		});
		
		return SUCCESS;
	}
	
	@Override
	public FileModel getModel() {
		// TODO Auto-generated method stub
		return fModel;
	}
}
