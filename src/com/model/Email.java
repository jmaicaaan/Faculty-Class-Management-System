package com.model;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Email extends Authenticator{

	private String fromAddress;
	private String fromPassword;
	private String toAddress;
	private String message;

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getFromPassword() {
		return fromPassword;
	}
	public void setFromPassword(String fromPassword) {
		this.fromPassword = fromPassword;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	
	public Email(){
		
	}

	public Email(String fromAddress, String fromPassword){
       
		this.fromAddress = fromAddress;
       this.fromPassword = fromPassword;
	}
	public PasswordAuthentication getPasswordAuthentication(){
      
	   return new PasswordAuthentication(fromAddress, fromPassword);
	}
	
	
}
