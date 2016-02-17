package com.helper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

import javax.imageio.ImageIO;

import com.HibernateUtil.ProfilingHelper;
import com.google.zxing.WriterException;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
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
	static {
		BodyName = new Font(FontFamily.HELVETICA , 16, Font.BOLD);
		link = new Font(FontFamily.HELVETICA, 8);
		date = new Font(FontFamily.HELVETICA, 8);
		title = new Font(FontFamily.HELVETICA  , 12, Font.BOLD);
		subContent = new Font(FontFamily.HELVETICA , 11, Font.BOLD);       
		link.setColor(BaseColor.BLUE.darker());
		date.setColor(BaseColor.BLUE.darker());
		subContent.setColor(BaseColor.BLUE.darker());
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

		PdfPTable achievementsTable = new PdfPTable(5);
		Set<Achievements> aSet = p_helper.viewAchievements(profile);

		for(Achievements achieve : aSet){
			
			try {
				
				File qrCode = HelperClass.createQRImage(achieve.getAchievement_Certificate_Name(),
									achieve.getAchievement_Certificate_Url());
				Image qrImg = Image.getInstance(qrCode.getAbsolutePath());
				qrImg.scaleAbsolute(70f, 70f);
				
				PdfPCell achievementCell = new PdfPCell();
				
				achievementCell.addElement(qrImg);
				achievementCell.addElement(new Paragraph(achieve.getAchievement_Certificate_Name(), title));
				achievementCell.addElement(new Paragraph(achieve.getAchievement_Certificate_Url(), link));
				achievementCell.setHorizontalAlignment(Element.ALIGN_BOTTOM);
				achievementCell.setBorderColor(BaseColor.WHITE);
				achievementCell.setPaddingTop(40);
				achievementsTable.addCell(achievementCell);
			}
			catch(WriterException | IOException | BadElementException e){
				e.printStackTrace();
			}
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
		for(Expertise expertise : eSet)
	 	{	    
		    PdfPCell psttCell = new PdfPCell();
		    psttCell.addElement(new Paragraph("\u2022 " +expertise.getSubjects().getCourseCode(), title));
		    psttCell.setBorderColor(BaseColor.WHITE);
		    psttCell.setPaddingBottom(20f);
		    psttContentTable.addCell(psttCell);
	 	}
		
		
		return psttContentTable;
	}

}
