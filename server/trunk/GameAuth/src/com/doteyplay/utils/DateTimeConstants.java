package com.doteyplay.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateTimeConstants
{
	/**
	 * һ���ӵĺ����ʾ
	 */
	public final static long SECOND = 1000L;
	/**
	 * һ���ӵĺ����ʾ
	 */
	public final static long MINUTE = 60L * SECOND;
	/**
	 * һСʱ�ĺ����ʾ
	 */
	public final static long HOUR = 60L * MINUTE;
	/**
	 * һ��ĺ����ʾ
	 */
	public final static long DAY = 24L * HOUR;
	/**
	 * һ�ܵĺ����ʾ
	 */
	public final static long WEEK = 7L * DAY;

	/**
	 * һ���µĺ����ʾ(��30�����)
	 */
	public final static long MONTH = 30L * DAY;
	/**
	 * һ��ĺ����ʾ(��365�����)
	 */
	public final static long YEAR = 365L * DAY;

	/**
	 * �û��Ѻõ�����ʱ���ʽ:yyyy-MM-dd HH:mm:ss
	 */
	public final static String FRIENDLY_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * ��ȡָ��ʱ���yyyy-MM-dd HH:mm:ss��ʾ��ʽ
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String getTimeString(long timestamp)
	{
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTimeInMillis(timestamp);
		return new SimpleDateFormat(FRIENDLY_DATE_TIME_FORMAT).format(cal.getTimeInMillis());
	}
}
