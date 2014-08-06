package com.doteyplay.game.util.excel;

import java.io.File;

import jxl.Sheet;
import jxl.Workbook;

public class DataTemplateCoder
{
	private final static String DIR_PATH = "C:\\Users\\AllenGao\\Desktop\\project2\\data\\";
	private final static String FILE_PATH = "合成表_20140624_陈天华.xls";

	public static void toCode(String path)
	{
		Workbook workbook = null;
		try
		{
			workbook = Workbook.getWorkbook(new File(path));
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		Sheet sheet = workbook.getSheet(0);
		int columnCount = sheet.getColumns();
		for (int j = 1; j < columnCount; j++)
		{
			if ("".equals(sheet.getCell(j, 3).getContents())
					|| "".equals(sheet.getCell(j, 4).getContents()))
				continue;
			System.out.println("@ExcelCellBinding");
			System.out.println("protected " + sheet.getCell(j, 3).getContents()
					+ " " + sheet.getCell(j, 4).getContents() + ";");
			System.out.print("\n");
		}
		workbook.close();
	}

	public static void main(String[] args)
	{
		toCode(DIR_PATH + FILE_PATH);
	}

}
