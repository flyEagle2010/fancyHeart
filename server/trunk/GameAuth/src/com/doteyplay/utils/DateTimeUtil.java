package com.doteyplay.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil
{
	/**
	 * 
	 */
	private DateTimeUtil()
	{
	}

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
	 * ���ع̶���ʽ����ȷ������
	 */
	private final static String ONLY_MINUTES_FORMAT= "yyyy-MM-dd HH:mm";
	
	
	/**
	 * ��ȡָ��ʱ���yyyy-MM-dd HH:mm:ss��ʾ��ʽ
	 * @param timestamp
	 * @return
	 */
	public static String getTimeString(long timestamp)
	{
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTimeInMillis(timestamp);
		return new SimpleDateFormat(FRIENDLY_DATE_TIME_FORMAT).format(cal.getTimeInMillis());
	}
	/**
	 * ����ʱ���ʽΪyyyy-MM-dd HH:mm
	 * @param timestamp
	 * @return
	 */
	public static String getTimeMinutes(long timestamp){
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTimeInMillis(timestamp);
		return new SimpleDateFormat(ONLY_MINUTES_FORMAT).format(cal.getTimeInMillis());
	}
	/**
	 * ��ȡ��ǰ��ʱ��
	 * @return
	 */
	public static String nowDate(){
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}
	/**
	 * ͨ��ָ�������ȡ���
	 * @param cal
	 * @return
	 */
	public static int getYear(Calendar cal)
	{
		return cal.get(Calendar.YEAR);
	}
	
	/**
	 * ͨ��ָ�������ȡ�·�(1-12)
	 * @param cal
	 * @return 1�µ�12�·ֱ𷵻�1-12
	 */
	public static int getMonth(Calendar cal)
	{
		return cal.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * ͨ��ָ�������ȡ��
	 * @param cal
	 * @return
	 */
	public static int getDayOfMonth(Calendar cal)
	{
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * ͨ��ָ�������ȡ����
	 * @param cal
	 * @return ����һ��������ֱ��Ӧ1-7
	 */
	public static int getDayOfWeek(Calendar cal)
	{
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}
	
	/**
	 * ��ȡСʱ(24Сʱ��)
	 * @param cal
	 * @return
	 */
	public static int getHourOfDay(Calendar cal)
	{
		return cal.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * ��ȡ����
	 * @param cal
	 * @return
	 */
	public static int getMinute(Calendar cal)
	{
		return cal.get(Calendar.MINUTE);
	}
	
	/**
	 * ��ȡ��
	 * @param cal
	 * @return
	 */
	public static int getSecond(Calendar cal)
	{
		return cal.get(Calendar.SECOND);
	}
	/**
	 * ���ص�ǰ��Сʱ��(24Сʱ 0-23)
	 * @return
	 */
	public static int getCurrentHour(){
		return getHourOfDay(Calendar.getInstance());
	}
	
	/**
	 * ����date1�����ڲ��֣�����Date2��ʱ�䲿��
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Date changeTime(Date date1,Date date2){
		return new Date(date1.getTime()/DAY*DAY+date2.getTime()%DAY);
	}
	/**
	 * �Ƚ��������ڵ�ʱ�䲿���Ƿ�һ��
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareTime(Date date1,Date date2){
		return date1.getTime()%DAY==date2.getTime()%DAY;
	}
	
	/**
	 * �Ƚ�����Date���ڲ��ֵĴ�С
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDate(Date date1,Date date2){
		if(date1.getTime()/DAY==date2.getTime()/DAY){
			return 0;
		}
		else if(date1.getTime()/DAY>date2.getTime()/DAY){
			return 1;
		}
		else{
			return -1;
		}
	}
	/**
	 * ����������ڵ�ʱ�䣬�����һ�����е�ʱ��ĵĺ�����
	 * @author pb.li 
	 * @param date
	 * @return
	 */
	public static long dayRunTime(Date date){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		Calendar runTime=Calendar.getInstance();
		runTime.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
		runTime.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
		runTime.set(Calendar.SECOND, cal.get(Calendar.SECOND));
		return runTime.getTimeInMillis();
	}
	

	/**
	 * ���������Ϊ���ڽ���ѭ����������һ��������ʱ��
	 * @author pb.li 
	 * @param date
	 * @param dayOfWeek ��1��ʼ��7
	 * @return
	 */
	public static long weekRunTime(Date date,byte dayOfWeek){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		Calendar runTime=Calendar.getInstance();
		if((dayOfWeek+8)%7>=runTime.get(Calendar.DAY_OF_WEEK)){			
			runTime.add(Calendar.DATE,(dayOfWeek+8)%7-runTime.get(Calendar.DAY_OF_WEEK));
		}
		else{
			runTime.add(Calendar.DATE,(dayOfWeek+8)%7-runTime.get(Calendar.DAY_OF_WEEK)+7);
		}
		runTime.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
		runTime.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
		runTime.set(Calendar.SECOND, cal.get(Calendar.SECOND));
		return runTime.getTimeInMillis();
	}
	/**
	 * �������Ϊ����������������һ������ʱ��
	 * @author pb.li 
	 * @param date
	 * @param dayOfMonth
	 * @return
	 */
	public static long monthRunTime(Date date,byte dayOfMonth){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		Calendar runTime=Calendar.getInstance();
		if(dayOfMonth>=runTime.get(Calendar.DAY_OF_MONTH)){
			runTime.add(Calendar.DATE, dayOfMonth-runTime.get(Calendar.DAY_OF_MONTH));
		}
		else{
			runTime.add(Calendar.MONTH,1);
			runTime.set(Calendar.DATE, dayOfMonth);
		}
		runTime.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
		runTime.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
		runTime.set(Calendar.SECOND, cal.get(Calendar.SECOND));
		return runTime.getTimeInMillis();
	}
	
	/**
	 * ��ָ����ʱ�����ָ��������
	 * @author pb.li 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date,int day){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	public static Date addDay(int day){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}
	/**
	 * ��ָ����ʱ�����ָ����Сʱ
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date addHour(Date date,int hour){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, hour);
		return cal.getTime();
	}

	/**
	 * �ڸ��ʱ�������ָ��������
	 * @param date ָ����ʱ��
	 * @param second ����ӵ�����
	 * @return �µ�ʱ��
	 */
	public static Date addSecond(Date date, int second) {
	    Calendar cal=Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.SECOND, second);
	    return cal.getTime();
	}

	public static Date addMinute(int minute){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.MINUTE, minute);
		return cal.getTime();
	}
	public static Timestamp string2Timestamp(String time) {
		Timestamp date = null; 
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = sdf.parse(time);
			date = new Timestamp(d.getTime());
		} catch (Exception e) {
		}
		return date;
	}
	public static Date string2Date(String time) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = sdf.parse(time);
		} catch (Exception e) {
			date=new Date();
		}
		return date;
	}
	
	public static Date string2Date2(String time) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.parse(time);
		} catch (Exception e) {
			date=new Date();
		}
		return date;
	}
	
	public static String time2String(Date time) {
		String timeString = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			timeString = sdf.format(time);
		} catch (Exception e) {
//			logger.error("ʱ��ת�ַ����", e);
		}
		return timeString;
	}
	
}
