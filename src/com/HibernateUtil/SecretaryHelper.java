package com.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.helper.SecretaryHelperClass;
import com.model.ProfessorProfile;
import com.model.Users;

public class SecretaryHelper {
	
	public List<ProfessorProfile> view_Professors(){
		
		Session session = null;
		Transaction trans = null;
		List<ProfessorProfile> pList = new ArrayList<ProfessorProfile>();
		SecretaryHelperClass s_helper_class = new SecretaryHelperClass();
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			List<ProfessorProfile> tempList = session.createQuery("from ProfessorProfile").list();
			
			for(ProfessorProfile p : tempList){
				Hibernate.initialize(p);
				System.out.println(p.getUsers().getUsername());
				if(s_helper_class.hasProfile(p)){
					pList.add(p);
				}
			}
		
			trans.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if(trans != null){
				trans.rollback();
			}
			e.printStackTrace();
		} finally {
//			session.close();
		}
		
		return pList;
	}
	
	public Users getUsersModel (int ppid)
	{
		Session session = null;
		Transaction trans = null;
		Users users = null;
		try
		{
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			users = (Users) session.get(Users.class, ppid);
			trans.commit();
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally {
			session.close();
		}
		
		return users;
		
		
		
	}
}
