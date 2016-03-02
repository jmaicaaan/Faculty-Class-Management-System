package com.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.helper.SecretaryHelperClass;
import com.model.ProfessorProfile;

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
}
