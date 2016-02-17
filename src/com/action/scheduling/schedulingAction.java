package com.action.scheduling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.opensymphony.xwork2.ActionSupport;
import com.HibernateUtil.SchedulingHelper;
import com.model.Subjects;
import com.model.Schedule;

public class schedulingAction extends ActionSupport{

	private File fileUpload;
	private String fileUploadContentType;
	private String fileUploadFileName;
	private SchedulingHelper session_Helper=new SchedulingHelper();
	
		
	@Override
	public String execute() throws Exception{
		
		try{
			BufferedReader br=new BufferedReader(new FileReader(fileUpload));
			 String scheduleRow = null;
		 //with Commas
		    while ((scheduleRow = br.readLine()) != null) {
		      String[] splitSchedule = scheduleRow.split(",");

		      String courseCode = splitSchedule[0],
		    		  description = splitSchedule[1],
		    		  units = splitSchedule[2],
		    		  room = splitSchedule[3],
		    		  day = splitSchedule[4],
		    		  section = splitSchedule[5],
		    		  time = splitSchedule[6];
		      
		      
		      for(int i = 0; i <= 6; i++){
		    	  System.out.println(splitSchedule[i] + " = " + splitSchedule[i].length());
		      }

		      
		      Subjects subject = new Subjects(courseCode, description, units);	   
		      subject = session_Helper.addSubjects(subject);
		      
		      System.out.println(subject.getSubjID());
		      Schedule schedule = new Schedule(room, day, time, section, subject);
		      session_Helper.addSchedules(schedule);
		      
	
		    }
		   
		    
		    br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getFileUploadContentType() {
		return fileUploadContentType;
	}

	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}
	
	
}
