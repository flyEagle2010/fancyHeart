package com.doteplay.editor.file.exporter.impl;

import java.io.IOException;

import javax.swing.JOptionPane;

import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WriteException;

import com.doteplay.editor.common.DataGroup;
import com.doteplay.editor.common.DataManager;
import com.doteplay.editor.data.sprite.SpriteData;
import com.doteplay.editor.data.text.TextData;
import com.doteplay.editor.file.exporter.AbstractExcelExporter;

public class TextExcelExporter extends AbstractExcelExporter
{

	@Override
	public void doExport()
	{
		
		try
		{
			DataGroup<TextData> dg = DataManager.getDataGroup("text");
			WritableCellFormat wcf = getCellFormat();

			sheet.addCell(new Label(0, 0, "数据编号", wcf));
			sheet.addCell(new Label(1, 0, "中文", wcf));
			sheet.addCell(new Label(2, 0, "翻译", wcf));

			sheet.setColumnView(0, 20);
			sheet.setColumnView(1, 30);
			sheet.setColumnView(2, 30);

			for (int i = 0; i < dg.getDataCount(); i++)
			{
				TextData qd = dg.getDataByIndex(i);
				qd.open();
				String[] cells = qd.getExcelData();

				if (cells == null || cells.length <= 0)
				{
					return;
				}
				for (int j = 0; j < cells.length; j++)
				{
					sheet.addCell(new Label(j, i + 3, cells[j]));
				}
				qd.close();

			}
			JOptionPane.showMessageDialog(null, "导出成功!");
		} catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "导出失败!");
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
	
	

}
