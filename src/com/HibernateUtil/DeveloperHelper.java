package com.HibernateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.helper.Utilities;
import com.model.AccountType;
import com.model.Password;
import com.model.ProfessorProfile;
import com.model.Subjects;
import com.model.Users;

public class DeveloperHelper implements Utilities {

	public Boolean addUser(Users users)
	{
		boolean validate=false;
		try
		{
			Session session = HibernateFactory.getSession().openSession();
			session.beginTransaction();
			Integer count=(Integer)session.createSQLQuery("Select count(*) from Users "
					+ "where Users.IDNo=:idno")
					.setParameter("idno", users.getIdNo())
					.uniqueResult();
			if(count <= 0){
				session.save(users);
				validate = true;
			}


			session.getTransaction().commit();
			session.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return validate;

	}

	public void addPassword(Password password)
	{
		try{
			Session session = HibernateFactory.getSession().openSession();
			session.beginTransaction();
			session.save(password);
			session.getTransaction().commit();
			session.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

	}




	public void addProfessorProfile(ProfessorProfile professorProfile)
	{
		try
		{
			Session session = HibernateFactory.getSession().openSession();
			session.beginTransaction();
			session.save(professorProfile);
			session.getTransaction().commit();
			session.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public boolean addAccountType(AccountType accountType,Users users){
		
		Session session = null;
		Query query = null;
		Transaction trans = null;
		boolean hasAdded = false;
		List <AccountType> checkAccountTypeList = new ArrayList<AccountType>();
		
		try{
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();

			String acType = accountType.getAccountType();

			if(acType.equals(Utilities.CHAIRPERSON)){
				query = session.createQuery("From AccountType where accountType=:act")
						.setParameter("act", Utilities.CHAIRPERSON);

				checkAccountTypeList = query.list();

				if (checkAccountTypeList != null && !checkAccountTypeList.isEmpty()){
					System.out.println("NAH NAH NAH");
				}
				else{
					session.save(accountType);
					hasAdded = true;
				}
			}
			else if(acType.equals(Utilities.ACADEMIC_ADIVSER)){
				
				Integer firstCount = (Integer) session.createSQLQuery("select COUNT(*) from AccountType where UserID=:id and AccountType=:act")
						.setInteger("id", users.getUserID())
						.setParameter("act", Utilities.ACADEMIC_ADIVSER)
						.uniqueResult();
				
				if(firstCount == 0){
					
					Integer secondCount = (Integer) session.createSQLQuery("select COUNT(*) from AccountType where AccountType=:act")
							.setString("act", Utilities.ACADEMIC_ADIVSER)
							.uniqueResult();
					
					if(secondCount >= 3){
						System.out.println("MORE THAN NA PO");
					}
					else{
						session.save(accountType);
						hasAdded = true;
					}
				}
				else{
					System.out.println("NDI KA PEDE UGOK");
				}
			}
			else{
				session.save(accountType);
			}
			trans.commit();
		}
		catch(Exception ex){
			if(trans != null){
				trans.commit();
			}
		}finally {
			if(checkAccountTypeList.isEmpty()){
				session.close();
			}
		}
		
		return hasAdded;
	}

	public List<Users> viewAllProfessors(){
		
		Session session = null;
		Transaction trans = null;
		List<Users> list = new ArrayList<Users>();
		
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			list = session.createQuery("from Users").list();
			
			list.forEach(i -> {
				Hibernate.initialize(i.getAccountType());
			});
			trans.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			if(trans != null){
				trans.rollback();
			}
		} finally{
			session.close();
		}
		
		return list;
	}

	public void addSubjects(Subjects subjects){

		try{
			Session session = HibernateFactory.getSession().openSession();
			session.beginTransaction();

			String courseCode=subjects.getCourseCode();
			Query query=session.createQuery("from Subjects where CourseCode=:cc");
			query.setParameter("cc", courseCode);



			session.save(subjects);
			session.getTransaction().commit();
			session.close();

		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public List<Subjects>viewSubjects(){
		
		
		Session session = null;
		Transaction trans = null;
		List<Subjects> list = new ArrayList<Subjects>();
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			list = session.createQuery("From Subjects")
					.list();
			
			for(Subjects s : list){
				s.setExpertise(Collections.emptySet());
				s.setSchedule(Collections.emptySet());
			}
			
			
			trans.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if(trans != null){
				trans.commit();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;

	}
}
