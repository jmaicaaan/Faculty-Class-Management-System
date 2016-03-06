package com.HibernateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.helper.Utilities;
import com.model.AccountType;
import com.model.Attendance;
import com.model.Classlist;
import com.model.FacultyAssign;
import com.model.Password;
import com.model.ProfessorProfile;
import com.model.Users;

public class AttendanceHelper {

	public void addStudent(Users users){

		Session session = null;
		Transaction trans = null;
		try{
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			session.save(users);
			trans.commit();
		}
		catch(Exception ex){
			ex.printStackTrace();
			if(trans != null){
				trans.rollback();
			}
		}
		finally{
			session.close();
		}
	}

	public boolean isStudentNotAdded(Users users){
		Session session = null;
		Transaction trans = null;
		boolean isAdded = false;

		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();

			int count = (int) session.createSQLQuery("Select count(*) from Users where users.idno = :idno")
					.setParameter("idno", users.getIdNo())
					.uniqueResult();

			isAdded = count <= 0 ? true : false;

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
		return isAdded;
	}

	public void addPassword(Password password){

		Session session = null;
		Transaction trans = null;
		try{
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			session.save(password);
			trans.commit();
		}
		catch(Exception ex){
			ex.printStackTrace();
			if(trans != null){
				trans.rollback();
			}
		}finally{
			session.close();
		}

	}

	public void addAccountType(AccountType accountType){

		Session session = null;
		Transaction trans = null;

		try{
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			Query query=null;
			List <AccountType> checkAccountType=null;

			String acType = accountType.getAccountType();
			//			Integer userid=(Integer)session.createSQLQuery("select UserID from Users where IDno=:id")
			//					.setParameter("id",users.getIdNo()).uniqueResult();

			if(acType.equals(Utilities.STUDENT)){
				query=session.createQuery("From AccountType where userid=:userid");
				query.setParameter("userid", accountType.getUsers().getUserID());
				checkAccountType=query.list();
				if (checkAccountType != null && !checkAccountType.isEmpty()){
					System.out.println("Student is existing in the accountType");
				}
				else{
					session.save(accountType);
				}
			}		
			trans.commit();
		}
		catch(Exception ex){
			ex.printStackTrace();
			if(trans != null){
				trans.rollback();
			}
		}finally{
			session.close();
		}
	}

