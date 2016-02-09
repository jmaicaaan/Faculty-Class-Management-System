package com.HibernateUtil;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.helper.Utilities;
import com.model.AccountType;
import com.model.Password;
import com.model.ProfessorProfile;
import com.model.Projects;
import com.model.Subjects;
import com.model.Users;

public class DeveloperHelper implements Utilities {

	static SessionFactory sessionFactory = null;
	Session session = null;
	static
	{
		sessionFactory=new Configuration().configure().buildSessionFactory();
	}

	public void addUser(Users users)
	{

		try
		{
			session=sessionFactory.openSession();
			session.beginTransaction();
			session.save(users);
			session.getTransaction().commit();
			session.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}


	}

	public void addPassword(Password password)
	{
		try
		{
			session=sessionFactory.openSession();
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
			session=sessionFactory.openSession();
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

	public void addAccountType(AccountType accountType)
	{
		try
		{
			session=sessionFactory.openSession();
			session.beginTransaction();
			Query query=null;
			List <AccountType> checkAccountType=null;
			
			String acType = accountType.getAccountType();
			
			if(acType.equals(Utilities.CHAIRPERSON))
			{
				query=session.createQuery("From AccountType where accountType=:act");
				query.setParameter("act", Utilities.CHAIRPERSON);
				checkAccountType=query.list();
				 if (checkAccountType != null && !checkAccountType.isEmpty())
				 {
					 System.out.println("NAH NAH NAH");
				 }
				 else
				 {
					 session.save(accountType);
				 }
			}
			else if(acType.equals(Utilities.ACADEMIC_ADIVSER))
			{
		
				Integer count=(Integer)session.createSQLQuery("select COUNT(*) from AccountType where AccountType=:act")
						.setString("act", Utilities.ACADEMIC_ADIVSER).uniqueResult();
				if(count>=3)
				{
					System.out.println("MORE THAN NA PO");
				}
				else
				{
					session.save(accountType);
				}
			}
			else
			{
				session.save(accountType);
			}
			
			
			session.getTransaction().commit();
			session.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}


	public List<Users>viewAllProfessors() 
	{

		session=sessionFactory.openSession();
		session.beginTransaction();

		Query query=session.createQuery("From Users");

		List<Users>list=query.list();

		session.getTransaction().commit();
		session.close();
		return list;

	}

	public void addSubjects(Subjects subjects){

		try
		{
			session=sessionFactory.openSession();
			session.beginTransaction();

			String courseCode=subjects.getCourseCode();
			Query query=session.createQuery("from Subjects where CourseCode=:cc");
			query.setParameter("cc", courseCode);

			
				
			session.save(subjects);
			session.getTransaction().commit();
			session.close();

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public List<Subjects>viewSubjects() 
	{

		session=sessionFactory.openSession();
		session.beginTransaction();

		Query query=session.createQuery("From Subjects");

		List<Subjects> list = query.list();

		session.getTransaction().commit();
		session.close();
		return list;

	}
}
