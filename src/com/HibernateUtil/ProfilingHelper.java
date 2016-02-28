package com.HibernateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.model.Achievements;
import com.model.Expertise;
import com.model.Password;
import com.model.ProfessorProfile;
import com.model.Projects;
import com.model.Researches;
import com.model.Resume;
import com.model.Users;

public class ProfilingHelper {

	public void addResearches(Researches researches)
	{
		try{
			Session session = HibernateFactory.getSession().openSession();
			session.beginTransaction();
			session.save(researches);
			session.getTransaction().commit();
			session.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	
	
	public void addProjects(Projects projects)
	{
	
		try
		{
			Session session = HibernateFactory.getSession().openSession();
			session.beginTransaction();
			session.save(projects);
			session.getTransaction().commit();
			session.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	
	public void addAchievements(Achievements achievements)
	{
		try
		{
			Session session = HibernateFactory.getSession().openSession();
			session.beginTransaction();
			session.save(achievements);
			session.getTransaction().commit();
			session.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void addResume(Resume resume, ProfessorProfile profile)
	{
		Transaction trans = null;
		Session session = null;
		try
		{
			Set<Resume> resumeSet = null;
			session=HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			
			int PPID = profile.getPpID();
			profile = (ProfessorProfile) session.get(ProfessorProfile.class, PPID);
			resumeSet = profile.getResume();
			
			if(resumeSet.isEmpty() || resumeSet == null){
				session.save(resume);
			}else{
				
				int resumeID = resumeSet.stream().collect(Collectors.toList()).get(0).getRID();
				
				Resume rObj = (Resume) session.get(Resume.class, resumeID);
				rObj.setResumeUrl(resume.getResumeUrl());
				session.update(rObj);
			}
			
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
	}
	
	public void updatePicture(Users users)
	{
		
		Transaction trans = null;
		Session session = null;
		try
		{
			session=HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			
			Users updateUser=(Users)session.get(Users.class, users.getUserID());
			updateUser.setPictureUrl(users.getPictureUrl());
			
			session.update(updateUser);
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
	}
	
	public void updateUserProfile(Users user)
	{
		LoginHelper login_helper = new LoginHelper();
		Transaction trans = null;
		Session session = null;
		try
		{
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			
			int userID = login_helper.getUserID(user.getUsername());
			
			Users uModel = (Users) session.get(Users.class , userID);
			uModel.setFirstName(user.getFirstName());
			uModel.setLastName(user.getLastName());
			
			session.update(uModel);
			trans.commit();
			
		}
		catch(Exception e)
		{	
			if(trans != null){
				trans.rollback();
			}
			e.printStackTrace();
		}
		session.close();
	}
	
	public void updatePassword(Password password, Users users)
	{
		Transaction trans = null;	
		Session session = null;
		try
		{
			
			session=HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			
			int userID = users.getUserID();
			
			Password updatePassword=(Password)session.get(Password.class, userID);
			updatePassword.setPassword(password.getPassword());
			
			updatePassword.setNewPassword(password.getNewPassword());
			updatePassword.setNewPassword_Verify(password.getNewPassword_Verify());
			
			session.update(updatePassword);
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
	}
	
	
	
	
	public Set<Researches> viewResearches (ProfessorProfile professor){
		
		Session session = null;
		Transaction trans = null;
		Set<Researches> list = new HashSet<Researches>();
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			ProfessorProfile professorProfile = (ProfessorProfile) 
						session.get(ProfessorProfile.class, professor.getPpID());
			list = professorProfile.getResearches();
			Hibernate.initialize(list);
			
			
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
		return list;
	}
	
	public Set<Projects> viewProjects(ProfessorProfile profile){
		
		
		Session session = null;
		Transaction trans = null;
		Set<Projects>list = new HashSet<Projects>();
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			ProfessorProfile professorProfile=(ProfessorProfile) session.get(ProfessorProfile.class, profile.getPpID());
			list = professorProfile.getProjects();
			Hibernate.initialize(list);
			
			trans.commit();
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
	
	public Set<Resume>viewResume(ProfessorProfile profile)   //overload id from loginHelper
	{
		Session session = null;
		Transaction trans = null;
		Set<Resume> list = new HashSet<Resume>();
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			ProfessorProfile professorProfile = (ProfessorProfile) session.get(ProfessorProfile.class, profile.getPpID());
			list = professorProfile.getResume();
			Hibernate.initialize(list);
			
			
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
		return list;
		
	}
	public Set<Achievements>viewAchievements(ProfessorProfile profile){
		
		Session session = null;
		Transaction trans = null;
		Set<Achievements> list = new HashSet<Achievements>();
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			ProfessorProfile professorProfile = (ProfessorProfile) session.get(ProfessorProfile.class, profile.getPpID());
			list=professorProfile.getAchievements();
			Hibernate.initialize(list);
			
			
			
			
			trans.commit();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			session.close();
		}
		return list;
	}
	
	
	public void deleteResearches(Researches researches)
	{

		try
		{
			Session session = HibernateFactory.getSession().openSession();
			session.beginTransaction();
			
			Researches rObj=(Researches)session.get(Researches.class, researches.getrID());
			session.delete(rObj);
			session.getTransaction().commit();
			session.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void deleteAchievements(Achievements achievements)
	{
		Transaction trans = null;
		Session session = null;
		try
		{
			session=HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			
			
			Achievements aObj=(Achievements)session.get(Achievements.class, achievements.getaID());
			session.delete(aObj);
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
	}
	
	public void deleteProjects(Projects projects)
	{
		Transaction trans = null;
		Session session = null;
		try
		{
			session=HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			
			Projects pObj=(Projects)session.get(Projects.class, projects.getPrID());
			session.delete(pObj);
			
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
	}
	
	public void deleteResume(Resume resume)
	{
		Transaction trans = null;
		Session session = null;
		try
		{
			session=HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			
			Resume rObj = (Resume) session.get(Resume.class, resume.getRID());
			session.delete(rObj);
			
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
		
	}
	
	public Set<Expertise> viewExpertise(ProfessorProfile professor)
	{
		Session session = null;
		Transaction trans = null;
		Set<Expertise> list = null;
		
		try {
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			
			ProfessorProfile professorProfile = (ProfessorProfile)session.get(ProfessorProfile.class, professor.getPpID());
			list = professorProfile.getExpertise();
			Hibernate.initialize(list);
//			list.forEach(i -> i.getSubjects().getCourseCode());
			
			trans.commit();
			
		} catch (Exception e) {
			if(trans != null){
				trans.rollback();
			}
			e.printStackTrace();
		}

		session.close();
		return list;
	}
	
	public void addExpertise(Expertise expertise,ProfessorProfile professor)
	{
		try
		{
			Session session = HibernateFactory.getSession().openSession();
			session.beginTransaction();
			int count= (int) session.createSQLQuery("select COUNT(*) from Expertise where SubjID=:subj and PPID=:pp")
					.setInteger("subj", expertise.getSubjects().getSubjID())
				.setInteger("pp", professor.getPpID()).uniqueResult();

			if(count <= 0){
				session.save(expertise);
			}
			session.getTransaction().commit();
			session.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void deleteExpertise(Expertise expertise)
	{
		Transaction trans = null;
		Session session = null;
		try
		{
			session = HibernateFactory.getSession().openSession();
			trans = session.beginTransaction();
			
			System.out.println(expertise.getProfessorProfile().getPpID());
			System.out.println(expertise.getSubjects().getSubjID());
			
			Integer id =(Integer)session.createSQLQuery("select expertID from Expertise where PPID=:ppid and subjID=:subjID")
					.setInteger("ppid", expertise.getProfessorProfile().getPpID()).
					setInteger("subjID", expertise.getSubjects().getSubjID()).uniqueResult();
			

			Expertise eObj=(Expertise)session.get(Expertise.class, id);
			session.delete(eObj);
			
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
	}
	
	
}
