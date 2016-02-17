package com.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

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

	Session session = null;


	public Subjects addSubjects(Subjects subjects){

		Subjects subjectSetID=new Subjects();
		Transaction trans = null;
		try
		{
			session=HibernateFactory.getSession();
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

	public void addSchedule(Schedule schedule){

		Transaction trans = null;
		Query query = null;
		try{
			
			session = HibernateFactory.getSession();
			trans = session.beginTransaction();

			String courseCode = schedule.getSubjects().getCourseCode();
			query = session.createQuery("from Subjects where CourseCode=:cc");
			query.setParameter("cc", courseCode);
			
			Subjects subjObj = (Subjects) query.uniqueResult();
			
			
			if(subjObj != null){
				//The subject is existing!
				try {
					query = null;
					//Get the id of the subject to check if it has already a schedule
					int subjID = subjObj.getSubjID();
					
					query = session.createQuery("from Schedule where day = :day and room = :room and section = :section and time = :time and SubjID = :subjID");
					query.setParameter("day", schedule.getDay());
					query.setParameter("room", schedule.getRoom());
					query.setParameter("section", schedule.getSection());
					query.setParameter("time", schedule.getTime());
					query.setParameter("subjID", subjID);
								
					Schedule sObj = (Schedule) query.uniqueResult();
					
					if(sObj == null){
						session.save(schedule);
					}	
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					throw e;
				}
			}
			trans.commit();
		}
		catch(Exception ex)
		{
			if(trans != null){
				trans.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
	}

//	public List<Users> compatibleExpertise(Subjects subjects)
//	{
//		session=HibernateFactory.getSession();
//		session.beginTransaction();
//		
//		SQLQuery query=session.createSQLQuery("select Users.LastName,Users.FirstName, Users.Username, Users.PictureUrl "
//				+ "from Users, Expertise, ProfessorProfile, Subjects where Users.UserID=ProfessorProfile.UserID "
//				+ "and Subjects.SubjID=Expertise.SubjID "
//				+ "and Expertise.PPID=ProfessorProfile.PPID "
//				+ "and Subjects.CourseCode=:cc");
//		
//	
//		
//		query.setParameter("cc", subjects.getCourseCode());
//		query.setResultTransformer(Transformers.aliasToBean(Users.class));
//		
//		List<Users> list = query.list();
////		Users user = (Users) query.uniqueResult();
//
//		list.forEach(i -> System.out.println(i.getUsername()));
////		System.out.println(user.getUsername());
//		
//		session.getTransaction().commit();
////	session.close();
//		return list;
//	}
	
	public List<Expertise> matchExpertise(Subjects subjects)
	{
		session=HibernateFactory.getSession();
		session.beginTransaction();
		List<Expertise>list=null;
		Subjects sObjc=(Subjects)session.get(Subjects.class, subjects.getSubjID());
		list=sObjc.getExpertise();
		
		session.getTransaction().commit();
		session.close();
		return list;
		
	}
	
	
	public List<ProfessorProfile>compatibleProfessors(List<Expertise>expertise){
		session=HibernateFactory.getSession();
		session.beginTransaction();
		List<ProfessorProfile>list=new ArrayList<ProfessorProfile>();
		Users users=null;
		
		int PPID=0;
		for(Expertise eObj:expertise){
			
			PPID=eObj.getProfessorProfile().getPpID();
			users=(Users)session.get(Users.class, PPID);
			list.addAll(users.getProfessorProfile());
		}
		
		session.getTransaction().commit();
		session.close();
		return list;
	}

	public List<Schedule>displaySchedules(List<Schedule>schedule){
		session=HibernateFactory.getSession();
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
	
	public void ExplicitSessionClose(){
		HibernateFactory.explicitlyClose();
	}

	public void addFacultyAssign(FacultyAssign facultyAssign){
		try
		{
			session=HibernateFactory.getSession();
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
