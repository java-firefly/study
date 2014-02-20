package com.eplugger.util.exportUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

public abstract class ExportTempletExcel {
	
	private static String masterRegEx = "master:\\[(.+?)\\]";
	private static String manyRegEx = "many:\\[(.+?)\\]";
//	TODO 暂时不支持查询条件部分替换，需要支持时打开 1
//	private static String filterRegEx = "filter:\\[(.+?)\\]";
	
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static HSSFWorkbook exportXlsByTemplet(String templetName,List<Map<String,Object>> exportData){
		List<HSSFCell> masterCellMetas = new ArrayList<HSSFCell>();
		List<CellMeta> manyCellMetas = new ArrayList<CellMeta>();
//		List<HSSFCell> filterCellMetas = new ArrayList<HSSFCell>();
		HSSFWorkbook templetWorkbook=null;
		try {
			templetWorkbook = new HSSFWorkbook(ResourceUtils.getURL("classpath:/resource/expTemplet/"+templetName+".xls").openStream());
			// TODO 只扫描第1页
			HSSFSheet sheet = templetWorkbook.getSheetAt(0);
			CellMeta cellMeta=null;
			for (Iterator iter = sheet.rowIterator(); iter.hasNext();) {
				HSSFRow row = (HSSFRow) iter.next();
				for (Iterator iterator = row.cellIterator(); iterator.hasNext();) {
					HSSFCell cell = (HSSFCell) iterator.next();
					String value = cell.getRichStringCellValue().getString();
					if(StringUtils.isBlank(value))continue;
					try {
						if (Pattern.compile(masterRegEx).matcher(value).find()) {
							masterCellMetas.add(cell);
						} else if (Pattern.compile(manyRegEx).matcher(value).find()) {
							cellMeta=new CellMeta(row.getRowNum(), cell.getCellNum());
							cellMeta.setCellStyle(cell.getCellStyle());
							manyCellMetas.add(cellMeta);
						}
//					TODO 暂时不支持查询条件部分替换，需要支持时打开 2
//					else if (Pattern.compile(filterRegEx).matcher(value).find()) {
//						filterCellMetas.add(cell);
//					}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if(masterCellMetas.size()>0){
				Map<String, Object> map = (exportData==null || exportData.isEmpty()?new HashMap<String,Object>():exportData.get(0));
				updateMasterData(masterCellMetas, map);
				if(map.size()>0){
					exportData.remove(0);
				}
			}
			if(manyCellMetas.size()>0){
				updateManyData(sheet, manyCellMetas, exportData);
			}
//		TODO 暂时不支持查询条件部分替换，需要支持时打开 3
//		if(filterCellMetas.size()>0){
//			updateFilterData(filterCellMetas,conditions);
//		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return templetWorkbook;
	}
	/**
	 * 替换模版中1行有关的数据
	 */
	private static void updateMasterData(List<HSSFCell> masterCellMetas, Map<String,Object> resultSet) {
		if (masterCellMetas.isEmpty())return;
		for (int i = 0; i < masterCellMetas.size(); i++) {
			HSSFCell cell = masterCellMetas.get(i);
			HSSFRichTextString strVal = cell.getRichStringCellValue();
			cell.setCellValue(new HSSFRichTextString(replaceVal(masterRegEx,strVal.getString(),resultSet)));
		}
	}
	/**
	 * 替换模版中1行有关的数据-查询条件部分
	 */
//	TODO 暂时不支持查询条件部分替换，需要支持时打开 4
	/*
	 * private static void updateFilterData(List<HSSFCell> masterCellMetas, Map<String,String> conditions) {
		if (masterCellMetas.isEmpty())return;
		for (int i = 0; i < masterCellMetas.size(); i++) {
			HSSFCell cell = masterCellMetas.get(i);
			HSSFRichTextString strVal = cell.getRichStringCellValue();
			cell.setCellValue(new HSSFRichTextString(replaceVal(filterRegEx,strVal.getString(),conditions)));
		}
	}*/
	/**
	 * 替换模版中N行有关的数据
	 */
	@SuppressWarnings("deprecation")
	private static void updateManyData(HSSFSheet sheet, List<CellMeta> childCellMetas, List<Map<String,Object>> resultSets) {
		if (childCellMetas.isEmpty())
			return;
		//单元格默认值
		CellMeta cellMeta = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		String[] cellRegValue=new String[childCellMetas.size()];
		try {
			int rowIndex = 0;
			int maxRowIndex = 0;
			for (int j = 0; j < resultSets.size(); j++) {
				Map<String,Object> resultSet = resultSets.get(j);
				for (int colIndex = 0; colIndex < childCellMetas.size(); colIndex++) {
					cellMeta = childCellMetas.get(colIndex);
					if(cellRegValue[colIndex]==null){
						cellRegValue[colIndex] = sheet.getRow(cellMeta.getRowNum()).getCell(cellMeta.getCellNum()).getRichStringCellValue().getString();
					}
					//得到行
					row = sheet.getRow(cellMeta.getRowNum() + rowIndex);
					if (row == null) {
						row = sheet.createRow(cellMeta.getRowNum() + rowIndex);
					}
					//得到单元格
					cell = row.getCell(cellMeta.getCellNum());
					if (cell == null) {
						cell = row.createCell(cellMeta.getCellNum());
					}
					//设置样式
					cell.setCellStyle(cellMeta.getCellStyle());
					//设置值
					cell.setCellValue(new HSSFRichTextString(replaceVal(manyRegEx,cellRegValue[colIndex],resultSet)));
					if(maxRowIndex<(cellMeta.getRowNum() + rowIndex)){
						maxRowIndex = (cellMeta.getRowNum() + rowIndex);
					}
				}
				rowIndex++;
			}
			//合并同值列单元格
			colMergedCells(sheet,childCellMetas,maxRowIndex);
			if(rowIndex==0){//如果子表无值 则清空模版表示
				for (int k = 0; k < childCellMetas.size(); k++) {
					cellMeta = childCellMetas.get(k);
					sheet.getRow(cellMeta.getRowNum()).getCell(cellMeta.getCellNum()).setCellValue(new HSSFRichTextString(""));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 纵向合并
	 * @param sheet
	 * @param childCellMetas
	 */
	@SuppressWarnings("deprecation")
	private static void colMergedCells(HSSFSheet sheet, List<CellMeta> childCellMetas,int maxRow){
		CellMeta cellMeta = null;
		//LOGIC 合并规则 - 合并范围限制参数，后面一列的合并内容要在前面一列的合并范围内，即逐节细化
		List<List<Integer>> rangeLs=new ArrayList<List<Integer>>();
		for (int metaIndex = 0; metaIndex < childCellMetas.size(); metaIndex++) {
			cellMeta = childCellMetas.get(metaIndex);
			HSSFRow row = sheet.getRow(cellMeta.getRowNum());
			List<Integer> currentRangeLs = new ArrayList<Integer>();
			rangeLs.add(metaIndex,currentRangeLs);
			int colIndex = cellMeta.getCellNum();
			List<Integer> beforeRangeLs = null;
			if(metaIndex>0){
				beforeRangeLs = rangeLs.get(metaIndex-1);
			}
			for(;row!=null && maxRow >= cellMeta.getRowNum();){
				int sameFirstRowIndex = row.getRowNum();
				int sameLastRowIndex = sameFirstRowIndex+1;
				
				int maxRangeIndex = -1;
				if(beforeRangeLs!=null){
					for (int i = 0; i < beforeRangeLs.size(); i++) {
						if(beforeRangeLs.get(i) > sameFirstRowIndex){
							maxRangeIndex = beforeRangeLs.get(i);
							break;
						}
					}
				}
				HSSFRichTextString strVal = row.getCell(cellMeta.getCellNum()).getRichStringCellValue();
				boolean exitFlag=false;
				for(HSSFRow nextRow = sheet.getRow(sameLastRowIndex); nextRow!=null && maxRow >= sameLastRowIndex ; nextRow = sheet.getRow(sameLastRowIndex)){
					HSSFCell nextCell = nextRow.getCell(cellMeta.getCellNum());
					if(nextCell==null){
						exitFlag=true;
						break;
					}
					HSSFRichTextString nextCellVal = nextCell.getRichStringCellValue();
					if(strVal.equals(nextCellVal) && (maxRangeIndex == -1 || maxRangeIndex > sameLastRowIndex)){
						sameLastRowIndex++;
					}else{
						break;
					}
				}
				sameLastRowIndex--;
				if(exitFlag)break;
				if(sameLastRowIndex > sameFirstRowIndex){
					sheet.addMergedRegion(new CellRangeAddress(sameFirstRowIndex,sameLastRowIndex,colIndex,colIndex));
				}
				currentRangeLs.add(sameFirstRowIndex);
				sameFirstRowIndex=sameLastRowIndex+1;
				row = sheet.getRow(sameFirstRowIndex);
			}
		}
	}
	
	/** 替换单元格中的正则内容 */
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
	private static String replaceVal(String regEx,String value,Map<String,?> resultSet){
		if(resultSet==null)return StringUtils.EMPTY;
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(value);
		StringBuffer sb = new StringBuffer();
		Object val;
		while(m.find()){
			try {
				val = resultSet.get(StringUtils.trim(m.group(1)));
				String replaceVal=null;
				if(val instanceof String){
					replaceVal=String.class.cast(val);
				}else if(val instanceof Date){
					replaceVal=sdf.format(Date.class.cast(val));
				}else if(val!=null){
					replaceVal=val.toString();
				}				
				m.appendReplacement(sb, StringUtils.defaultIfEmpty(replaceVal,""));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		m.appendTail(sb);
		return sb.toString();
	}
	/**
	 * 单元格源数据对象
	 */
	static class CellMeta {
		/** 行号 */
		private int rowNum;

		/** 单元格对象 */
		private short cellNum;

		/** 单元格样式 */
		private HSSFCellStyle cellStyle;
		public CellMeta(int rowNum, short cellNum) {
			this.rowNum = rowNum;
			this.cellNum = cellNum;
		}
		
		public void setCellStyle(HSSFCellStyle cellStyle){
			this.cellStyle=cellStyle;
//			this.cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		}

		public HSSFCellStyle getCellStyle(){
			return this.cellStyle;
		}

		public int getRowNum() {
			return rowNum;
		}

		public short getCellNum() {
			return cellNum;
		}
	}
}
