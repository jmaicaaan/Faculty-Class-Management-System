package com.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.HibernateUtil.AttendanceHelper;
import com.model.AccountType;
import com.model.Password;
import com.model.Users;

public class AttendanceHelperClass {

	public List<Users> readUploadedClasslist(FileModel fModel) throws Exception{

		List<Users> uList = new ArrayList<Users>();
		Users user = null;

		try {
			if(fModel.getFileFileName().contains(".csv") || fModel.getFileFileName().contains(".txt")){
				try(BufferedReader buffered = new BufferedReader(new FileReader(fModel.getFile()));) {

					String line = "";
					while( (line = buffered.readLine()) != null){
						String[] student = line.split(",");
						String idNumber = student[0].trim(),
								lastName = student[1].trim(),
								firstName = student[2].trim(),
								middleName = student[3].trim();

						user = new Users(idNumber, firstName, lastName,middleName);
						uList.add(user);
					}

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					throw e;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
		return uList;
	}

	public Users Student(Users user){
		AttendanceHelper a_helper = new AttendanceHelper();

		Users uObj = user;
		Password pObj = new Password(user);
		AccountType aObj = new AccountType(Utilities.STUDENT, user);

		uObj.setUsername(user.getIdNo());

		a_helper.addStudent(uObj);
		a_helper.addPassword(pObj);
		a_helper.addAccountType(aObj);

		return uObj;
	}
}
