package com.HibernateUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
			
			if(getSubjID != null){			
				
				Subjects sObjcCHecker=(Subjects)session.get(Subjects.class, getSubjID);
				if(sObjcCHecker!=null){
					Integer queryChecker = (Integer) session.createSQLQuery("Select Count(*) from Schedule "
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
	
	public Set<Expertise> getExpertise(Subjects subjects)
	{
		Session session = null;
		Transaction trans = null;
		Set<Expertise>list = new HashSet<Expertise>();
		
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
			
		} catch (HibernateException  e) {
			// TODO: handle exception
			if(trans != null){
				trans.rollback();
			}
			e.printStackTrace();
		}finally{
			if(list == null){
				session.close();
			}
		}
		
//		session.close();
//		session.close();
		return list;
	}
	
	
	public Set<ProfessorProfile> compatibleProfessors(Set<Expertise>expertise){
		
		Set<ProfessorProfile>list = new HashSet<ProfessorProfile>();
		Transaction trans = null;
		Session session = null;
		Users users = null;
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();

			for(Expertise eObj : expertise){
				int PPID = eObj.getProfessorProfile().getPpID();
				users = (Users) session.get(Users.class, PPID);
				
				list.addAll(users.getProfessorProfile());
			}
			
			trans.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if(trans != null){
				trans.commit();
			}
			throw e;
		}
		session.close();
		return list;
	}

	public List<Schedule>displaySchedules(List<Schedule>schedule){
		
		List<Schedule>list = new ArrayList<Schedule>();
		Transaction trans = null;
		Session session = null;
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			
			Subjects sObjc = null;
			
			for(Schedule sObjec:schedule){
				sObjc=(Subjects)session.get(Subjects.class, sObjec.getSubjects().getSubjID());
				list.add(sObjec);
				
			}
			
			trans.commit();
		} catch (Exception e) {
			// TODO: handle exception
		}
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
		catch(Exception ex){
			ex.printStackTrace();
		}
	}





}
