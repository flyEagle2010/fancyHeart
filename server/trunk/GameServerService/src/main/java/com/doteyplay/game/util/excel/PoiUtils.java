package com.doteyplay.game.util.excel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

public class PoiUtils
{

	private static final NumberFormat FMT_NUMBER = new DecimalFormat(
			"0.#########");

	public PoiUtils()
	{
	}

	public static int getIntValue(HSSFCell cell)
	{
		if (cell == null || cell.toString().trim().length() == 0)
			return 0;
		else
			return (int) Double.parseDouble(cell.toString());
	}

	public static short getShortValue(HSSFCell cell)
	{
		if (cell == null || cell.toString().length() == 0)
			return 0;
		else
			return (short) (int) Double.parseDouble(cell.toString());
	}

	public static byte getByteValue(HSSFCell cell)
	{
		if (cell == null || cell.toString().length() == 0)
			return 0;
		else
			return (byte) (int) Double.parseDouble(cell.toString());
	}

	public static double getDoubleValue(HSSFCell cell)
	{
		if (cell == null || cell.toString().length() == 0)
			return 0.0D;
		else
			return Double.parseDouble(cell.toString());
	}

	public static Date getDateValue(HSSFCell cell, String pattern)
	{
		if (cell != null && cell.toString().length() > 0)
			return cell.getDateCellValue();
		else
			return null;
	}

	public static Calendar getCalendarValue(HSSFCell cell)
	{
		if (cell != null && cell.toString().length() > 0)
		{
			double numDate = getDoubleValue(cell);
			Date date = HSSFDateUtil.getJavaDate(numDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal;
		} else
		{
			return null;
		}
	}

	public static String getStringValue(HSSFCell cell)
	{
		if (cell == null)
			return "";
		switch (cell.getCellType())
		{
		case 1: // '\001'
			return cell.toString();

		case 0: // '\0'
			String str = FMT_NUMBER.format(cell.getNumericCellValue());
			if (str.endsWith(".0"))
				return str.substring(0, str.length() - 2);
			else
				return str;
		}
		return cell.toString();
	}

	public static float getFloatValue(HSSFCell cell)
	{
		if (cell == null || cell.toString().length() == 0)
			return 0.0F;

		try
		{
			return Float.parseFloat(cell.toString());
		} catch (RuntimeException e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	public static String getIntString(HSSFCell cell)
	{
		return (new StringBuilder()).append("").append(getIntValue(cell))
				.toString();
	}

}
