package com.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
	
	public static Map<String, Set<ProfessorProfile>> createDisplayMap(Set<ProfessorProfile> pSet){
		
		Map<String, Set<ProfessorProfile>> displayMap = new HashMap<String, Set<ProfessorProfile>>();
		boolean hasSchedule = false;
		
		for(ProfessorProfile pp : pSet){
//			Subjects subj = null;
			String courseCode = "";
			for(Expertise e : pp.getExpertise()){
				
				hasSchedule = e.getSubjects().getSchedule().size() > 0 ? true : false;
				
				if(hasSchedule){
					courseCode = e.getSubjects().getCourseCode();
					
					if(!displayMap.containsKey(courseCode)){
						displayMap.put(courseCode, Collections.synchronizedSet(new HashSet<ProfessorProfile>())); //Make it thread safe!
					}
					displayMap.get(courseCode).add(pp);
				}
			}
			if(hasSchedule){
				displayMap.get(courseCode).add(pp);
			}
		}
		
		return displayMap;
	}
	
	public static void displayMap(Map<String, Set<ProfessorProfile>> displayMap){
			
		boolean hasSchedule = false;
		
		for(Map.Entry<String, Set<ProfessorProfile>> map : displayMap.entrySet()){
//			Subjects subj = map.getKey();
			String courseCode = map.getKey();
			
//			System.out.print("Subject: " + subj.getCourseCode() + " ");
		
			Set<ProfessorProfile> valueSet = map.getValue();
			
				for(ProfessorProfile p : valueSet){
					
					for(Expertise e : p.getExpertise()){

						hasSchedule = e.getSubjects().getSchedule().size() > 0 ? true : false;
						
						if(hasSchedule){
							
							System.out.print("Subject: " + e.getSubjects().getCourseCode() + " ");
							for(Schedule sched : e.getSubjects().getSchedule()){
								System.out.print(sched.getTime() + " " + sched.getSection() + " ");
							}
						}
					}
					if(hasSchedule){
						System.out.print(p.getUsers().getUsername() + " ");
					}
					System.out.println();
				}
				
		}
	}
}
