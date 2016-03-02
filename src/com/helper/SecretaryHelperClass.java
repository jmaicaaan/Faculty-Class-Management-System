package com.helper;

import com.model.ProfessorProfile;

public class SecretaryHelperClass {
	
	public boolean hasProfile(ProfessorProfile professorProfile){
		if (professorProfile.getAchievements().size() > 0 && professorProfile.getProjects().size() > 0 &&
				professorProfile.getResearches().size() > 0 && professorProfile.getResume().size() > 0){
			return true;
		}
		return false;
	}
	
	public void addToZip(){
		
	}
	
	public void getZipFile(){
		
	}
}
