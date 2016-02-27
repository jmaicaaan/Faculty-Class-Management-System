package com.action.scheduling;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.ServletContextAware;

import com.HibernateUtil.SchedulingHelper;
import com.helper.PDFGenerator;
import com.model.FacultyAssign;
import com.opensymphony.xwork2.ActionSupport;

public class Generate_PDF extends ActionSupport implements ServletContextAware {
	
	private InputStream inputStream;
	private String inputStreamName;
	private ServletContext context;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		SchedulingHelper s_helper = new SchedulingHelper();
		List<FacultyAssign> faList = new ArrayList<FacultyAssign>();
		PDFGenerator pdf = new PDFGenerator();

		faList = s_helper.Generate_AssignedProfessors();
		context = ServletActionContext.getServletContext();
		String serverPath = context.getRealPath("/");

		File file = new File(pdf.generateAssignedProfessorPDF(faList, serverPath));
		inputStream = new FileInputStream(file);
		inputStreamName = file.getName();
		
		return SUCCESS;
	}
	
	@Override
	public void setServletContext(ServletContext context) {
		// TODO Auto-generated method stub
		this.context = context;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public String getInputStreamName() {
		return inputStreamName;
	}
	

}
