package com.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateFactory {
	
	private static SessionFactory sessionFactory = null;
	private static Session session = null;
	static{
		
		sessionFactory=new Configuration().configure().buildSessionFactory();
	}
	
	public static Session getSession(){
		
		if(session == null || !session.isOpen()){
			session = sessionFactory.openSession();
		}
		
		return session;
	}
	
	public static void explicitlyClose(){
		session.close();
	}
}
