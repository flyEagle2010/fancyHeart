package com.doteplay.editor.file.importer.impl;

import java.io.IOException;

import javax.swing.JOptionPane;

import jxl.Cell;
import jxl.write.WriteException;

import com.doteplay.editor.common.DataGroup;
import com.doteplay.editor.common.DataManager;
import com.doteplay.editor.common.GroupListPanel;
import com.doteplay.editor.data.text.TextData;
import com.doteplay.editor.file.importer.AbstractExcelImporter;

public class TextExcelImporter extends AbstractExcelImporter
{

	@Override
	public void doImport()
	{
		DataGroup dg = DataManager.getDataGroup("text");
		String errorStr = "";
		int size = sheet.getRows();
		System.out.println("importText size:" + size);

		for (int i = 3; i < sheet.getRows(); i++)
		{
			Cell cell0 = sheet.getCell(0, i);
			String id = "";

			if (cell0.getContents() == null || "".equals(cell0.getContents()))
			{
				id = DataManager.getNewDataId("text");
			} else
			{
				id = cell0.getContents();
			}

			try
			{
				TextData qd = (TextData) dg.getData(id);
				if (qd == null)
				{
					qd = (TextData) dg.newData();
				}
				String[] cells = new String[3];
				for (int j = 0; j < 3; j++)
				{
					Cell cell = sheet.getCell(j, i);
					cells[j] = cell.getContents();

				}
				// qd.open();
				qd.releaseData();
				qd.setExcelData(cells);
				qd.save();
//				qd.releaseData();
				// qd.close();

				GroupListPanel gp = (GroupListPanel) dg.groupPanel;
				gp.updateView();
				dg.saveIndexFile();
			} catch (Exception e)
			{
				e.printStackTrace();
				errorStr += id + ",";
				System.out.println(id + "错误");
			}

		}
			book.close();
		if (errorStr.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "任务导入成功!");
		} else
		{
			errorStr = errorStr.substring(0, errorStr.length() - 1);
			JOptionPane.showMessageDialog(null, errorStr + "任务导入失败!");
		}
	}

}
