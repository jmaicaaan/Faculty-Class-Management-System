package com.action.attendance;

import com.opensymphony.xwork2.ActionSupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.ServletContextAware;

import com.helper.Zip_Helper;
import com.model.Attendance;
import com.model.Users;
import com.HibernateUtil.AttendanceHelper;
import com.helper.PDFGenerator;

public class Download_PDFStudent extends ActionSupport implements ServletContextAware{
	
	private int userID = 0;
	private ServletContext context;
	private InputStream inputStream;
	private String inputStreamName; 

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		Users uModel = new Users();
		uModel.setIdNo(String.valueOf(userID));
		System.out.println(userID);
		AttendanceHelper a_helper = new AttendanceHelper();
		List<Attendance> aList = a_helper.displayAttendancetoPDF(uModel);
		
		PDFGenerator pdf = new PDFGenerator();
		context = ServletActionContext.getServletContext();
		String serverPath = context.getRealPath("/");	
		File file = new File(pdf.generateAchievementsPDF(serverPath, aList));
		inputStream = new FileInputStream(file);
		inputStreamName = file.getName();
		
		
		return SUCCESS;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
		
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getInputStreamName() {
		return inputStreamName;
	}

	public void setInputStreamName(String inputStreamName) {
		this.inputStreamName = inputStreamName;
	}

	public int getUserID() {
		return userID;
	}
	
	
}
