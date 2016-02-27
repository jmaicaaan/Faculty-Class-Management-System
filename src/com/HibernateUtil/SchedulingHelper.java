package com.HibernateUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.helper.ProfilingHelperClass;
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
		} finally{
			session.close();
		}
		
		return subjectSetID;
	}
	
	public boolean is_ISSubject(Subjects subject){
		Session session = null;
		Transaction trans = null;
		
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			Subjects subjObj = (Subjects) session.createSQLQuery("Select * from Subjects where courseCode = :cc")
						.setParameter("cc", subject.getCourseCode())
						.setResultTransformer(Transformers.aliasToBean(Subjects.class))
						.uniqueResult();
			
			if(subjObj == null){
				return false;
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
		return true;
	}
	
	public boolean hasSchedule(Schedule schedule, Subjects subject){
		Session session = null;
		Transaction trans = null;
		
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			
			int scheduleCount = (int) session.createSQLQuery("Select Count(*) from Schedule "
							+ "where section=:section and time=:time and day=:day and room=:room and subjID=:id")
					.setParameter("section", schedule.getSection())
					.setParameter("time", schedule.getTime())
					.setParameter("day", schedule.getDay())
					.setParameter("room", schedule.getRoom())
					.setParameter("id", subject.getSubjID())
					.uniqueResult();
			
			if(scheduleCount <= 0){
				return false;
			}
			
			trans.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if(trans != null){
				trans.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return true;
	}
	
	public int get_SubjID(Subjects subject){
		Session session = null;
		Transaction trans = null;
		int subjID = 0;
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			
			subjID = (int) session.createSQLQuery("Select subjID from Subjects where courseCode = :cc")
						.setParameter("cc", subject.getCourseCode())
						.uniqueResult();
			trans.commit();
			
		} catch (HibernateException e) {
			// TODO: handle exception
			if(trans != null){
				trans.rollback();
			}
		}
		
		return subjID;
	}

	public void addSchedule(Schedule schedule)
	{	
		Session session = null;
		Transaction trans = null;

		try{
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			
			Integer subjID = (Integer) session.createSQLQuery("Select SubjID from Subjects where courseCode=:cc")
								.setParameter("cc", schedule.getSubjects().getCourseCode())
								.uniqueResult();
			
			if(subjID != null){		
				Subjects subjObj = (Subjects) session.get(Subjects.class, subjID);
				
				if( !hasSchedule(schedule, subjObj) ){
					schedule.setSubjects(subjObj);
					session.save(schedule);
				}
				
			}	
			trans.commit();
		}
		catch(Exception ex){
			if(trans != null){
				trans.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		session.close();
	}
	
	public void renewScheduleTable(){
		Session session = null;
		Transaction trans = null;
		Query query = null;
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			query = session.createQuery("Delete from Schedule");
			query.executeUpdate();
			trans.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if(trans != null){
				trans.rollback();
			}
		} finally{
			session.close();
		}
		
	}
	
	public List<Expertise> getTableExpertise(Schedule schedule){
		Session session = null;
		Transaction trans = null;
		Query query = null;
		List<Expertise> expList = new ArrayList<Expertise>();
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			
			int subjID = get_SubjID(schedule.getSubjects());
			query = session.createQuery("FROM Expertise where SubjID = :id")
					.setParameter("id", subjID);
			expList = query.list();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			if(trans != null){
				trans.rollback();
			}
		}
		finally{
			if(expList.isEmpty()){
				session.close();
			}
		}
		return expList;
	}
	
	public Set<Expertise> getExpertise(Schedule schedule){
		Session session = null;
		Transaction trans = null;
		Set<Expertise>list = new HashSet<Expertise>();
		
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			Integer subjID = (Integer)session.createSQLQuery("select SubjID from Subjects where courseCode=:cc")
					.setParameter("cc", schedule.getSubjects().getCourseCode()).uniqueResult();
			
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
		} finally{
			session.close();
		}
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
			if(trans != null){
				trans.rollback();
			}
			throw e;
		} finally{
			session.close();
		}
		
		return list;
	}

	public void addFacultyAssign(List<FacultyAssign> facultyAssign){
		
		Session session = null;
		Transaction trans = null;
		try{
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			
			for(FacultyAssign fa : facultyAssign){
				
				int CID = fa.getSchedule().getcID();
				String username = fa.getProfessorProfile().getUsers().getUsername();
				
				Integer assignID = (Integer) session.createSQLQuery("select assignID from FacultyAssign where CID = :CID")
							.setParameter("CID", CID)
							.uniqueResult();
				Integer userID = (Integer) session.createSQLQuery("select userId from users where username=:un")
						.setParameter("un", username)
						.uniqueResult();
				
				ProfessorProfile professorProfile = (ProfessorProfile) session.get(ProfessorProfile.class, userID);
			
				if(assignID == null){
					session.save(new FacultyAssign(professorProfile, fa.getSchedule()));
				}

			}
		
			trans.commit();
		}
		catch(Exception ex){
			if(trans != null){
				trans.rollback();
			}
			ex.printStackTrace();
			throw ex;
		} finally {
			session.close();
		}
	}
	
	public List<FacultyAssign> viewAssignedFaculty(){
		
		Session session = null;
		Transaction trans = null;
		Query query = null;
		List<FacultyAssign> faList = new ArrayList<FacultyAssign>();
		
		try {
			
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			query = session.createQuery("from FacultyAssign");
			faList = query.list();
			trans.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			if(trans != null){
				trans.rollback();
			}
			e.printStackTrace();
			throw e;
			
		}finally{
			session.close();
		}
		return faList;
	}
	
	public List<FacultyAssign> viewMySchedule(String username){
		
		Session session = null;
		Transaction trans = null;
		Query query = null;
		List<FacultyAssign> sList = new ArrayList<FacultyAssign>();
		
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			
			ProfessorProfile professorProfile = ProfilingHelperClass.getProfessorProfile(username);
			
			query = session.createQuery("from FacultyAssign where PPID = :ppid")
						.setParameter("ppid", professorProfile.getPpID());
			
			sList = query.list();
			
			trans.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if(trans != null){
				trans.rollback();
			}
			e.printStackTrace();
			
		} finally{
			session.close();
		}
		return sList;
	}
	
	public List<FacultyAssign> Generate_AssignedProfessors(){
		
		List<FacultyAssign> list = new ArrayList<FacultyAssign>();
		Session session = null;
		Query query=null;
		Transaction trans = null;
		
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			query = session.createQuery("From FacultyAssign");
			list = query.list();
			trans.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			if(trans != null){
				trans.rollback();
			}
			e.printStackTrace();
		} finally{
			if (list.isEmpty()){
				session.close();
			}
		}
		return list;
		
	}
	
	
}

