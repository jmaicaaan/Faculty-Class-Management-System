package com.HibernateUtil;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.helper.HelperClass;
import com.model.Users;

public class LoginHelper {

	public int getUserID(String username) //Jm was here :)
	{
		//Handle the exception when the the username is not found in the database.
		//Exception: NullPointerException
		Transaction trans = null;
		Session session = null;
		int userID = 0;
		try
		{

			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();

			Query query=session.createQuery("from Users where username=:uName");
			query.setParameter("uName", username);

			Users users=(Users) query.uniqueResult();

			userID = users.getUserID();
			trans.commit();
		}

		catch(Exception ex)
		{
			if(trans != null){
				trans.rollback();
			}
			ex.printStackTrace();
		}

		session.close();
		return userID;

	}

	public Users loginUser(String username, String password){

		Users users = null;
		Transaction trans = null;
		Session session = null;
		try{ 

			if(HelperClass.isAdmin(username, password)){
				users = HelperClass.Admin();
				return users;
			}else{

				session = HibernateFactory.getSession().openSession();
				trans = session.beginTransaction();

				Query query = session.createQuery("from Users where username = :uName");
				query.setParameter("uName", username);
				users = (Users) query.uniqueResult();
				trans.commit();

				if(users == null){
					return null;
				}else{
					if(users.getUsername().equalsIgnoreCase(username) 
							&& users.getPassword().get(0).getPassword().equals(HelperClass.encrypt(password))){					
						return users;
					}
				}
			}


		}catch(NullPointerException e){
			if(trans != null){
				trans.rollback();
			}
			e.printStackTrace();
		}
		session.close();
		return null;
	}

	public Users getUserDetails(int userID){
		Users usersModel = null;
		Transaction trans = null;
		Session session = null;
		try{
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			
			Query query = session.createQuery("from Users where UserID = :userID");
			query.setParameter("userID", userID);

			//Single result
			usersModel = (Users) query.uniqueResult();

			trans.commit();

		}catch(Exception e){
			if(trans != null){
				trans.rollback();
			}
			e.printStackTrace();
		}

		session.close();
		return usersModel;

	}
}
