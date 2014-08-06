/**
 * @package com.doteplay.editor.export
 * @file TextExport.java
 */
package com.doteplay.editor.file.exporter;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.doteplay.editor.common.BaseData;
import com.doteplay.editor.common.DataGroup;
import com.doteplay.editor.common.DataManager;

/**
 */
public class ExcelExporterManager
{

	private File file;
	protected WritableWorkbook book = null;
	protected WritableSheet sheet = null;

	public ExcelExporterManager(File file)
	{
		this.file = file;
	}

	public synchronized void start(int selectIndex)
	{
		AbstractExcelExporter excelExporter = AbstractExcelExporter.createExporter(selectIndex);
		excelExporter.setSheet(selectIndex,file);
		excelExporter.doExport();
	}

	
	public WritableSheet start(String groupName)
	{
		synchronized (this)
		{
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
					if (sheets[i].getName().equals(groupName))
					{
						sheet = sheets[i];
						flag = true;
						break;
					}
				}
				if (!flag)
				{
					sheet = book.createSheet(groupName, sheets.length);
				}
			} catch (BiffException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return sheet;
	}

	/**
	 * 导出任务
	 */
	public void exporter(DataGroup dg)
	{
		try
		{
			WritableCellFormat wcf = getCellFormat();

			BaseData data1 = dg.getDataByIndex(0);
			data1.getWritableSheet(sheet, wcf);

			int i = 0;
			Object[] bds = dg.groupPanel.getSelectObjects();
			if (bds == null || bds.length < 1)
			{// 说明没有选择任何数据直接导出全部
				Vector<BaseData> vbd = dg.getDataList();
				for (BaseData data : vbd)
				{
					if (data.geneType != 1
							&& data.lastEditorDateTime.getTime() > dg.whatTime)
					{
						data.open();
						String[] cells = data.getExcelData();
						if (cells == null || cells.length <= 0)
						{
							return;
						}
						for (int j = 0; j < cells.length; j++)
						{
							sheet.addCell(new Label(j, i + 1, cells[j]));
						}
						data.close();
						i++;
					}
				}
			} else
			{// 导出选择项
				for (Object obj : bds)
				{
					data1 = (BaseData) obj;
					if (data1.geneType != 1
							&& data1.lastEditorDateTime.getTime() > dg.whatTime)
					{
						data1.open();
						String[] cells = data1.getExcelData();
						if (cells == null || cells.length <= 0)
						{
							return;
						}
						for (int j = 0; j < cells.length; j++)
						{
							sheet.addCell(new Label(j, i + 1, cells[j]));
						}
						data1.close();
						i++;
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, dg.groupName + "导出失败!");
		} finally
		{
			try
			{
				book.write();
				book.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			} catch (WriteException e)
			{
				e.printStackTrace();
			}
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
}
