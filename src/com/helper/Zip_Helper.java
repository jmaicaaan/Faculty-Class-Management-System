package com.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip_Helper {
	
	public static ZipEntry zipEntry = null;
	private static FileInputStream inputStream = null;

	 public static void addToZip(String file, ZipOutputStream zipFile ){
	        byte[] buffer = new byte[1024];
	        int i;
	        
	        try{
	            zipEntry = new ZipEntry(file);
	            zipEntry.setMethod(ZipEntry.DEFLATED);
	            zipFile.putNextEntry(zipEntry);
	            inputStream = new FileInputStream(file);
	            while ((i = inputStream.read(buffer)) > 0) zipFile.write(buffer, 0, i);
	            zipFile.closeEntry();
	            inputStream.close();
	        } catch (IOException e){
	        	e.printStackTrace();
	        }
	 
	 }
}	




