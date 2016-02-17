package com.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.model.ProfessorProfile;
import com.model.Users;

public class PDFGenerator 
{
	private ProfessorProfile professorProfile = new ProfessorProfile();
	private Users user = new Users();

	

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



	public String generateProfessorPDF(String serverPath){
		String filepath = "";
		
		try{
			filepath = serverPath + File.separator + user.getUsername() + ".pdf";
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
			document.open();

			float[] headerColumnWidths = {1f, 2f};

			PdfPTable table = new PdfPTable(2);
			table.setWidths(headerColumnWidths);
			table.setWidthPercentage(100); //Width 100%
			table.setSpacingBefore(10f); //Space before table
			table.setSpacingAfter(10f); //Space after table

			Font headerName = new Font(FontFamily.HELVETICA, 18, Font.BOLD);
			Font subheader = new Font(FontFamily.HELVETICA, 14);
			subheader.setColor(BaseColor.BLUE.darker());



			//PROFILE PIC
			Image image1 = Image.getInstance(PDFGenerator_Helper.getUserImage(getUser()));
			//Fixed Positioning
			image1.setAbsolutePosition(70f, 700f);
			//Scale to new height and new width of image
			image1.scaleAbsolute(150, 150);

			//ISP LOGO
			Image image2 = Image.getInstance("/Faculty-Class-Management-System/web/resources/img/is.jpg");
			//Fixed Positioning
			image2.setAbsolutePosition(70f, 700f);
			//Scale to new height and new width of image
			image2.scaleAbsolute(50, 50);

			//SMIT LOGO
			Image image3 = Image.getInstance("/Faculty-Class-Management-System/web/resources/img/smit.jpg");

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
			table = new PdfPTable(2);
			table.setWidths(headerColumnWidths);
			table.setWidthPercentage(100); //Width 100%

			PdfPCell acheivementsCell1 = new PdfPCell(new Paragraph("ACHIEVEMENTS", PDFGenerator_Helper.BodyName));
			acheivementsCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			acheivementsCell1.setBorderColor(BaseColor.WHITE);   
			acheivementsCell1.setPaddingLeft(10);

			PdfPCell acheivementsCell2 = new PdfPCell(new Paragraph("", PDFGenerator_Helper.BodyName));
			acheivementsCell2.setBorderColor(BaseColor.WHITE);   

			table.addCell(acheivementsCell1);
			table.addCell(acheivementsCell2);
			document.add(table);

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
		
		return filepath;
	}
}
