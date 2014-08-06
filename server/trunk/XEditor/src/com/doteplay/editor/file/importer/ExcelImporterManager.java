/**
 * @package com.doteplay.editor.export
 * @file TextExport.java
 */
package com.doteplay.editor.file.importer;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.doteplay.editor.common.BaseData;
import com.doteplay.editor.common.DataGroup;
import com.doteplay.editor.common.DataManager;
import com.doteplay.editor.file.exporter.AbstractExcelExporter;

/**
 */
public class ExcelImporterManager
{

	private File file;
	private Workbook book = null;
	private Sheet sheet = null;

	public ExcelImporterManager(File file)
	{
		this.file = file;
	}

	public String[] getOption()
	{
		return DataManager.getGroupNames();
	}

	public void start(int selectIndex)
	{
		AbstractExcelImporter excelExporter = AbstractExcelImporter.createImporter(selectIndex);
		excelExporter.setSheet(selectIndex,file);
		excelExporter.doImport();
		
//		synchronized (this)
//		{
//			String[] dgNames = getOption();
//			setSheet(selectIndex);// 确定sheet
//			if (sheet == null)
//			{
//				JOptionPane.showMessageDialog(null, "没有发现\""
//						+ dgNames[selectIndex] + "\"表");
//				return;
//			}
//			if (dgNames[selectIndex].equals("任务"))
//			{
//				importQuest();
//
//			} else
//			{
//				System.out.println("目前不支持");
//			}
//		}
	}

	/**
	 * 确定sheet
	 */
	public void setSheet(int selectIndex)
	{
		String[] dgNames = getOption();
		try
		{
			book = Workbook.getWorkbook(file);
			Sheet[] sheets = book.getSheets();
			for (int i = 0; i < sheets.length; i++)
			{
				if (sheets[i].getName().equals(dgNames[selectIndex]))
				{
					sheet = sheets[i];
					break;
				}
			}

		} catch (BiffException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	// TODO:=======Excel=====================================================
	public void start(String sheetName)
	{
		synchronized (this)
		{
			setSheet(sheetName);// 确定sheet
			if (sheet == null)
			{
				JOptionPane.showMessageDialog(null, "没有发现\"" + sheetName
						+ "\"表");
				return;
			}

		}
	}

	/**
	 * 执行导入
	 */
	public void exeExport(String sheetName)
	{
		DataGroup dg = DataManager.getDataGroup(sheetName);
		Cell cell = null;
		BaseData bd = null;
		for (int i = 1; i < sheet.getRows(); i++)
		{
			cell = sheet.getCell(0, i);
			String id = cell.getContents();
			if (id == null || id.isEmpty())
			{
				continue;
			}
			bd = dg.getData(id.trim());
			System.err.println("----------->" + bd);
			bd.open();
			Cell[] cells = sheet.getRow(i);
			bd.setExcelData(cells, 0);
			bd.close();
		}
	}

	/**
	 * 根据组名确定sheet
	 */
	public void setSheet(String sheetName)
	{
		try
		{
			book = Workbook.getWorkbook(file);
			Sheet[] sheets = book.getSheets();
			for (int i = 0; i < sheets.length; i++)
			{
				if (sheets[i].getName().equals(sheetName))
				{
					sheet = sheets[i];
					break;
				}
			}
		} catch (BiffException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

}
