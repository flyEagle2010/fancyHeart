package com.doteplay.editor.tools.excel;

import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ExcelDataImporter {

	private Workbook book = null;

	private ISheetDataFilter sheetDataFilter = null;
	
	public ExcelDataImporter(File file, ISheetDataFilter sheetDataFilter) {
		initWorkbook(file);
		this.sheetDataFilter = sheetDataFilter;
	}
	
	public ExcelDataImporter(File file) {
		initWorkbook(file);
	}
	
	private boolean initWorkbook(File file) {
		try {
			book = Workbook.getWorkbook(file);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
		return true;
	}

	public Sheet getSheet(int index) {
		Sheet sheet = book.getSheet(index);
		return sheet;
	}

	public String[][] readSheet(Sheet sheet, int maxCols, int maxRows) {
		if (sheet == null) {
			return null;
		}

		System.out.println("==============" + sheet.getName() + "================");
		String[][] s = new String[maxRows][maxCols];
		for (int row = 0; row < maxRows; row++) {
			for (int col = 0; col < maxCols; col++) {
				Cell c = sheet.getCell(col, row);
				System.out.print(c.getContents() + "\t");
				s[row][col] = c.getContents();
			}
			System.out.println();
		}
		System.out.println("==============================");
		return s;
	}

	public String[][] readSheet(Sheet sheet) {
		if (sheet == null) {
			return null;
		}

		System.out.println("==============" + sheet.getName() + "================");
		int maxCols = sheet.getColumns();
		int maxRows = sheet.getRows();

		String[][] s = new String[maxRows][maxCols];
		for (int row = 0; row < maxRows; row++) {
			for (int col = 0; col < maxCols; col++) {
				Cell c = sheet.getCell(col, row);
				System.out.print(c.getContents() + "\t");
				s[row][col] = c.getContents();
			}
			System.out.println();
		}
		
		if (sheetDataFilter != null) {
			s = sheetDataFilter.filter(s);
		}
		
		System.out.println("==============================");
		return s;
	}

	public String[][] readSheet(int sheetIndex) {
		Sheet sheet = book.getSheet(sheetIndex);
		return readSheet(sheet);
	}

	public String[][] readSheet(String sheetName) {
		Sheet sheet = book.getSheet(sheetName);
		return readSheet(sheet);
	}

	public String[][] readSheet(String sheetName, int maxCols, int maxRows) {
		Sheet sheet = book.getSheet(sheetName);
		return readSheet(sheet, maxCols, maxRows);
	}
}
