package com.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.helper.Utilities;
import com.model.AccountType;
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
			  
			  
			
//			trans.commit();
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
	
	
}
