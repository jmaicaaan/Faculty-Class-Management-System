package com.action.profiling;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.helper.PDFGenerator;
import com.helper.Utilities;
import com.model.ProfessorProfile;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

public class Generate_PDF extends ActionSupport implements SessionAware, ServletContextAware {
	
	private Map<String, Object> userSession;
	private InputStream inputStream;
	private String inputStreamName;
	private ServletContext context;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		ProfessorProfile professorProfile = new ProfessorProfile();
		Users uModel = (Users) userSession.get(Utilities.user_sessionName);
		
		try {
			context = ServletActionContext.getServletContext();
			String serverPath = context.getRealPath("/");
			professorProfile.setPpID(uModel.getUserID());
			PDFGenerator pdf = new PDFGenerator(uModel, professorProfile);
			File file = new File(pdf.generateProfessorPDF(serverPath));
			inputStream = new FileInputStream(file);
			inputStreamName = file.getName();
			
		} catch (Exception e) {
			// TODO: handle exception
			return INPUT;
		}
		return SUCCESS;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.userSession = session;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public String getInputStreamName() {
		return inputStreamName;
	}

	@Override
	public void setServletContext(ServletContext context) {
		// TODO Auto-generated method stub
		this.context = context;
		
	}
}
