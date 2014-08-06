package com.doteyplay.game.util.excel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;

public class TemplateFileParser
{
	Logger logger;
	private TemplateObjectAssembler objectAssembler;

	public TemplateFileParser()
	{
		objectAssembler = new TemplateObjectAssembler();
	}

	public void parseXlsFile(Class classes[], Map templateObjects,
			InputStream is) throws Exception
	{
		int i = 0;
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(is));
		for (; i < classes.length; i++)
		{
			HSSFSheet sheet = wb.getSheetAt(i);
			Class curClazz = classes[i];
			if (curClazz == null)
				continue;
			Map curSheetObjects = parseXlsSheet(sheet, curClazz);
			Map existCurClazzMap = (Map) templateObjects.get(curClazz);
			if (existCurClazzMap != null)
				existCurClazzMap.putAll(curSheetObjects);
			else
				templateObjects.put(curClazz, curSheetObjects);
		}

	}

	protected Map parseXlsSheet(HSSFSheet sheet, Class clazz)
			throws InstantiationException, IllegalAccessException
	{
		Map map = new LinkedHashMap();
		int i = 5;
		do
		{
			if (i > 32767)
				break;
			TemplateObject obj = (TemplateObject) clazz.newInstance();
			HSSFRow row = sheet.getRow(i);
			if (isEmpty(row))
				break;
			parseXlsRow(obj, row);
			map.put(Integer.valueOf(obj.getId()), obj);
			i++;
		} while (true);
		return map;
	}

	protected boolean isEmpty(HSSFRow row)
	{
		if (row == null)
			return true;
		HSSFCell cell0 = row.getCell(0);
		String value = PoiUtils.getStringValue(cell0);
		return value == null || value.isEmpty();
	}

	public void parseXlsRow(TemplateObject obj, HSSFRow row)
	{
		Class clazz = obj.getClass();
		if (!clazz.isAnnotationPresent(ExcelRowBinding.class))
			throw new ConfigException((new StringBuilder()).append(clazz)
					.append(" is not a excel row binding object").toString());
		try
		{
			objectAssembler.doAssemble(obj, row, clazz);
		} catch (Exception e1)
		{
			HSSFSheet sheet = row.getSheet();
			int rowNum = row.getRowNum();
			String errMsg = String.format("sheet = %s, row = %d", new Object[] {
					sheet.getSheetName(), Integer.valueOf(rowNum) });
			throw new ConfigException(errMsg, e1);
		}
		try
		{
			Method methods[] = null;
			if (TemplateObjectAssembler.classMethods.containsKey(clazz))
			{
				methods = (Method[]) TemplateObjectAssembler.classMethods
						.get(clazz);
			} else
			{
				methods = clazz.getDeclaredMethods();
				Method.setAccessible(methods, true);
				TemplateObjectAssembler.classMethods.put(clazz, methods);
			}
			for (int i = 0; i < methods.length; i++)
				if ((methods[i].getModifiers() & 8) == 0
						&& methods[i]
								.isAnnotationPresent(FixUpByCellRange.class))
				{
					FixUpByCellRange fixupByCellRange = (FixUpByCellRange) methods[i]
							.getAnnotation(FixUpByCellRange.class);
					int startOff = fixupByCellRange.start();
					int len = fixupByCellRange.len();
					String params[] = new String[len];
					for (int k = 0; k < params.length; k++)
						params[k] = PoiUtils.getStringValue(row
								.getCell(startOff + k));

					methods[i].invoke(obj, new Object[] { params });
				}

		} catch (Exception e)
		{
			throw new ConfigException("Unknown exception", e);
		}
		
		obj.onLoadFinished();
	}

	public void parseXlsFile(String xlsPath, int index, Class clazz,
			Map templateObjects)
	{
		InputStream is = null;
		try
		{
			is = new FileInputStream(xlsPath);
			HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(is));
			HSSFSheet sheet = wb.getSheetAt(index);
			Map curSheetObjects = parseXlsSheet(sheet, clazz);
			Map existCurClazzMap = (Map) templateObjects.get(clazz);
			if (existCurClazzMap != null)
				existCurClazzMap.putAll(curSheetObjects);
			else
				templateObjects.put(clazz, curSheetObjects);
		} catch (Exception e)
		{
			throw new ConfigException(
					(new StringBuilder())
							.append("Errors occurs while parsing xls file:")
							.append(xlsPath).append(",sheet:").append(index)
							.toString(), e);
		}
	}

	public Class[] getLimitClazzes()
	{
		return null;
	}
}
