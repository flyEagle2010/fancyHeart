package com.doteplay.editor.file.exporter;

import java.io.File;
import java.io.IOException;

import com.doteplay.editor.common.DataManager;
import com.doteplay.editor.file.exporter.impl.SpriteExcelExporter;
import com.doteplay.editor.file.exporter.impl.TextExcelExporter;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public abstract class AbstractExcelExporter
{
	protected WritableWorkbook book = null;
	protected WritableSheet sheet = null;
	
	
	public abstract void doExport();
	
	/**
	 * 为sheet赋值
	 */
	public void setSheet(int selectIndex,File file)
	{
		String[] dgNames = DataManager.getGroupNames();
		try
		{
			if (file.exists())
			{// 说明是新建的文件
				Workbook workBook = Workbook.getWorkbook(file);// 获得第一个工作表对象
				book = Workbook.createWorkbook(file, workBook); // 通过上面的工作表获得该副本
			} else
			{
				book = Workbook.createWorkbook(file);
			}

			WritableSheet[] sheets = book.getSheets();
			boolean flag = false;
			for (int i = 0; i < sheets.length; i++)
			{
				if (sheets[i].getName().equals(dgNames[selectIndex]))
				{
					sheet = sheets[i];
					flag = true;
					break;
				}
			}
			if (!flag)
			{
				sheet = book.createSheet(dgNames[selectIndex], sheets.length);
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
	
	public static AbstractExcelExporter createExporter(int idx)
	{
		switch (idx)
		{
			case 0:
				return new TextExcelExporter();
			case 1:
				return new SpriteExcelExporter();
	
			default:
				return null;
		}
	}

}
