package com.action.scheduling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.helper.FileModel;
import com.model.Expertise;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class View_UploadedSubjects extends ActionSupport implements ModelDriven<FileModel>, SessionAware {
		
	private FileModel fModel = new FileModel();
	private Map<String, Object> userSession;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		List<Expertise> expList = new ArrayList<Expertise>();
		
		expList = userSession.get("expList") != null ? (List<Expertise>) userSession.get("expList") 
				: new ArrayList<Expertise>();
		
		if(expList.size() > 0){
			fModel.setResponse(expList);
		}
		return SUCCESS;
	}
	
	@Override
	public FileModel getModel() {
		// TODO Auto-generated method stub
		return fModel;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.userSession = session;
	}
}
