package com.doteplay.editor.file.importer;

import java.io.File;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.doteplay.editor.common.DataManager;
import com.doteplay.editor.file.importer.impl.TextExcelImporter;

public abstract class AbstractExcelImporter
{
	protected Workbook book = null;
	protected Sheet sheet = null;
	
	
	public abstract void doImport();
	
	/**
	 * 确定sheet
	 */
	public void setSheet(int selectIndex,File file)
	{
		String[] dgNames = DataManager.getGroupNames();
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

	/**
	 * 返回Excel单元格中格式控制器
	 */
	protected WritableCellFormat getCellFormat() throws WriteException
	{
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 14,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
		WritableCellFormat wcf = new WritableCellFormat(wf); // 单元格定义
		wcf.setBackground(jxl.format.Colour.GRAY_25); // 设置单元格的背景颜色
		wcf.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
		return wcf;
	}
	
	public static AbstractExcelImporter createImporter(int idx)
	{
		switch (idx)
		{
			case 0:
				return new TextExcelImporter();
	
			default:
				return null;
		}
	}

}
