package com.helper;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import com.HibernateUtil.GenericHelper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.model.AccountType;
import com.model.AuditLog;
import com.model.Password;
import com.model.Users;

public class HelperClass {

	private static byte[] key = {
			0x74, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x53, 0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x79
	};

	public static String CreateUsername(String firstname, String lastname) {
		String username = "";
		username = lastname.toLowerCase() 
				+ firstname.substring(0, 1);

		return username;
	}

	public static boolean verify_password(String newPass, String newPass_verify){
		return (newPass.equals(newPass_verify) || newPass_verify.equals(newPass)) ? true : false;
	}

	public static boolean isAdmin(String username, String password){
		return (username.equals(Utilities.adminUsername) && password.equals(Utilities.adminPassword)) ? true : false;
	}
	public static boolean isAdmin(Users user){
		
		String password = "";
		
		for(Password p : user.getPassword()){
			password = p.getPassword();
		}
		
		return (user.getUsername().equals(Utilities.adminUsername) && password.equals(Utilities.adminPassword)) ? true : false;
	}
	
	public static Users Admin(){
		Users users = new Users();
		users.setUsername(Utilities.adminUsername);
		users.setFirstName(Utilities.adminUsername);
		users.setLastName(Utilities.adminUsername);
		users.setPictureUrl(Utilities.defaultImage);
		AccountType ac=  new AccountType();
		ac.setAccountType(Utilities.adminAccountType);
		Set<AccountType> acList = new HashSet<AccountType>();
		acList.add(ac);
		users.setAccountType(acList);

		return users;
	}
	
	public static boolean isSecretary(String username, String password){
		return (username.equals(Utilities.secUsername) && password.equals(Utilities.secPassword)) ? true : false;
	}
	
	public static boolean isSecretary(Users user){
		
		String password = "";
		
		for(Password p : user.getPassword()){
			password = p.getPassword();
		}
		
		return (user.getUsername().equals(Utilities.secUsername) && password.equals(Utilities.secPassword)) ? true : false;
	}

	public static Users Secretary(){

		Users users = new Users();
		users = new Users();
		users.setUsername(Utilities.secUsername);
		users.setFirstName(Utilities.secUsername);
		users.setLastName(Utilities.secUsername);
		users.setPictureUrl(Utilities.defaultImage);
		AccountType ac=  new AccountType();
		ac.setAccountType(Utilities.secAccountType);
		Set<AccountType> acList = new HashSet<AccountType>();
		acList.add(ac);
		users.setAccountType(acList);

		return users;
	}

	public static String encrypt(String strToEncrypt) {
		String encryptedString = null;
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			encryptedString = Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes()));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return encryptedString;
	}

	public static String decrypt(String codeToDecrypt){
		String decryptedString = null;
		try{
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			decryptedString = new String(cipher.doFinal(Base64.decodeBase64(codeToDecrypt)));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return decryptedString;
	}

	public static File createQRImage(String title, String encodedString) throws WriterException, IOException {

		/** Based from API with a little modification */

		final String qrCodeText = encodedString,
				fileType = "png",
				QRfileName = title + "." + fileType;
		final int size = 150;

		File qrFile = new File(QRfileName);

		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText,
				BarcodeFormat.QR_CODE, size, size);
		int matrixWidth = byteMatrix.getWidth();

		// Make the BufferedImage that will hold the QRCode


		BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();

		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		graphics.setColor(Color.BLACK);

		for (int i = 0; i < matrixWidth; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}

		ImageIO.write(image, fileType, qrFile);
		return qrFile;
	}

	public static boolean isFileSizeCorrect(File file){
		//File.length returns byte

		long fileSizeByte = file.length();
		return fileSizeByte < Utilities.maximumFileSizeMB ? true : false; 
	}

	public static boolean isFileContentTypeResume(String filename, String fileType){
		try {
			if(fileType.indexOf("application/") > -1){
				if( filename.contains(".docx") || filename.contains(".pdf")){
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
		return false;
	}

	public static boolean isFileContentTypeImage(String fileType) throws Exception{

		// image/* - format
		return fileType.indexOf("image/") > -1 ? true : false;
	}
	
	public static String getPropertyFile(String propertyFileName, String propertyName){
		
		Properties prop = new Properties();
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		String result = "";
		try {
			prop.load(classloader.getResourceAsStream("/" + propertyFileName + ".properties"));
			
			result  = prop.getProperty(propertyName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static Users validateUser(Users user, Users dbResult){
		
		String password = "";
		Users userModel = null;
		
		for(Password p : user.getPassword()){
			password = p.getPassword();
		}
		
		for(Password p : dbResult.getPassword()){
			if( (p.getPassword().equals(HelperClass.encrypt(password)) && 
					(dbResult.getUsername().equalsIgnoreCase(user.getUsername())))){
				userModel = dbResult;
			}
		}
		
		return userModel;
	}
	
	public static String setJSESSIONID(Map<String, Object> userSession){
		return HelperClass.encrypt(((Users)userSession.get(Utilities.user_sessionName)).getUsername());
	}
	
	public void setAdminLoginAuditLog(Users usersModel){

		GenericHelper g_helper = new GenericHelper();
		AuditLog auditLog = new AuditLog(AuditLogUtil.loginAction, AuditLogUtil.loginType, usersModel);
		g_helper.AddAuditLog(auditLog);
	}
}
