package com.action.secretary;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.ServletContextAware;

import com.helper.Zip_Helper;
import com.opensymphony.xwork2.ActionSupport;

public class ZipFiles extends ActionSupport implements ServletContextAware{
	
	private ServletContext context;	
	private InputStream inputStream;
	private String inputStreamName;

	public String execute(){
		//
		try {
			context = ServletActionContext.getServletContext();
			String serverPath = context.getRealPath("/");
			System.out.println(serverPath + File.separator);

			String profilingReports = "D:\\Professor_Profile.pdf";		
	       
			File profileReportsZip = new File("test.zip");
	        FileOutputStream fileObject = new FileOutputStream(profileReportsZip) ;
	        ZipOutputStream zipFile = new ZipOutputStream (fileObject);
	        Zip_Helper.addToZip(profilingReports, zipFile);        
	        inputStream = new FileInputStream(profileReportsZip);
			inputStreamName = profileReportsZip.getName();

			zipFile.close();
			fileObject.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}
	
	@Override
	public void setServletContext(ServletContext context){
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
