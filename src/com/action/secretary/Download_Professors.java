package com.action.secretary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.CookiesAware;
import org.apache.struts2.util.ServletContextAware;

import com.HibernateUtil.SecretaryHelper;
import com.helper.PDFGenerator;
import com.model.ProfessorProfile;
import com.model.Users;
import com.opensymphony.xwork2.ActionSupport;

import com.helper.Zip_Helper;

public class Download_Professors extends ActionSupport implements CookiesAware, ServletContextAware {
	
	private Map<String, String> userCookie;
	private InputStream inputStream;
	private String inputStreamName;
	private ServletContext context;
	private SecretaryHelper secteray_helper = new SecretaryHelper();
	private List<String> secretaryList = new ArrayList<String>();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		if(userCookie.containsKey("secretaryList")){
			ProfessorProfile professorProfile = new ProfessorProfile();
			PDFGenerator pdf = null;
			Users uModel = null;
			context = ServletActionContext.getServletContext();
			String serverPath = context.getRealPath("/");
			File file = null;
			String ppid = userCookie.get("secretaryList").replace("%2C", ","); 
			String[] splitted = ppid.split(",");
			
			File profileReportsZip = new File("test.zip");
			FileOutputStream fileObject = new FileOutputStream(profileReportsZip);
			ZipOutputStream zipFile = new ZipOutputStream (fileObject);
			
			for(int i = 0; i <= splitted.length -1; i++){
				professorProfile.setPpID(Integer.parseInt(splitted[i]));
				uModel = secteray_helper.getUsersModel(professorProfile.getPpID());
				pdf = new PDFGenerator(uModel, professorProfile);
				file = new File(pdf.generateProfessorPDF(serverPath));
				Zip_Helper.addToZip(file.getAbsolutePath(), zipFile);
			}
			
			inputStream = new FileInputStream(profileReportsZip);
			inputStreamName = profileReportsZip.getName();
			
			zipFile.close();
			fileObject.close();
		}
		return SUCCESS;
	}
	
	
	
	@Override
	public void setCookiesMap(Map<String, String> cookies) {
		// TODO Auto-generated method stub
		this.userCookie = cookies;
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
	
	public void setSecretaryList(List<String> secretaryList) {
		this.secretaryList = secretaryList;
	}

	
}
