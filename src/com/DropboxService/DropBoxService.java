package com.DropboxService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.Files;
import com.dropbox.core.v2.Files.ListFolderException;
import com.dropbox.core.v2.Files.Metadata;

public class DropBoxService implements DropboxApi {

	private static DbxClientV2 DBinstance;
	
	private static DbxClientV2 initializeDropBox(){
		
		DbxRequestConfig config = new DbxRequestConfig("dropbox", DropboxApi.locale);
		DbxClientV2 DBinstance = new DbxClientV2(config, DropboxApi.dropBoxToken);		
		return DBinstance;
	}
	
	public static DbxClientV2 getDropBoxInstance(){
		return DBinstance != null ? DBinstance : initializeDropBox();
	}
	
	@Override
	public String upload_Achievement_Certificate(String filepath) {
		// TODO Auto-generated method stub
		File file = new File(filepath);
		Files.FileMetadata metadata = null;
		
		try(InputStream in = new FileInputStream(file);) {
			metadata = getDropBoxInstance().files
					.uploadBuilder(DropboxApi.achievements_certificates_path + file.getName()).run(in);
			
			return getDropBoxInstance().sharing.createSharedLink(metadata.pathLower).url + "&raw=1";

		} catch (DbxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Something went wrong while uploading your file.";
		}
	}
	
	@Override
	public String uploadResume(String filepath) {
		// TODO Auto-generated method stub
		File file = new File(filepath);
		Files.FileMetadata metadata = null;
		
		try(InputStream in = new FileInputStream(file);) {
			metadata = getDropBoxInstance().files
					.uploadBuilder(DropboxApi.resume_path + file.getName()).run(in);
			
			return getDropBoxInstance().sharing.createSharedLink(metadata.pathLower).url + "&raw=1";

		} catch (DbxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Something went wrong while uploading your file.";
		}
	}
	
	@Override
	public List<Metadata> listFolders() {
		// TODO Auto-generated method stub
		List<Metadata> files = null;
		try {
			files = getDropBoxInstance().files.listFolder("/" + DropboxApi.appName).entries;
			
	
		
		} catch (ListFolderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return files;
	}
	
	@Override
	public String uploadUserImage(String filepath) {
		// TODO Auto-generated method stub
		File file = new File(filepath);
		Files.FileMetadata metadata = null;
		
		try(InputStream in = new FileInputStream(file);) {
			metadata = getDropBoxInstance().files
					.uploadBuilder(DropboxApi.userImage_path + file.getName()).run(in);
			
			return getDropBoxInstance().sharing.createSharedLink(metadata.pathLower).url + "&raw=1";

		} catch (DbxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Something went wrong while uploading your file.";
		}
	}
		
	
}
