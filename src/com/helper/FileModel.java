package com.helper;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.DropboxService.DropBoxService;

public class FileModel {

	private File file;
	private String fileFileName;
	private String fileContentType;
	private String Url;
	private Object response;
	private String request;
		
	private DropBoxService dbService = new DropBoxService();

	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String Url) {
		this.Url = Url;
	}
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}
	
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public void doUpload(String serverPath) {

		try{
			File tempFile = new File(serverPath, fileFileName);
			FileUtils.copyFile(file, tempFile);
			String Url = dbService.uploadResume(tempFile.getPath());
			this.Url = Url;
			
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}



}
