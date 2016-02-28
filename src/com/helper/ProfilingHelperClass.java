package com.helper;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.HibernateUtil.HibernateFactory;
import com.model.ProfessorProfile;

public class ProfilingHelperClass {
	
	public static ProfessorProfile getProfessorProfile(String username){
		
		Session session = null;
		Transaction trans = null;
		ProfessorProfile professorProfile = new ProfessorProfile();
		
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			
			Integer userID = (Integer) session.createSQLQuery("select userId from users where username=:un")
					.setParameter("un", username)
					.uniqueResult();
			
			professorProfile = (ProfessorProfile) session.get(ProfessorProfile.class, userID);
			
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
		
		return professorProfile;
	}
	
	public void excludeProperties(String propertyName){
		
		
	}
}
