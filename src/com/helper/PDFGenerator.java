package com.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.model.Attendance;
import com.model.FacultyAssign;
import com.model.ProfessorProfile;
import com.model.Users;

public class PDFGenerator 
{
	private ProfessorProfile professorProfile = new ProfessorProfile();
	private Users user = new Users();


	public PDFGenerator(){}

	public PDFGenerator(Users user, ProfessorProfile profile){
		setProfessorProfile(profile);
		setUser(user);
	}

	public ProfessorProfile getProfessorProfile() {
		return professorProfile;
	}
	public void setProfessorProfile(ProfessorProfile professorProfile) {
		this.professorProfile = professorProfile;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}


	public String generateAssignedProfessorPDF(List<FacultyAssign> faList, String serverPath){
		String filepath = "";

		try{
			filepath = serverPath + File.separator + "AssignedProfessors.pdf";
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));

			//
			document.addAuthor("Team Avengers");
			document.addCreationDate();
			document.addProducer();
			document.addCreator("Team Avengers");
			document.addTitle("Professor Profile");

			document.open();

			//		Header {		  
			//Settings
			BaseFont baseFont = BaseFont.createFont("C:\\Users\\Jm\\Desktop\\Files\\Java\\Workspace_EclipseMars\\Faculty-Class-Management-System\\web\\resources\\Fonts\\arial-unicode-ms.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
			Font headerFont = new Font(baseFont, 12);
			//---------HEADER CONTENT---------//

			Image ispLogo = Image.getInstance("C:\\Users\\Jm\\Desktop\\Files\\Java\\Workspace_EclipseMars\\Faculty-Class-Management-System\\web\\resources\\img\\is.jpg");
			ispLogo.scaleAbsolute(50f, 50f);
			ispLogo.setAbsolutePosition(205f, 725f);
			document.add(ispLogo);


			Image dlsCsbLogo = Image.getInstance("C:\\Users\\Jm\\Desktop\\Files\\Java\\Workspace_EclipseMars\\Faculty-Class-Management-System\\web\\resources\\img\\DLS-CSB_Seal.png");
			dlsCsbLogo.scaleAbsolute(90f, 90f);
			dlsCsbLogo.setAbsolutePosition(255f, 725f);
			document.add(dlsCsbLogo);

			Image smitLogo = Image.getInstance("C:\\Users\\Jm\\Desktop\\Files\\Java\\Workspace_EclipseMars\\Faculty-Class-Management-System\\web\\resources\\img\\smit.jpg");
			smitLogo.scaleAbsolute(55f, 45f);
			smitLogo.setAbsolutePosition(350f, 725f);
			document.add(smitLogo);

			document.add(new Paragraph(" "));

			PdfPTable headerTable = new PdfPTable(1);
			headerTable.setWidthPercentage(100);
			headerTable.setSpacingBefore(70);
			headerTable.setSpacingAfter(30);

			PdfPCell header1 = new PdfPCell(new Paragraph("De La Salle - College of Saint Benilde", headerFont));
			header1.setHorizontalAlignment(Element.ALIGN_CENTER);
			header1.setBorderColor(BaseColor.WHITE);

			PdfPCell header2 = new PdfPCell(new Paragraph("2455 Taft Avenue", headerFont));
			header2.setHorizontalAlignment(Element.ALIGN_CENTER);
			header2.setBorderColor(BaseColor.WHITE);

			PdfPCell header3 = new PdfPCell(new Paragraph("Manila Philippines", headerFont));
			header3.setHorizontalAlignment(Element.ALIGN_CENTER);
			header3.setBorderColor(BaseColor.WHITE);


			//		        headerTable.addCell(logoHeaderCell);
			headerTable.addCell(header1);
			headerTable.addCell(header2);
			headerTable.addCell(header3);

			document.add(headerTable);

			//--------------------------------//

			//		}
			//  	Content {
			//Settings  
			Font title = new Font(FontFamily.HELVETICA, 18, Font.BOLD);
			Font subTitle = new Font(FontFamily.HELVETICA, 16, Font.BOLD);
			Font scheduleTitleCell = new Font(baseFont, 12, Font.BOLD);
			Font scheduleContentCellFont = new Font(baseFont, 10);


			//---------BOBDY CONTENT---------//
			Paragraph reportTitle = new Paragraph("Schedule Management Reports", title);
			reportTitle.setAlignment(Element.ALIGN_CENTER);  
			document.add(reportTitle);
			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));

			PdfPTable subTitleTable = new PdfPTable(1);
			subTitleTable.setWidthPercentage(100);
			subTitleTable.setSpacingBefore(0);
			subTitleTable.setSpacingAfter(0);

			PdfPCell subTitleCell = new PdfPCell(new Paragraph("Faculty assigned to I.S. subjects", subTitle));
			subTitleCell.setUseVariableBorders(true);
			subTitleCell.setBorderColorTop(BaseColor.WHITE);
			subTitleCell.setBorderColorRight(BaseColor.WHITE);
			subTitleCell.setBorderColorLeft(BaseColor.WHITE);
			subTitleCell.setBorderColorBottom(BaseColor.LIGHT_GRAY);
			subTitleCell.setBorderWidthBottom(1f);
			subTitleCell.setPaddingBottom(15);

			subTitleTable.addCell(subTitleCell);
			document.add(subTitleTable);

			//---MAIN TABLE--//

			PdfPTable scheduleHeaderTable = new PdfPTable(6);
			scheduleHeaderTable.setWidthPercentage(100);
			scheduleHeaderTable.setSpacingBefore(0);
			scheduleHeaderTable.setSpacingAfter(0);

			String[] titleArray = {"Course Code", "Section", "Room", "Day", "Time" ,"Professor"};

			for(String columnTitle : titleArray){
				PdfPCell titleCell = new PdfPCell(new Paragraph(columnTitle, scheduleTitleCell));
				titleCell.setUseVariableBorders(true);
				titleCell.setBorderColorTop(BaseColor.WHITE);
				titleCell.setBorderColorRight(BaseColor.WHITE);
				titleCell.setBorderColorLeft(BaseColor.WHITE);
				titleCell.setBorderColorBottom(BaseColor.GRAY.brighter());
				titleCell.setPaddingTop(20);
				// titleCell.setPaddingRight(15);
				titleCell.setPaddingLeft(10);
				titleCell.setPaddingBottom(15); 

				//titleCell.setBorderWidth(2f);
				scheduleHeaderTable.addCell(titleCell);
			}
			scheduleHeaderTable.getDefaultCell().setBorder(0);
			document.add(scheduleHeaderTable);

			//Content
			PdfPTable contentTable = new PdfPTable(6);
			contentTable.setWidthPercentage(100);
			contentTable.setSpacingBefore(0);
			contentTable.setSpacingAfter(0);

			for(FacultyAssign data : faList){

				contentTable.addCell(PDFGenerator_Helper.createCell(data.getSchedule().getSubjects().getCourseCode(), scheduleContentCellFont));
				contentTable.addCell(PDFGenerator_Helper.createCell(data.getSchedule().getSection(), scheduleContentCellFont));
				contentTable.addCell(PDFGenerator_Helper.createCell(data.getSchedule().getRoom(), scheduleContentCellFont));
				contentTable.addCell(PDFGenerator_Helper.createCell(data.getSchedule().getDay(), scheduleContentCellFont));
				contentTable.addCell(PDFGenerator_Helper.createCell(data.getSchedule().getTime(), scheduleContentCellFont));
				contentTable.addCell(PDFGenerator_Helper.createCell(data.getProfessorProfile().getUsers().getFirstName() + " " 
						+ data.getProfessorProfile().getUsers().getLastName(), scheduleContentCellFont));

			}

			document.add(contentTable);
			document.close();
			writer.close();

		} catch (Exception e){
			e.printStackTrace();
		}
		return filepath;
	}

	public String generateProfessorPDF(String serverPath){
		String filePath = "";

		try{
			filePath = serverPath + File.separator + user.getUsername() + ".pdf";
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
			document.open();

			float[] headerColumnWidths = {1f, 2f};

			PdfPTable table = new PdfPTable(2);
			table.setWidths(headerColumnWidths);
			table.setWidthPercentage(100); //Width 100%
			table.setSpacingBefore(10f); //Space before table
			table.setSpacingAfter(10f); //Space after table

			BaseFont baseFont = BaseFont.createFont(serverPath + File.separator + "\\resources\\Fonts\\arial-unicode-ms.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
			Font headerName = new Font(baseFont, 18, Font.BOLD);
			Font subheader = new Font(baseFont, 14);
			subheader.setColor(BaseColor.BLUE.darker());



			//PROFILE PIC
			Image image1 = Image.getInstance(PDFGenerator_Helper.getUserImage(getUser()));
			//Fixed Positioning
			image1.setAbsolutePosition(70f, 700f);
			//Scale to new height and new width of image
			image1.scaleAbsolute(150, 150);

			//ISP LOGO
			Image image2 = Image.getInstance(serverPath + File.separator + "\\resources\\img\\is.jpg");
			//Fixed Positioning
			image2.setAbsolutePosition(70f, 700f);
			//Scale to new height and new width of image
			image2.scaleAbsolute(50, 50);

			//SMIT LOGO
			Image image3 = Image.getInstance(serverPath + File.separator + "\\resources\\img\\smit.jpg");

			//Fixed Positioning
			image3.setAbsolutePosition(280f, 665f);
			//Scale to new height and new width of image
			image3.scaleAbsolute(70, 50);

			image1.scaleAbsolute(150, 150);
			PdfPCell headerCell1 = new PdfPCell(image1);
			headerCell1.setBorderColor(BaseColor.WHITE);
			headerCell1.setPaddingLeft(10);
			headerCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			headerCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

			PdfPCell headerCell2 = new PdfPCell();
			headerCell2.addElement(new Paragraph(PDFGenerator_Helper.getUserName(user), headerName));
			headerCell2.addElement(new Paragraph("Information Systems Program Faculty Member", subheader));
			headerCell2.addElement(new Paragraph(" "));
			headerCell2.addElement(image2);
			document.add(image3);
			headerCell2.setBorderColor(BaseColor.WHITE);      
			headerCell2.setPaddingLeft(10);

			table.addCell(headerCell1);
			table.addCell(headerCell2);

			document.add(table);
			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));



			//--------------ACHEIVEMENTS--------------//
			//Achievements Header
			PdfPTable achievementsHeadertable = new PdfPTable(2);
			achievementsHeadertable.setWidths(headerColumnWidths);
			achievementsHeadertable.setSpacingAfter(20);
			achievementsHeadertable.setWidthPercentage(100);

			PdfPCell acheivementsCell1 = new PdfPCell(new Paragraph("ACHIEVEMENTS", PDFGenerator_Helper.BodyName));
			acheivementsCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			acheivementsCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			acheivementsCell1.setBorderColor(BaseColor.WHITE);   
			acheivementsCell1.setPaddingLeft(10);

			PdfPCell acheivementsCell2 = new PdfPCell(new Paragraph("", PDFGenerator_Helper.BodyName));
			acheivementsCell2.setBorderColor(BaseColor.WHITE);   

			achievementsHeadertable.addCell(acheivementsCell1);
			achievementsHeadertable.addCell(acheivementsCell2);
			document.add(achievementsHeadertable);

			//Achievements Content

			document.add(PDFGenerator_Helper.getAchievements(getProfessorProfile()));



			//--------------PROJECTS--------------//
			//Projects Header
			PdfPTable projectsTable = new PdfPTable(2);
			projectsTable.setWidths(headerColumnWidths);
			projectsTable.setWidthPercentage(100); //Width 100%
			projectsTable.setSpacingBefore(50f); //Space before table
			projectsTable.setSpacingAfter(10f); //Space before table

			PdfPCell projectsHeaderCell = new PdfPCell(new Paragraph("PROJECTS", PDFGenerator_Helper.BodyName));

			projectsHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			projectsHeaderCell.setBorderColor(BaseColor.WHITE);   
			projectsHeaderCell.setPaddingLeft(10);

			PdfPCell projectsHeaderCell2 = new PdfPCell(new Paragraph("", PDFGenerator_Helper.BodyName));
			projectsHeaderCell2.setBorderColor(BaseColor.WHITE);   

			projectsTable.addCell(projectsHeaderCell);
			projectsTable.addCell(projectsHeaderCell2);
			document.add(projectsTable);

			//Projects Content			    

			document.add(PDFGenerator_Helper.getProjects(getProfessorProfile()));


			//--------------PROJECTS--------------// 

			//--------------PREFERRED SUBJECT TO TEACH--------------//
			//PSTT Header
			PdfPTable psttTable = new PdfPTable(1);
			//psttTable.setWidths(headerColumnWidths);
			psttTable.setWidthPercentage(100); //Width 100%
			psttTable.setSpacingBefore(10f); //Space before table
			psttTable.setSpacingAfter(10f); //Space before table

			PdfPCell psttHeaderCell = new PdfPCell(new Paragraph("PREFERRED SUBJECTS TO TEACH", PDFGenerator_Helper.BodyName));
			psttHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			psttHeaderCell.setBorderColor(BaseColor.WHITE);   
			psttHeaderCell.setPaddingLeft(10);



			psttTable.addCell(psttHeaderCell);

			document.add(psttTable);


			//Preferred Subjects to teach Content			    

			document.add(PDFGenerator_Helper.getPrefferedSubjects(professorProfile));

			document.close();
			writer.close();

		}
		catch (Exception e){
			e.printStackTrace();
		}

		return filePath;
	}

	public String generateAchievementsPDF(String serverPath, List<Attendance> attendance){
		String filePath = "";
		filePath = serverPath + File.separator + "AttendanceManagement_Reports.pdf";
		try{
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));

			//Author
			document.addAuthor("Team Avengers");
			document.addCreationDate();
			document.addProducer();
			document.addCreator("Team Avengers");
			document.addTitle("Professor Profile");	        

			document.open();

			System.out.println(serverPath);
			//					Header {		  
			//Settings
			BaseFont baseFont = BaseFont.createFont(serverPath + "/resources/Fonts/arial-unicode-ms.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
			Font headerFont = new Font(baseFont, 12);	 
			Font title = new Font(FontFamily.HELVETICA, 18, Font.BOLD);
			Font subTitle = new Font(FontFamily.HELVETICA, 17, Font.BOLD);
			Font attendanceTitleCell = new Font(baseFont, 12, Font.BOLD);
			Font attendanceContentCell = new Font(baseFont, 10);
			Font headerName = new Font(baseFont, 18, Font.BOLD);
			Font subheader = new Font(baseFont, 14);
			//---------HEADER CONTENT---------//


			Image ispLogo = Image.getInstance(serverPath + "/resources/img/is.jpg");
			ispLogo.scaleAbsolute(50f, 50f);
			ispLogo.setAbsolutePosition(205f, 725f);
			document.add(ispLogo);

			Image dlsCsbLogo = Image.getInstance(serverPath + "/resources/img/DLS-CSB_Seal.png");
			dlsCsbLogo.scaleAbsolute(90f, 90f);
			dlsCsbLogo.setAbsolutePosition(255f, 725f);
			document.add(dlsCsbLogo);

			Image smitLogo = Image.getInstance(serverPath + "/resources/img/smit.jpg");
			smitLogo.scaleAbsolute(55f, 45f);
			smitLogo.setAbsolutePosition(350f, 725f);
			document.add(smitLogo);

			document.add(new Paragraph(" "));

			PdfPTable headerTable = new PdfPTable(1);
			headerTable.setWidthPercentage(100);
			headerTable.setSpacingBefore(70);
			headerTable.setSpacingAfter(30);

			PdfPCell header1 = new PdfPCell(new Paragraph("De La Salle - College of Saint Benilde", headerFont));
			header1.setHorizontalAlignment(Element.ALIGN_CENTER);
			header1.setBorderColor(BaseColor.WHITE);

			PdfPCell header2 = new PdfPCell(new Paragraph("2455 Taft Avenue", headerFont));
			header2.setHorizontalAlignment(Element.ALIGN_CENTER);
			header2.setBorderColor(BaseColor.WHITE);

			PdfPCell header3 = new PdfPCell(new Paragraph("Manila Philippines", headerFont));
			header3.setHorizontalAlignment(Element.ALIGN_CENTER);
			header3.setBorderColor(BaseColor.WHITE);


			//			        headerTable.addCell(logoHeaderCell);
			headerTable.addCell(header1);
			headerTable.addCell(header2);
			headerTable.addCell(header3);

			document.add(headerTable);

			document.add(new Paragraph(""));
			document.add(new Paragraph(""));
			//--------------------------------//
			//	 			}		        

			//--------BODY CONTENT-------------//

			Paragraph reportTitle = new Paragraph("Attendance Management Reports", title);
			reportTitle.setAlignment(Element.ALIGN_CENTER);  
			document.add(reportTitle);
			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));

			//PROFILE PIC
			Image profFilepci = null;
			for(Attendance aObj : attendance){
				String url = aObj.getClasslist().getUsers().getPictureUrl();
				System.out.println(url);
				if(url.contains("dropbox")){
					
					profFilepci = Image.getInstance(new URL(url));
				}else{
					profFilepci = Image.getInstance(serverPath + File.separator + url);
				}
				
			}
		
			profFilepci.setAbsolutePosition(50f, 700f);
			profFilepci.scaleAbsolute(100, 100);

			PdfPTable profileTable = new PdfPTable(2);
			profileTable.setWidthPercentage(100);
			profileTable.setSpacingAfter(0);
			profileTable.setSpacingBefore(0);

			PdfPCell profileCell1 = new PdfPCell(profFilepci);
			profileCell1.setBorderColor(BaseColor.WHITE);
			profileCell1.setPaddingLeft(-30);
			profileCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			profileCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

			PdfPCell profileCell2 = new PdfPCell();
			for(Attendance aObj : attendance){
				String studentName = aObj.getClasslist().getUsers().getFirstName() + " " +
							aObj.getClasslist().getUsers().getLastName();
				profileCell2.addElement(new Paragraph(studentName, headerName));
				break;
			}
			
			profileCell2.addElement(new Paragraph("Information Systems Program Student", subheader));
			profileCell2.addElement(new Paragraph(" "));
			profileCell2.setBorderColor(BaseColor.WHITE);      
			profileCell2.setPaddingLeft(-60);

			profileTable.addCell(profileCell1);
			profileTable.addCell(profileCell2);

			document.add(profileTable);

			document.add(new Paragraph(" "));

			PdfPTable subTitleTable = new PdfPTable(1);
			subTitleTable.setWidthPercentage(100);
			subTitleTable.setSpacingBefore(0);
			subTitleTable.setSpacingAfter(0);

			PdfPCell subTitleCell = new PdfPCell(new Paragraph("Accumulated attedance for all subjects", subTitle));
			subTitleCell.setUseVariableBorders(true);
			subTitleCell.setBorderColorTop(BaseColor.WHITE);
			subTitleCell.setBorderColorRight(BaseColor.WHITE);
			subTitleCell.setBorderColorLeft(BaseColor.WHITE);
			subTitleCell.setBorderColorBottom(BaseColor.LIGHT_GRAY);
			subTitleCell.setBorderWidthBottom(1f);
			subTitleCell.setPaddingBottom(15);

			subTitleTable.addCell(subTitleCell);
			document.add(subTitleTable);

			//---MAIN TABLE--//
			//
			PdfPTable attendanceHeaderTable = new PdfPTable(4);
			attendanceHeaderTable.setWidthPercentage(100);
			attendanceHeaderTable.setSpacingBefore(0);
			attendanceHeaderTable.setSpacingAfter(0);

			String[] titleArray = {"Course Code", "Schedule", "No. of Lates", "No. of Absents"};

			for(String columnTitle : titleArray){
				PdfPCell attendancetitleCell = new PdfPCell(new Paragraph(columnTitle, attendanceTitleCell));
				attendancetitleCell.setUseVariableBorders(true);
				attendancetitleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				attendancetitleCell.setBorderColorTop(BaseColor.WHITE);
				attendancetitleCell.setBorderColorRight(BaseColor.WHITE);
				attendancetitleCell.setBorderColorLeft(BaseColor.WHITE);
				attendancetitleCell.setBorderColorBottom(BaseColor.GRAY.brighter());
				attendancetitleCell.setPaddingTop(20);
				attendancetitleCell.setPaddingLeft(10);
				attendancetitleCell.setPaddingBottom(15); 
				attendanceHeaderTable.addCell(attendancetitleCell);
			}
			attendanceHeaderTable.getDefaultCell().setBorder(0);
			document.add(attendanceHeaderTable);

			//Content
			PdfPTable contentTable = new PdfPTable(4);
			contentTable.setWidthPercentage(100);
			contentTable.setSpacingBefore(0);
			contentTable.setSpacingAfter(0);

			String[] contentArray = {"ISPROJ1", "11:20 - 12:50PM", "3", "3"};
			
			for(Attendance aObj : attendance){
				contentTable.addCell(PDFGenerator_Helper
						.createCell(aObj.getClasslist().getFacultyAssign().getSchedule().getSubjects().getCourseCode(), attendanceTitleCell));
				contentTable.addCell(PDFGenerator_Helper.createCell(aObj.getClasslist().getFacultyAssign().getSchedule().getTime(), attendanceTitleCell));
				contentTable.addCell(PDFGenerator_Helper.createCell(String.valueOf(aObj.getNoOfLates()), attendanceTitleCell));
				contentTable.addCell(PDFGenerator_Helper.createCell(String.valueOf(aObj.getNoOfAbsences()), attendanceTitleCell));
			}
			document.add(contentTable);

			document.close();
			writer.close();

		} catch (Exception e){
			e.printStackTrace();
		}

		return filePath;
	}
}