	public void addClassList(Classlist classlist){

		Session session = null;
		Transaction trans = null;

		try{
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			Integer assignID=null;
			Integer userID=null;
			if(classlist.getUsers().getUserID()==0){
				Integer userId=(Integer)session.createSQLQuery("select userid from users where idno=:idno")
						.setParameter("idno", classlist.getUsers().getIdNo())
						.uniqueResult();
				classlist.getUsers().setUserID(userId);
			}
			assignID=(Integer)session.createSQLQuery("select COUNT(*) from Classlist "
					+ "where AssignID=:aid and UserID=:uid")
					.setParameter("aid", classlist.getFacultyAssign().getAssignID())
					.setParameter("uid", classlist.getUsers().getUserID())
					.uniqueResult();

			if(assignID == 0){
				session.save(classlist);
			}


			trans.commit();
		}
		catch(Exception ex){
			ex.printStackTrace();
			if(trans != null){
				trans.rollback();
			}
		}finally{
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Classlist> viewClassList (FacultyAssign facultyAssign){

		Session session = null;
		Transaction trans = null;
		List<Classlist> list = new ArrayList<Classlist>();

		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();

			list = session.createQuery("from Classlist where AssignID = :aid")
					.setParameter("aid", facultyAssign.getAssignID())
					.list();

			list.forEach(i -> {
				Hibernate.initialize(i.getUsers());
				Hibernate.initialize(i.getAttendance());
			});

			System.out.println("Free memory (bytes): " + 
					Runtime.getRuntime().freeMemory());

			/* Total memory currently in use by the JVM */
			System.out.println("Total memory (bytes): " + 
					Runtime.getRuntime().totalMemory());



			trans.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if(trans != null){
				trans.rollback();
			}
			e.printStackTrace();
		} finally { 
			session.close();
		}
		return list;
	}

	public int getAssignID(String subject, String section){

		Session session = null;
		Transaction trans = null;
		Integer aid = 0;
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			aid = (Integer) session.createSQLQuery("select assignid "
					+ "from Schedule,FacultyAssign,Subjects "
					+ "where Schedule.Section=:section "
					+ "and Subjects.CourseCode=:cc "
					+ "and Subjects.SubjID=Schedule.SubjID "
					+ "and FacultyAssign.CID=Schedule.CID")
					.setParameter("section", section)
					.setParameter("cc", subject)
					.uniqueResult();

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
		return aid;
	}

	public int getAssignID(String subject, String section, ProfessorProfile professorProfile){

		Session session = null;
		Transaction trans = null;
		Integer aid = 0;
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			aid = (Integer) session.createSQLQuery("select assignid "
					+ "from Schedule as sched, FacultyAssign as f, Subjects as s "
					+ "where F.PPID = :ppid "
					+ "and sched.Section = :section "
					+ "and s.CourseCode = :cc "
					+ "and s.SubjID = sched.SubjID "
					+ "and f.CID = sched.CID")
					.setParameter("ppid", professorProfile.getPpID())
					.setParameter("section", section)
					.setParameter("cc", subject)
					.uniqueResult();

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
		return aid;
	}

	public void deleteStudent(Classlist classlist){

		Session session = null;
		Transaction trans = null;
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			Classlist cl = (Classlist) session.get(Classlist.class, classlist.getClassID());
			session.delete(cl);
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
	}

	public void addAttendance(int assignID, Users users, Attendance attendance){
		Session session = null;
		Transaction trans = null;

		List<Classlist> cList = null;
		Attendance aObjc = new Attendance();
		boolean hasAttendance = false;
		try{
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			Integer uID = (Integer) session.createSQLQuery("Select userId from users where idno=:idno")
					.setParameter("idno", users.getIdNo())
					.uniqueResult();

			Query query = session.createQuery("From Classlist where AssignID = :aid and UserID = :uid")
					.setParameter("aid", assignID)
					.setParameter("uid", uID);

			cList = query.list();

			

			for(Classlist cObjc : cList){
				attendance.setClasslist(cObjc);
				hasAttendance = hasAttendance(attendance);
				aObjc.setAttendance(attendance.getAttendance());
				aObjc.setDate(attendance.getDate());
				aObjc.setClasslist(cObjc);

				if(hasAttendance){
					updateAttendance(aObjc);
				}else{
					session.save(aObjc);
				}
			}
			trans.commit();
		}
		catch (Exception e) {
			if(trans != null){
				trans.rollback();
			}
			e.printStackTrace();
		} finally{
			session.close();
		}
	}

	public void updateAttendance(Attendance attendance){

		Session session = null;
		Transaction trans = null;
		try {

			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();

			Integer attendID = (Integer) session.createSQLQuery("Select attendID from Attendance where date = :date and classID = :cid")
					.setParameter("date", attendance.getDate())
					.setParameter("cid", attendance.getClasslist().getClassID())
					.uniqueResult();

			Attendance aObj = (Attendance) session.get(Attendance.class, attendID);
			aObj.setAttendance(attendance.getAttendance());
			session.update(aObj);			
			trans.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if(trans != null){
				trans.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public List<Attendance> viewAttendance(String date, int assignID){

		Session session = null;
		Transaction trans = null;
		Query query = null;
		List<Classlist> cList = new ArrayList<Classlist>(); 
		List<Attendance> aList = new ArrayList<Attendance>();

		try{
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			cList = session.createQuery("From Classlist where AssignID =:aid")
					.setParameter("aid" , assignID)
					.list();

			for(Classlist cObjc : cList){
				query = session.createQuery("From Attendance where date=:date and ClassID=:cid")
						.setParameter("date", date)
						.setParameter("cid", cObjc.getClassID());
				aList.addAll(query.list());
			}

			trans.commit();
		}
		catch (Exception e) {
			if(trans != null){
				trans.rollback();
			}
			e.printStackTrace();
		} finally{
			session.close();
		}
		return aList;
	}

	public boolean hasAttendance(Attendance attendance){

		Session session = null;
		Transaction trans = null;
		boolean hasAttendance = false;
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			Integer count = (Integer) session.createSQLQuery("Select COUNT(*) from Attendance as A where A.Date = :date and A.classID = :cid")
					.setParameter("cid", attendance.getClasslist().getClassID())
					.setParameter("date", attendance.getDate())
					.uniqueResult();

			hasAttendance = count != 0 ? true : false;

			trans.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if(trans != null){
				trans.rollback();
			}
			hasAttendance = false;
		} finally {
			session.close();
		}

		return hasAttendance;
	}

	public List<Attendance> viewAttendance(int assignID){

		Session session = null;
		Transaction trans = null;
		Query query = null;
		List<Classlist> cList = new ArrayList<Classlist>();
		List<Attendance> aList = new ArrayList<Attendance>();

		try{
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			cList = session.createQuery("From Classlist where AssignID =:aid")
					.setParameter("aid" , assignID)
					.list();

			for(Classlist cObjc : cList){
				query = session.createQuery("From Attendance where ClassID=:cid")
						.setParameter("cid", cObjc.getClassID());
				aList.addAll(query.list());
			}

			trans.commit();
		}
		catch (Exception e) {
			if(trans != null){
				trans.rollback();
			}
			e.printStackTrace();
		} finally{
			session.close();
		}
		return aList;
	}
	
	public List<Attendance> getHighCharts (List<Classlist> cList)
	{
		Session session = null; 
		Transaction trans = null;
		double maximumLives = 5.5;
		Integer late = null;
		Integer absences = null;
		Attendance aObcj = null;

		List<Attendance> aList = new ArrayList<Attendance>();
		try{
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			for(Classlist cObjc : cList){
				late = (Integer) session.createSQLQuery("select COUNT(*) from Attendance "
						+ "where ClassID = :cid and Attendance.Attendance = :attendance")
						.setParameter("cid", cObjc.getClassID())
						.setParameter("attendance", "L")
						.uniqueResult();
				
				absences = (Integer) session.createSQLQuery("select COUNT(*) from Attendance "
						+ "where ClassID = :cid and Attendance.Attendance = :attendance")
						.setParameter("cid", cObjc.getClassID())
						.setParameter("attendance", "A")
						.uniqueResult();
				
				aObcj = new Attendance();
				aObcj.setNoOfLives(maximumLives - (late * 0.5) - absences);
				aObcj.setClasslist(cObjc);
				aList.add(aObcj);
			}
			
			trans.commit();
			
			aList.forEach(i -> {
				Hibernate.initialize(i.getClasslist());
				Hibernate.initialize(i.getClasslist().getUsers());
				Hibernate.initialize(i.getNoOfLives());
			});
		}
		catch (Exception e) {
			if(trans != null){
				trans.rollback();
			}
			e.printStackTrace();
		} 
		
		finally{
			session.close();
		}
		return aList;
	}
	public List<Classlist> getSubjects(Users users){
		Session session = null;
		Transaction trans = null;
		List<Classlist> cList = null;
		Query query = null;
		int uid = 0;
		try{
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			uid = (Integer) session.createSQLQuery("select userid from users where idno=:IDno")
					.setParameter("IDno", users.getUsername())
					.uniqueResult();
			query = session.createQuery("from Classlist where userID=:userID")
					.setParameter("userID", uid);
			cList = query.list();
//
			cList.forEach(i -> {
				Hibernate.initialize(i);
			});

			trans.commit();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			session.close();
		}
		return cList;
	}
	
	public List<Attendance> studentViewAttendance (List<Classlist> cList){
		Session session = null; 
		Transaction trans = null;
		double maximumLives = 5.5;
		Integer late = null;
		Integer absences = null;
		Attendance aObcj = null;

		List<Attendance> aList = new ArrayList<Attendance>();
		try{
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			for(Classlist cObjc : cList){
				late = (Integer) session.createSQLQuery("select COUNT(*) from Attendance "
						+ "where ClassID = :cid and Attendance.Attendance = :attendance")
						.setParameter("cid", cObjc.getClassID())
						.setParameter("attendance", "L")
						.uniqueResult();
				
				absences = (Integer) session.createSQLQuery("select COUNT(*) from Attendance "
						+ "where ClassID = :cid and Attendance.Attendance = :attendance")
						.setParameter("cid", cObjc.getClassID())
						.setParameter("attendance", "A")
						.uniqueResult();
				
				aObcj = new Attendance();
				aObcj.setNoOfLives(maximumLives - (late * 0.5) - absences);
				aObcj.setClasslist(cObjc);
				aList.add(aObcj);
			}
			

			trans.commit();
			
		}
		catch(Exception ex){
			if(trans != null){
				trans.rollback();
			}
			ex.printStackTrace();
		}
		finally{
			session.close();
		}
		return aList;
	}
	public List<Attendance> displayAttendancetoPDF(Users users)
	{
		Session session = null; 
		Transaction trans = null;

		Integer late = null;
		Integer absences = null;
		Attendance aObcj = null;
		
		Query query = null;
		Integer uid = null;
		List<Attendance> aList = new ArrayList<Attendance>();
		List<Classlist> cList = null;
		try
		{
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			uid = (Integer) session.createSQLQuery("select userid from users where idno=:idno")
					.setParameter("idno", users.getIdNo())
					.uniqueResult();
			query = session.createQuery("From Classlist where UserID=:uid")
					.setParameter("uid", uid);
			cList = query.list();
			
			for(Classlist cObjc : cList)
			{
				
				late = (Integer) session.createSQLQuery("select COUNT(*) from Attendance "
						+ "where ClassID = :cid and Attendance.Attendance = :attendance")
						.setParameter("cid", cObjc.getClassID())
						.setParameter("attendance", "L")
						.uniqueResult();
				
				absences = (Integer) session.createSQLQuery("select COUNT(*) from Attendance "
						+ "where ClassID = :cid and Attendance.Attendance = :attendance")
						.setParameter("cid", cObjc.getClassID())
						.setParameter("attendance", "A")
						.uniqueResult();
				
				aObcj = new Attendance();
				aObcj.setNoOfAbsences(absences);
				aObcj.setNoOfLates(late);
				aObcj.setClasslist(cObjc);
				aList.add(aObcj);
			}
			
		}
		catch(Exception ex){
			if(trans != null){
				trans.rollback();
			}
			ex.printStackTrace();
		}
		finally{
			session.close();
		}
		return aList;
	}
	
	public List<Users> getAllStudents(){
		
		Session session = null; 
		Transaction trans = null;
		List<Users> uList = null;
		Query query = null;
		try{
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			query = session.createQuery("select u from Users as u inner join u.accountType ac where ac.accountType = :ac")
					.setParameter("ac", Utilities.STUDENT);
					
			uList = query.list();
	
			
			
			
			trans.commit();
			
		}
		catch(Exception ex){
			if(trans != null){
				trans.rollback();
			}
			ex.printStackTrace();
		}
		finally{
			session.close();
		}
		return uList;
	}
	
}
