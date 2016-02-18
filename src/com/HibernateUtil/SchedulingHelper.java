package com.HibernateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.model.Expertise;
import com.model.FacultyAssign;
import com.model.ProfessorProfile;
import com.model.Schedule;
import com.model.Subjects;
import com.model.Users;

public class SchedulingHelper {

	public Subjects addSubjects(Subjects subjects){

		Subjects subjectSetID = new Subjects();
		Transaction trans = null;
		Session session = null;
		try
		{
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();

			String courseCode=subjects.getCourseCode();
			Query query=session.createQuery("from Subjects where CourseCode=:cc");
			query.setParameter("cc", courseCode);

			List<Subjects>checkSubjects=query.list();

			if (checkSubjects != null && !checkSubjects.isEmpty())
			{	
				for(Subjects subjects2 : checkSubjects)
				{

					//					schedule.setSubjects(subjects2);
					subjectSetID.setSubjID(subjects2.getSubjID());
				}
			}

			else
			{
				subjectSetID=subjects;
				session.save(subjects);
			}

			trans.commit();
			session.getTransaction().commit();


		}
		catch(Exception ex)
		{
			if(trans != null){
				trans.rollback();
			}
			ex.printStackTrace();
		}
		session.close();
		return subjectSetID;
	}

//	public void addSchedule(Schedule schedule){
//
//		Transaction trans = null;
//		Query query = null;
//		Session session = null;
//		try{
//			
//			session = HibernateFactory.getSession().openSession();
//			trans = session.beginTransaction();
//
//			String courseCode = schedule.getSubjects().getCourseCode();
//			query = session.createQuery("from Subjects where CourseCode=:cc");
//			query.setParameter("cc", courseCode);
//			
//			Subjects subjObj = (Subjects) query.uniqueResult();
//			
//			if(subjObj != null){
//				//The subject is existing!
//				try {
//					query = null;
//					//Get the id of the subject to check if it has already a schedule
//					int subjID = subjObj.getSubjID();
//					
//					query = session.createQuery("from Schedule where day = :day and room = :room and section = :section and time = :time and SubjID = :subjID");
//					query.setParameter("day", schedule.getDay());
//					query.setParameter("room", schedule.getRoom());
//					query.setParameter("section", schedule.getSection());
//					query.setParameter("time", schedule.getTime());
//					query.setParameter("subjID", subjID);
//								
//					Schedule sObj = (Schedule) query.uniqueResult();
//					
//					if(sObj == null){
//						session.save(schedule);
//					}	
//					
//				
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//					throw e;
//				}
//			}
//			trans.commit();
//		}
//		catch(Exception ex)
//		{
//			if(trans != null){
//				trans.rollback();
//			}
//			ex.printStackTrace();
//			throw ex;
//		}
//		session.close();
//	}
	public void addSchedule(Schedule schedule)
	{	
		Session session = null;
		Transaction trans = null;
		try{
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			//Get SUbject ID since the subject Id from the bean is 0
			Integer getSubjID=(Integer)session.createSQLQuery("Select SubjID from Subjects where courseCode=:cc")
					.setParameter("cc", schedule.getSubjects().getCourseCode()).uniqueResult();
			//if getSubjID is 0 it is because the subject doesnt exist in the subject table
			if(getSubjID!=null){			
				
				Subjects sObjcCHecker=(Subjects)session.get(Subjects.class , getSubjID);
				if(sObjcCHecker!=null){
					Integer queryChecker=(Integer)session.createSQLQuery("Select Count(*) from Schedule "
							+ "where section=:section and time=:time and day=:day and subjID=:id and room=:room")
							.setParameter("section", schedule.getSection())
							.setParameter("time", schedule.getTime())
							.setParameter("day", schedule.getDay())
							.setParameter("id", getSubjID)
							.setParameter("room", schedule.getRoom()).uniqueResult();
					if(queryChecker <= 0){
						schedule.setSubjects(sObjcCHecker);
						session.save(schedule);
					}
				}
			}	
			trans.commit();
		}
		catch(Exception ex){
			if(trans != null){
				trans.rollback();
			}
		}
		session.close();
	}
	
	public List<Expertise> getExpertise(Subjects subjects)
	{
		Session session = null;
		Transaction trans = null;
		List<Expertise>list = new ArrayList<Expertise>();
		
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			Integer subjID = (Integer)session.createSQLQuery("select SubjID from Subjects where courseCode=:cc")
					.setParameter("cc", subjects.getCourseCode()).uniqueResult();
			
			if(subjID != null){
				Subjects sObjc = (Subjects)session.get(Subjects.class, subjID);
				list = sObjc.getExpertise();
			}
			
			trans.commit();
			
		} catch (HibernateException e) {
			// TODO: handle exception
			if(trans != null){
				trans.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		
		session.close();
		return list;
	}
	
	
	public Set<ProfessorProfile> compatibleProfessors(Set<Expertise>expertise){
		Session session = HibernateFactory.getSession().openSession();
		session.beginTransaction();
		Set<ProfessorProfile>list=new HashSet<ProfessorProfile>();
		Users users=null;
//		Map<Subjects, Set<ProfessorProfile>> map = new HashMap<Subjects, Set<ProfessorProfile>>();
		
		int PPID=0;
		
		for(Expertise eObj:expertise){
			
			PPID = eObj.getProfessorProfile().getPpID();
			users=(Users)session.get(Users.class, PPID);
			list.addAll(users.getProfessorProfile());
		}
		
		session.getTransaction().commit();
		session.close();
		return list;
//		for(ProfessorProfile p : list){
//		System.out.println(p.getUsers().getUsername());
//		for(Expertise e : p.getExpertise()){
//			System.out.println(e.getSubjects().getCourseCode());
//		}
//	}

	
	}
	
	public Map<Subjects, ArrayList<ProfessorProfile>> addToMap (Subjects subject, ProfessorProfile pProf){
		Map<Subjects, ArrayList<ProfessorProfile>> map = new HashMap<Subjects, ArrayList<ProfessorProfile>>();
		
		if(!map.containsKey(subject)){
			map.put(subject, new ArrayList<ProfessorProfile>());
		}
		map.get(subject).add(pProf);
		
		return map;
	}


	public List<Schedule>displaySchedules(List<Schedule>schedule){
		Session session = HibernateFactory.getSession().openSession();
		session.beginTransaction();
		List<Schedule>list = new ArrayList<Schedule>();
		Subjects sObjc = null;
		
		for(Schedule sObjec:schedule){
			sObjc=(Subjects)session.get(Subjects.class, sObjec.getSubjects().getSubjID());
			list.add(sObjec);
			
		}
		session.getTransaction().commit();
		session.close();
		return list;
	}


	public void addFacultyAssign(FacultyAssign facultyAssign){
		try
		{
			Session session = HibernateFactory.getSession().openSession();
			session.beginTransaction();
			int cid=facultyAssign.getSchedule().getcID();
			Integer assignID = (Integer)session.createSQLQuery("select assignID from facultyAssign where cid=:cid")
					.setInteger("cid", cid).uniqueResult();
		
			FacultyAssign fObjc = (FacultyAssign)session.get(FacultyAssign.class,assignID);
			
			if(fObjc==null)
			{
				session.save(facultyAssign);
			}
			session.getTransaction().commit();
			session.close();

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}





}
