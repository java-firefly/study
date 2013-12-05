package pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class CreatePDF {
	public static void main(String[] args) {
		//getChargerPdf();
	}
	public static void getProPdf(String fileName,IProject project){
		Document document = new Document(PageSize.A4, 10, 10, 50, 0);  
		try {
			PdfWriter.getInstance(document,  new FileOutputStream(fileName));
			document.open();
			//宋体
			BaseFont chinese = BaseFont.createFont("C:/WINDOWS/Fonts/STSONG.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			//仿宋
			
			BaseFont fangSong = BaseFont.createFont("C:/WINDOWS/Fonts/SIMFANG.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			BaseFont kaiti = BaseFont.createFont("C:/WINDOWS/Fonts/SIMKAI.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
			Font font = new Font(chinese, 12, Font.NORMAL); 
			Font songTi = new Font(chinese, 10.5f, Font.NORMAL); 
			Font fangSongFont22 = new Font(fangSong, 22, Font.NORMAL); 
			fangSongFont22.setStyle(Font.BOLD);
			
			Font fangSongFont26 = new Font(fangSong, 26, Font.NORMAL);
			fangSongFont26.setStyle(Font.BOLD);
			
			Font kaitiFont = new Font(kaiti, 14, Font.NORMAL);
			
			PdfPTable proTalbe = getProTable(songTi, project);
			
			document.add(getVersionTable(project));
			document.add(getParagraph("\n\n", font, Element.ALIGN_CENTER));
			document.add(getParagraph("中国人民大学科学研究基金项目", fangSongFont22, Element.ALIGN_CENTER));
			document.add(getParagraph("\n\n", font));
			document.add(getParagraph("申  请  书", fangSongFont26, Element.ALIGN_CENTER));
			document.add(getParagraph("\n\n\n", font));
			//添加表格
			document.add(proTalbe);
			
			document.add(getParagraph(getNullLine(7, 680, 60, proTalbe.getTotalHeight()),font));
			document.add(getParagraph("中国人民大学科学研究处", kaitiFont, Element.ALIGN_CENTER));
			document.add(getParagraph("2013年10月制", kaitiFont, Element.ALIGN_CENTER));
			document.add(getParagraph("\n\n", font));
			document.add(getBottom("2                              版本号："+project.getVersionId()));
		} catch (Exception e) {
			throw new BizException("","系统出现内部错误，请联系管理员");
		} finally{
			document.close();
		}
	}
	
	private static List<PdfCustomCell> getProCells(IProject project){
		List<PdfCustomCell> cells = new ArrayList<PdfCustomCell>();
		if(project instanceof ProjectInfo){
			ProjectInfo projectInfo = (ProjectInfo) project;
			cells.add(new PdfCustomCell("项目子类"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT,projectInfo.getProjectType(),3));
			cells.add(new PdfCustomCell("研究类型",2));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT,projectInfo.getResearchType(),2));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT,projectInfo.getSubResearchType(),2));
			
			cells.add(new PdfCustomCell("项目名称"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectInfo.getProjectName(), 9));
			cells.add(new PdfCustomCell("\n\n学科分类",1,3));
			cells.add(new PdfCustomCell("学科一"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectInfo.getDispalySubject1(), 8));
			cells.add(new PdfCustomCell("学科二"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectInfo.getDispalySubject2(), 8));
			cells.add(new PdfCustomCell("学科三"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectInfo.getDispalySubject3(), 8));
			cells.add(new PdfCustomCell("国民行业"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT,projectInfo.getIndustry(), 5));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectInfo.getIndustryCode(), 4));
			cells.add(new PdfCustomCell("预期成果"));
			
			String expertedProduct = projectInfo.getExpectedProduct();
			String [] expertedProducts = null;
			if(StringUtils.isNotBlank(expertedProduct)){
				expertedProducts = expertedProduct.split("、");
			}
			for (String string : expertedProducts) {
				cells.add(new PdfCustomCell(Element.ALIGN_LEFT,string,2));
			}
			for(int i = 5-expertedProducts.length; i > 0; i-- ){
				cells.add(new PdfCustomCell(Element.ALIGN_LEFT,"",2));
			}
			
			cells.add(new PdfCustomCell("申 请 人"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT,projectInfo.getChargerName(),4));
			cells.add(new PdfCustomCell("职工编号",2));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectInfo.getChargerCode() ,3));
			cells.add(new PdfCustomCell("所在单位"));
			CategoryDAO dao =  SpringContextUtil.getBean("categoryDAO");
			Category category = dao.getCategoryByCode("unit", projectInfo.getChargerWorkUnit());
			
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, category.getValue(),9));
			cells.add(new PdfCustomCell("研究周期"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT,projectInfo.getResPeriod(),4));
			cells.add(new PdfCustomCell("申请经费",2));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT,projectInfo.getApplyFee()+" 万元",3));
		}else if(project instanceof ProjectKeJi){
			ProjectKeJi projectKeJi = (ProjectKeJi) project;
			cells.add(new PdfCustomCell("项目子类"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT,projectKeJi.getProjectType(),3));
			cells.add(new PdfCustomCell("研究类型",2));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT,projectKeJi.getResearchType(),2));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT,projectKeJi.getSubResearchType(),2));
			
			cells.add(new PdfCustomCell("项目名称"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectKeJi.getProjectName(), 9));
			cells.add(new PdfCustomCell("\n\n学科分类",1,3));
			cells.add(new PdfCustomCell("学科一"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectKeJi.getDispalySubject1(), 8));
			cells.add(new PdfCustomCell("学科二"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectKeJi.getDispalySubject2(), 8));
			cells.add(new PdfCustomCell("学科三"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectKeJi.getDispalySubject3(), 8));
			cells.add(new PdfCustomCell("国民行业"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT,projectKeJi.getIndustry(), 5));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectKeJi.getIndustryCode(), 4));
			cells.add(new PdfCustomCell("预期成果"));
			String expertedProduct = projectKeJi.getExpectedProduct();
			String [] expertedProducts = null;
			if(StringUtils.isNotBlank(expertedProduct)){
				expertedProducts = expertedProduct.split("、");
			}
			for (String string : expertedProducts) {
				cells.add(new PdfCustomCell(Element.ALIGN_LEFT,string,2));
			}
			for(int i = 5-expertedProducts.length; i > 0; i-- ){
				cells.add(new PdfCustomCell(Element.ALIGN_LEFT,"",2));
			}
			
			cells.add(new PdfCustomCell("申 请 人"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT,projectKeJi.getChargerName(),4));
			cells.add(new PdfCustomCell("职工编号",2));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectKeJi.getChargerCode() ,3));
			cells.add(new PdfCustomCell("所在单位"));
			CategoryDAO dao =  SpringContextUtil.getBean("categoryDAO");
			Category category = dao.getCategoryByCode("unit", projectKeJi.getChargerWorkUnit());
			
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, category.getValue(),9));
			cells.add(new PdfCustomCell("研究周期"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT,projectKeJi.getResPeriod(),4));
			cells.add(new PdfCustomCell("申请经费",2));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT,projectKeJi.getApplyFee()+" 万元",3));
		}
		
		return cells;
	}
	public static void getChargerPdf(String filePath,IProject project,int chargerPageIndex){
		Document document = new Document(PageSize.A4, 10, 10, 50, 0);  
		try {
			PdfWriter.getInstance(document,  new FileOutputStream(filePath));
			document.open();
			
			//宋体
			BaseFont chinese = BaseFont.createFont("C:/WINDOWS/Fonts/STSONG.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			BaseFont heiti = BaseFont.createFont("C:/WINDOWS/Fonts/SIMHEI.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			BaseFont fangSong = BaseFont.createFont("C:/WINDOWS/Fonts/SIMFANG.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
			
			Font font = new Font(chinese, 12, Font.NORMAL); 
			Font songTi = new Font(chinese, 10.5f, Font.NORMAL); 
			Font heitiFont = new Font(heiti, 16, Font.NORMAL);
			Font fangSongFont = new Font(fangSong, 16, Font.NORMAL); 
			
			PdfPTable chargerTable = getChargerTable(songTi,project);
			document.add(getParagraph("表一、人员基本情况",10,heitiFont,Element.ALIGN_LEFT));
			//添加表格s
			document.add(chargerTable);
			
			document.add(getParagraph("\n\n", font));
			document.add(getParagraph("项目申请人的承诺：", heitiFont, Element.ALIGN_CENTER));
			document.add(getParagraph("本人填写的各项内容属实，没有知识产权争议。本项目如获资助，此《申请书》即为执行和验收的依据，本人严格遵守《中国人民大学基本科研业务费实施细则（修订稿）》的有关规定，按计划认真开展研究工作，取得预期研究成果。", 10, fangSongFont, Element.ALIGN_LEFT, 33));
			document.add(getParagraph("申请人（亲笔）：            ", 20, fangSongFont, Element.ALIGN_RIGHT));
			document.add(getParagraph("2013年    月   日           ", fangSongFont, Element.ALIGN_RIGHT));
			
			document.add(getParagraph(getNullLine(9, 684, 48, chargerTable.getTotalHeight()), font));
			
			document.add(getBottom(chargerPageIndex+"                              版本号："+project.getVersionId()));
			
		} catch (Exception e) {
			throw new BizException("","系统出现内部错误，请联系管理员");
		} finally{
			document.close();
		}
	}
	private static String getNullLine(int maxLine,float tableBaseHeight,float tableCellHeight,
			float tableHeight){
		StringBuffer sb = new StringBuffer(maxLine*2);
		int addLine = (int) ((tableHeight - tableBaseHeight)/tableCellHeight);
		for(int i = maxLine - addLine; i > 0; i--){
			sb.append("\n");
		}
		return sb.toString();
	}

	private static PdfPTable getVersionTable(IProject project) throws DocumentException, IOException{
		BaseFont chinese = BaseFont.createFont("C:/WINDOWS/Fonts/STSONG.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(chinese, 10, Font.NORMAL); 
		font.setStyle(Font.BOLD);
		
		//table 外层表格
		PdfPTable table=new PdfPTable(2);
		float[] tableWidths = {40f,60f};
		table.setWidths(tableWidths);
		
		//表格的第一个单元格的内容是一个表格
		PdfPTable tableCell=new PdfPTable(2);
		float [] tableCellWidths = {25f,75f}; 
		
		tableCell.setWidths(tableCellWidths);
		tableCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		PdfPCell pp1=new PdfPCell(new Paragraph ("版本号",font));
		pp1.setPaddingBottom(3);
		
		pp1.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableCell.addCell(pp1);
		
		PdfPCell pp2=new PdfPCell(new Paragraph (project.getVersionId(),font));
		pp2.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableCell.addCell(pp2);
		
		PdfPCell tableCell1 = new PdfPCell(tableCell);
		tableCell1.setBorder(0);
		table.addCell(tableCell1);
		
		PdfPCell tableCell2=new PdfPCell(new Paragraph (" ",font));
		tableCell2.setBorder(0);
		table.addCell(tableCell2);
		
		return table;
	}
	private static PdfPTable getChargerTable(Font font, IProject project) throws DocumentException{
		float[] tableWidths = {22.2f,17.3f,8.9f,16.4f,14.5f,20.4f};
		List<PdfCustomCell> cells = getChargerCells(project);
		return getPdfTable(font, tableWidths, cells);
	}
	
	private static PdfPTable getProTable(Font font,IProject project) throws DocumentException, IOException{
		float[] tableWidths = { 13.05f, 11.01f, 6.37f, 11.58f, 6.27f, 
				8.21f, 8.71f, 5.77f, 11.60f, 17.42f };
		List<PdfCustomCell> cells = getProCells(project);
		return getPdfTable(font, tableWidths, cells);
	}
	
	
	
	private static List<PdfCustomCell> getChargerCells(IProject project){
		List<PdfCustomCell> cells = new ArrayList<PdfCustomCell>();
		if(project instanceof ProjectInfo){
			ProjectInfo projectInfo = (ProjectInfo) project;
			cells.add(new PdfCustomCell("申请人",6));
			cells.add(new PdfCustomCell("申请人姓名"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT,projectInfo.getChargerName()));
			cells.add(new PdfCustomCell("性  别"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT,projectInfo.getChargerGender()));
			cells.add(new PdfCustomCell("出生日期"));
			
			Date date = projectInfo.getChargerBirthday();
			String displayDate = "";
			if(date != null){
				String dateStr = date.toString();
				displayDate = dateStr.substring(0, dateStr.indexOf(" "));
			}
			
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, displayDate));
			cells.add(new PdfCustomCell("行政职务"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectInfo.getChargerPost(),2));
			CategoryDAO categoryDAO = SpringContextUtil.getBean("categoryDAO");
			Category category = categoryDAO.getCategoryByCode("DM_TITLE_DEFINE_NAME", projectInfo.getChargerTitle());
			cells.add(new PdfCustomCell("职  称"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, category==null?"":category.getValue(), 2));
			cells.add(new PdfCustomCell("学  历"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectInfo.getEduLevel(),2));
			cells.add(new PdfCustomCell("学  位"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectInfo.getEduDegree(),2));
			cells.add(new PdfCustomCell("研究专长"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT,"",5));
			cells.add(new PdfCustomCell("所在研究基地类型"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectInfo.getBaseType(),5));
			cells.add(new PdfCustomCell("所在研究基地名称"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectInfo.getBaseName(),5));
			cells.add(new PdfCustomCell("办公电话"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectInfo.getChargerOfficeTel(),2));
			cells.add(new PdfCustomCell("住宅电话"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectInfo.getChargerTel(),2));
			cells.add(new PdfCustomCell("手机号码"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectInfo.getChargerPhoneNumber(),2));
			cells.add(new PdfCustomCell("电子邮件"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectInfo.getChargerEmail(),2));
		}else if(project instanceof ProjectKeJi){
			ProjectKeJi projectKeJi = (ProjectKeJi) project;
			cells.add(new PdfCustomCell("申请人",6));
			cells.add(new PdfCustomCell("申请人姓名"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT,projectKeJi.getChargerName()));
			cells.add(new PdfCustomCell("性  别"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT,projectKeJi.getChargerGender()));
			cells.add(new PdfCustomCell("出生日期"));
			
			Date date = projectKeJi.getChargerBirthday();
			String displayDate = "";
			if(date != null){
				String dateStr = date.toString();
				displayDate = dateStr.substring(0, dateStr.indexOf(" "));
			}
			
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, displayDate));
			cells.add(new PdfCustomCell("行政职务"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectKeJi.getChargerPost(),2));
			CategoryDAO categoryDAO = SpringContextUtil.getBean("categoryDAO");
			Category category = categoryDAO.getCategoryByCode("DM_TITLE_DEFINE_NAME", projectKeJi.getChargerTitle());
			cells.add(new PdfCustomCell("职  称"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, category==null?"":category.getValue(), 2));
			cells.add(new PdfCustomCell("学  历"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectKeJi.getEduLevel(),2));
			cells.add(new PdfCustomCell("学  位"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectKeJi.getEduDegree(),2));
			cells.add(new PdfCustomCell("研究专长"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT,"",5));
			cells.add(new PdfCustomCell("办公电话"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectKeJi.getChargerOfficeTel(),2));
			cells.add(new PdfCustomCell("住宅电话"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectKeJi.getChargerTel(),2));
			cells.add(new PdfCustomCell("手机号码"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectKeJi.getChargerPhoneNumber(),2));
			cells.add(new PdfCustomCell("电子邮件"));
			cells.add(new PdfCustomCell(Element.ALIGN_LEFT, projectKeJi.getChargerEmail(),2));
		}
		return cells;
	}
	
	private static PdfPTable getPdfTable(Font font,float[] tableWidths,List<PdfCustomCell> cells) throws DocumentException{
		PdfPTable table=new PdfPTable(tableWidths.length);
		table.setTotalWidth(tableWidths);
		for (PdfCustomCell cell : cells) {
			PdfPCell pp1=new PdfPCell(new Paragraph (cell.getText(),font));
			pp1.setColspan(cell.getColSpan());
			pp1.setRowspan(cell.getRoleSpan());
			pp1.setHorizontalAlignment(cell.getAlign());
			pp1.setPaddingTop(10);
			pp1.setPaddingBottom(10);
			table.addCell(pp1);
		}
		return table;
	}
	
	public static Paragraph getBottom(String text) throws DocumentException, IOException{
		BaseFont chinese = BaseFont.createFont("C:/WINDOWS/Fonts/STSONG.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Paragraph p1=new Paragraph(text,new Font(chinese,10,Font.NORMAL));
		p1.setIndentationLeft(55);
		p1.setIndentationRight(55);
		p1.setAlignment(Element.ALIGN_RIGHT);
		return p1;
	}
	
	public static Paragraph getParagraph(String text,Font font){
		Paragraph p1=new Paragraph(text,font);
		p1.setIndentationLeft(55);
		p1.setIndentationRight(55);
		return p1;
	}
	
	public static Paragraph getParagraph(String text,float spaceAfter,Font font,int align){
		Paragraph p1=new Paragraph(text, font);
		p1.setSpacingAfter(spaceAfter);
		p1.setIndentationLeft(55);
		p1.setIndentationRight(55);
		p1.setAlignment(align);
		return p1;
	}
	
	public static Paragraph getParagraph(String text,Font font,int align){
		Paragraph p1=new Paragraph(text,font);
		p1.setIndentationLeft(55);
		p1.setIndentationRight(55);
		p1.setAlignment(align);
		return p1;
	}
	
	public static Paragraph getParagraph(String text,Font font,int align,float firstLineIndent){
		Paragraph p1=new Paragraph(text,font);
		p1.setFirstLineIndent(firstLineIndent);
		p1.setIndentationLeft(55);
		p1.setIndentationRight(55);
		p1.setAlignment(align);
		return p1;
	}
	public static Paragraph getParagraph(String text,float spaceAfter, Font font,int align,float firstLineIndent){
		Paragraph p1=new Paragraph(text,font);
		p1.setFirstLineIndent(firstLineIndent);
		p1.setIndentationLeft(55);
		p1.setIndentationRight(55);
		p1.setAlignment(align);
		p1.setSpacingAfter(spaceAfter);
		return p1;
	}
}
