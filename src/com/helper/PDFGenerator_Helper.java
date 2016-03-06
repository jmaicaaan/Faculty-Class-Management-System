package com.helper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

import com.HibernateUtil.ProfilingHelper;
import com.google.zxing.WriterException;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.model.Achievements;
import com.model.Expertise;
import com.model.ProfessorProfile;
import com.model.Projects;
import com.model.Users;

public class PDFGenerator_Helper {

	static Font BodyName = null,
			link = null,
			date = null,
			title = null,
			subContent = null;
	static BaseFont baseFont = null;

	static {

		BodyName = new Font(baseFont , 16, Font.BOLD);
		link = new Font(baseFont, 10);
		date = new Font(baseFont, 8);
		title = new Font(baseFont  , 14, Font.BOLD);
		subContent = new Font(baseFont , 11, Font.BOLD);       
		link.setColor(BaseColor.BLUE.darker());
		date.setColor(BaseColor.BLUE.darker());
		subContent.setColor(BaseColor.BLUE.darker());
	}

	public PDFGenerator_Helper() {
		// TODO Auto-generated constructor stub
		try {
			baseFont = BaseFont.createFont("C:\\Users\\Jm\\Desktop\\Files\\Java\\Workspace_EclipseMars\\Faculty-Class-Management-System\\web\\resources\\Fonts\\arial-unicode-ms.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
		} catch (DocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static ProfilingHelper p_helper = new ProfilingHelper();

	public static Image getUserImage(Users user){

		Image image = null;
		try{
			image = Image.getInstance(new URL(user.getPictureUrl()));
		}catch(Exception e){
			e.printStackTrace();
		}
		return image;
	}

	public static String getUserName(Users user){
		return user.getFirstName() + " " + user.getLastName();
	}

	public static PdfPTable getAchievements(ProfessorProfile profile){

		float[] achievementsColumnWidths = {1f, 2f};
		PdfPTable achievementsTable = new PdfPTable(2);

		Set<Achievements> aSet = p_helper.viewAchievements(profile);

		try {
			achievementsTable.setWidths(achievementsColumnWidths);
			for(Achievements achieve : aSet){

				try {

					File qrCode = HelperClass.createQRImage(achieve.getAchievement_Certificate_Name(),
							achieve.getAchievement_Certificate_Url());
					Image qrImg = Image.getInstance(qrCode.getAbsolutePath());
					qrImg.setAbsolutePosition(10,500);
					qrImg.scalePercent(80);

					PdfPCell achievementContentCellQRCode = new PdfPCell();

					achievementContentCellQRCode.addElement(qrImg);
					achievementContentCellQRCode.setHorizontalAlignment(Element.ALIGN_BOTTOM);
					achievementContentCellQRCode.setVerticalAlignment(Element.ALIGN_MIDDLE);
					achievementContentCellQRCode.setHorizontalAlignment(Element.ALIGN_CENTER);
					achievementContentCellQRCode.setBorderColor(BaseColor.WHITE);

					PdfPCell achievementContentCell = new PdfPCell();


					achievementContentCell.addElement(new Paragraph(achieve.getAchievement_Certificate_Name(), title));
					achievementContentCell.addElement(new Paragraph(achieve.getAchievement_Certificate_Url(), link));
					achievementContentCell.setBorderColor(BaseColor.WHITE);
					achievementContentCell.setPaddingTop(5);
					achievementContentCell.setPaddingLeft(-15);


					achievementsTable.addCell(achievementContentCellQRCode);
					achievementsTable.addCell(achievementContentCell);
				}
				catch(WriterException | IOException | BadElementException e){
					e.printStackTrace();
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}


		achievementsTable.getDefaultCell().setBorder(0);
		achievementsTable.completeRow();	 
		return achievementsTable;
	}

	public static PdfPTable getProjects(ProfessorProfile profile){

		Set<Projects> pSet = p_helper.viewProjects(profile);
		PdfPTable projectsContentTable = new PdfPTable(1);

		for(Projects projects : pSet){
			
			PdfPCell projectsCell = new PdfPCell();
			projectsCell.addElement(new Paragraph("\u2022 " + projects.getProjectName(), title));
			projectsCell.addElement(new Paragraph("      ["+projects.getProjectDate()+"]", date));
			projectsCell.setBorderColor(BaseColor.WHITE);
			projectsCell.setPaddingBottom(20f);
			projectsContentTable.addCell(projectsCell);
		}
		return projectsContentTable;
	}

	public static PdfPTable getPrefferedSubjects(ProfessorProfile profile){

		PdfPTable psttContentTable = new PdfPTable(1);

		Set<Expertise> eSet = p_helper.viewExpertise(profile);
		for(Expertise expertise : eSet){	    
			
			PdfPCell psttCell = new PdfPCell();
			psttCell.addElement(new Paragraph("\u2022 " +expertise.getSubjects().getCourseCode(), title));
			psttCell.setBorderColor(BaseColor.WHITE);
			psttCell.setPaddingBottom(20f);
			psttContentTable.addCell(psttCell);
		}
		return psttContentTable;
	}
	
	public static PdfPCell createCell(String content, Font font){
		
		PdfPCell contentCell = new PdfPCell(new Paragraph(content, font));
		
		contentCell.setUseVariableBorders(true);
		contentCell.setBorderColorTop(BaseColor.WHITE);
		contentCell.setBorderColorRight(BaseColor.WHITE);
		contentCell.setBorderColorLeft(BaseColor.WHITE);
		contentCell.setBorderColorBottom(BaseColor.GRAY.brighter());
		contentCell.setPaddingTop(20);
		contentCell.setPaddingLeft(10);
		contentCell.setPaddingBottom(15);
		
		return contentCell;
	}
}
