package com.model;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.DropboxService.DropBoxService;

public class UploadUserImageModel {

	private File file;
	private String fileFileName;
	private String fileContentType;
	private String Url;
	
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
	public void setUrl(String url) {
		Url = url;
	}
	
	public void doUpload(String serverPath) {
		DropBoxService dbService = new DropBoxService();
		
		try{
			System.out.println(fileFileName);
			File tempFile = new File(serverPath, fileFileName);
			FileUtils.copyFile(file, tempFile);
			String Url = dbService.uploadUserImage(tempFile.getPath());
			this.Url = Url;
			
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
