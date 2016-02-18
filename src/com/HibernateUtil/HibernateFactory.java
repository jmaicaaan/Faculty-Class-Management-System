package com.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateFactory {
	
	private static SessionFactory sessionFactory;
	
	static{
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (HibernateException e) {
			// TODO: handle exception
			 System.out.println("Failed to create sessionFactory object." + e);
		}
	}
	
	public static SessionFactory getSession(){
		return sessionFactory;
	}
	

}

