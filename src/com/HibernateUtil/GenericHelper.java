package com.HibernateUtil;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.helper.HelperClass;
import com.helper.Utilities;
import com.model.Password;
import com.model.Users;

public class GenericHelper {
	
	
	
	/*
	 * session=HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			
			Users updateUser=(Users)session.get(Users.class, users.getUserID());
			updateUser.setPictureUrl(users.getPictureUrl());
			
			session.update(updateUser);
			trans.commit();
			
	 */
	public boolean edit_password(Users user){
		
		Session session = null;
		Transaction trans = null;
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			Integer userID = (Integer) session.createSQLQuery("Select UserID from Users where username = :un")
						.setParameter("un", user.getUsername())
						.uniqueResult();
			if(userID == null){
				return false;
			}else{
				Password pObj = (Password) session.get(Password.class, userID);
				String password = user.getUsername().length() > 5 ? 
						HelperClass.encrypt(Utilities.password) : HelperClass.encrypt(Utilities.password1);
				pObj.setPassword(password);
				session.update(pObj);
				trans.commit();
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			if(trans != null){
				trans.rollback();
			}
			return false;
		} finally{
			session.close();
		}
	}
}
