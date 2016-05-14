package com.helper;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.model.Email;
import com.model.Users;

public class SendEmail {
	
	public static void sendForgotPassword(Users uObj, String path){
		//if sending yourself a gmail, make sure you  turn on your gmail authentication
		try{
			Email email = new Email();
			 
			//Not Working
			email.setFromAddress("systemteamavengers@gmail.com");
			email.setFromPassword("teamavengers");				    
			email.setToAddress("fcmsteamavengers@gmail.com");
					  
			Properties emailProperties = new Properties();	
			emailProperties.put("mail.smtp.starttls.enable", "true");
			emailProperties.put("mail.smtp.host", "smtp.gmail.com");
			emailProperties.put("mail.smtp.port", "587");
			emailProperties.put("mail.smtp.auth", "true");
			emailProperties.put("mail.debug", "true");
			emailProperties.put("mail.smtp.user", email.getFromAddress());
			emailProperties.put("mail.smtp.password", email.getFromPassword());
			
			Session session = Session.getInstance(emailProperties,
			      new javax.mail.Authenticator() {
			          protected PasswordAuthentication getPasswordAuthentication() {
			               return new PasswordAuthentication(
			            		  email.getFromAddress(), email.getFromPassword());
			        }
			      });
	        
	        try{
	        	MimeMessage message = new MimeMessage(session);
	        	message.setFrom(new InternetAddress(email.getFromAddress(), "Faculty Class Management System"));
	        	message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(email.getToAddress()));
	        	message.setSubject("Change Password Request");
	        	message.setText("Welcome, " + uObj.getUsername() + "! To reset your password kindly click this link: " + createConfirmationLink(path, uObj));
		        
	        	 Transport transport = session.getTransport("smtp");
	             transport.connect("smtp.gmail.com", email.getFromAddress(), email.getFromPassword());
	            
	             
	             
	             message.saveChanges();
	             Transport.send(message);
	             transport.close();
	        	
	        }catch(MessagingException e){
	        	throw new RuntimeException(e);
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String createConfirmationLink(String path, Users uObj){
		
		return path + "Reset_Password?username=" + HelperClass.encrypt(uObj.getUsername());
	}
}

