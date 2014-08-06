package com.doteyplay.utils;

import java.util.Date;

public class PhpUtils
{
	public static String phpDate2Str(Date souDate)
	{
		return String.valueOf(phpDate(souDate));
	}

	public static long phpDate(Date souDate)
	{
		if (souDate != null)
			return souDate.getTime() / 1000;
		else
			return 0;
	}

	public static long phpTime()
	{
		return phpDate(new Date());
	}

	public static String phpTime2Str()
	{
		return phpDate2Str(new Date());
	}
}
