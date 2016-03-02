package com.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.model.Schedule;
import com.model.Subjects;

public class SchedulingHelperClass {

	public static List<Schedule> readUploadedSubjects(File file) throws Exception{

		List<Schedule> schedList = new ArrayList<Schedule>();
		try(BufferedReader buffered = new BufferedReader(new FileReader(file));){

			String scheduleRow = null;
			while ( (scheduleRow = buffered.readLine()) != null){

				if(scheduleRow != ""){
					String[] splitSchedule = scheduleRow.split(",");
					if(splitSchedule.length == 7){
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
			}	
		} 
		catch (Exception e){
			e.printStackTrace();
			throw e;
		}
		return schedList;
	}
}
