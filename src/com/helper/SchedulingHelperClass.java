package com.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.model.Expertise;
import com.model.ProfessorProfile;
import com.model.Schedule;
import com.model.Subjects;

public class SchedulingHelperClass {
	
	public static List<Schedule> readUploadedSubjects(File file) throws Exception{

		List<Schedule> schedList = new ArrayList<Schedule>();
		try(BufferedReader buffered = new BufferedReader(new FileReader(file));){

			String scheduleRow = null;
			while (( scheduleRow = buffered.readLine()) != null){

				String[] splitSchedule = scheduleRow.split(",");
				String courseCode = splitSchedule[0],
						description = splitSchedule[1],
						units = splitSchedule[2],
						room = splitSchedule[3],
						day = splitSchedule[4],
						section = splitSchedule[5],
						time = splitSchedule[6];

				Subjects subject = new Subjects(courseCode, description, units);
				Schedule schedule = new Schedule(room, day, time, section, subject);
				schedList.add(schedule);
			}	
		} 
		catch (Exception e){
			e.printStackTrace();
			throw e;
		}
		return schedList;
	}
	
	public static Map<Subjects, Set<ProfessorProfile>> createDisplayMap(Set<ProfessorProfile> pSet){
		
		Map<Subjects, Set<ProfessorProfile>> displayMap = new HashMap<Subjects, Set<ProfessorProfile>>();
		boolean hasSchedule = false;
		
		for(ProfessorProfile pp : pSet){
			Subjects subj = null;
			for(Expertise e : pp.getExpertise()){
				subj = e.getSubjects();
				hasSchedule = e.getSubjects().getSchedule().size() > 0 ? true : false;
				
				if(hasSchedule){
					if(!displayMap.containsKey(subj)){
						displayMap.put(subj, Collections.synchronizedSet(new HashSet<ProfessorProfile>())); //Make it thread safe!
					}
					displayMap.get(subj).add(pp);
				}
			}
			if(hasSchedule){
				displayMap.get(subj).add(pp);
			}
		}
		
		return displayMap;
	}
	
	public static void displayMap(Map<Subjects, Set<ProfessorProfile>> displayMap){
				
		for(Map.Entry<Subjects, Set<ProfessorProfile>> map : displayMap.entrySet()){
			Subjects subj = map.getKey();
			
			System.out.print("Subject: " + subj.getCourseCode() + " ");
			
			Set<ProfessorProfile> valueSet = map.getValue();
			
				for(Schedule sched : subj.getSchedule()){ 
					System.out.print(sched.getTime() + " " + sched.getSection() + " ");
				}
				
				for(ProfessorProfile p : valueSet){
					System.out.print(p.getUsers().getUsername() + " ");
				}
				System.out.println();
		}
	}
}
