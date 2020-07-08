package com.hejie.tmall_ssm.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 
 * <p>Title:PoiUtil </p>
 * <p>Description: excel相关工具类</p>
 * @author 何杰
 * @date 2019年9月20日
 * @version 1.0
 * @since JDK 1.8
 */
@SuppressWarnings("all")
public class PoiUtil<T> {
	
	private static Logger logger = LoggerFactory.getLogger(PoiUtil.class);
	private Workbook workbook;
	private String[] properties;
	private Class<T> class1;
	private String filePath;
	private int startRow;
	private int startColumn;
	private boolean isHash = false;  //是否hash校验
	private HashSet<Integer> hashSet;	
	private CopyOnWriteArrayList beanList;
	
	/*
	 * 构造方法a
	 */
	public PoiUtil(String[] properties, Class<T> class1, String filePath, int startRow, int startColumn) {
		this.properties = properties;
		this.class1 = class1;
		this.filePath = filePath;
		this.startRow = startRow;
		this.startColumn = startColumn;
		this.beanList = new CopyOnWriteArrayList();
		setWorkbook(filePath);
		logger.debug("初始化PoiUtil成功a");
	}
	
	/*
	 * 构造方法b
	 */
	public PoiUtil(String[] properties, Class<T> class1, String filePath, int startRow, int startColumn, HashSet<Integer> hashSet) {
		this.properties = properties;
		this.class1 = class1;
		this.filePath = filePath;
		this.startRow = startRow;
		this.startColumn = startColumn;
		this.isHash = true;
		this.hashSet = hashSet;
		this.beanList = new CopyOnWriteArrayList();
		setWorkbook(filePath);
		logger.debug("初始化PoiUtil成功b");
	}
	
	/*
	 * 获取数据
	 */
	public void getBeanList(int startSheet, int endSheet) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		HashMap<String, String> valueMap = new HashMap<>();
			
		for (int sheetNum = startSheet; sheetNum < endSheet; sheetNum++) {
			Sheet sheet = workbook.getSheetAt(sheetNum);
			
			for(int rowNum = startRow; rowNum < sheet.getPhysicalNumberOfRows(); rowNum++) {
				Row row = sheet.getRow(rowNum);
				
				for (int i = 0, columnNum = startColumn; i < properties.length; i++, columnNum++) {
					Cell cell = row.getCell(columnNum);
					valueMap.put(properties[i], getCellValue(cell, cell.getCellType()));
				}
				
				T t = class1.newInstance();
				BeanUtils.populate(t, valueMap);
				
				//hash校验
				if (isHash) {
					
					synchronized (hashSet) {
						
						if (hashSet.contains(t.hashCode())) {
							continue;
						}
						
						hashSet.add(t.hashCode());
					}
				}
				
				beanList.add(t);
			}
		}
		logger.debug("加载Excel数据完成");
	}
	
	/*
	 * 初始化workbook
	 */
	private void setWorkbook(String path) {
		Workbook workbook = null;
		
		path = "".equals(filePath) ? filePath : path;
		
		if (path != null) {
			try (FileInputStream fis = new FileInputStream(path);) {
				
				if (path.endsWith(".xls")) {
					workbook = new HSSFWorkbook(fis);
				} else if (path.endsWith(".xlsx")) {
					workbook = new XSSFWorkbook(fis);
					logger.debug("获取Workbook成功");
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("加载Workbook失败，错误信息 < " + e.getMessage() + " > ");
			}
		}
		
		this.workbook = workbook;
	}
	
	/*
	 * 获取单元格值
	 */
	private String getCellValue(Cell rowCell, int rowCellType) {
		String cellValue = "";
		
		switch (rowCellType) {
			case Cell.CELL_TYPE_STRING :
				cellValue = rowCell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC :
				String dataFormat = rowCell.getCellStyle().getDataFormatString();
				AtomicReference<Boolean> isDate = new AtomicReference<>(false);
				
				if (DateUtil.isCellDateFormatted(rowCell)) {
					cellValue = new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getJavaDate(rowCell.getNumericCellValue()));
				} else if (DateUtil.isCellInternalDateFormatted(rowCell)) {
					cellValue = new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getJavaDate(rowCell.getNumericCellValue()));
				} else if (isDate.get()) {
					cellValue = new SimpleDateFormat("yyyy-MM-dd").format(rowCell.getDateCellValue());
				} else if (dataFormat == null) {
					cellValue = new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getJavaDate(rowCell.getNumericCellValue()));
				} else {
					
				    if (dataFormat != null) {
				    	cellValue = String.valueOf(rowCell.getNumericCellValue());
				    	cellValue = cellValue.split("\\.")[0];
				    } else {
				    	
				        if (rowCell.getCellStyle().getDataFormatString().contains("$")) {
				        	cellValue = "$" + rowCell.getNumericCellValue();
				        } else if (rowCell.getCellStyle().getDataFormatString().contains("￥")) {
				        	cellValue = "￥" + rowCell.getNumericCellValue();
				        } else if (rowCell.getCellStyle().getDataFormatString().contains("¥")) {
				        	cellValue = "¥" + rowCell.getNumericCellValue();
				        } else if (rowCell.getCellStyle().getDataFormatString().contains("€")) {
				        	cellValue = "€" + String.valueOf(rowCell.getNumericCellValue());
				        } else {
//				        	cellValue = String.valueOf(rowCell.getNumericCellValue());
				        	cellValue = String.valueOf(rowCell.getNumericCellValue()).split("\\.")[0];
				        }
				    }
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN :
				cellValue = String.valueOf(rowCell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_ERROR :
				cellValue = ErrorEval.getText(rowCell.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA :
				cellValue = rowCell.getCellFormula();
				break;
		}
		
		logger.debug("获取单元格内容成功");
		return cellValue;
	}
	
	/*
	 * 加载Excel数据
	 */
	public CopyOnWriteArrayList getBeanList() {
		return beanList;
	}
	
	/*
	 * 获取workbook
	 */
	public Workbook getWorkbook() {
		return workbook;
	}

}
