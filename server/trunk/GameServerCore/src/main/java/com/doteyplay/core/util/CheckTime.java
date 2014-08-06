package com.doteyplay.core.util;

import java.util.Calendar;

import com.doteyplay.constant.DateTimeConstants;
import com.doteyplay.core.util.servertask.ServerTaskType;

/**
 * 时间检查点
 *
 */
public class CheckTime
{
	private int month = -1;
	private int day = -1;
	private int week = -1;
	private int hour = -1;
	private int minute = -1;

	/**
	 * 构造方法, 所有参数设置为负数
	 */
	public CheckTime()
	{
	}

	/**
	 * 构造方法, 将不需要检查的参数设置为负数
	 * @param month 1-12
	 * @param day 1-31
	 * @param week 1-7
	 * @param hour 0 - 23
	 * @param minute 0 - 59
	 */
	public CheckTime(int month, int day, int week, int hour, int minute)
	{
		if(day > 0 && week > 0)
			throw new IllegalArgumentException("不能同时设置日期和星期");
		this.month = month;
		this.day = day;
		this.week = week;
		this.hour = hour;
		this.minute = minute;
	}
	
	/**
	 * 获取此对象设置的时间与指定日历时间的差值(单位:毫秒)
	 * @param cal
	 * @return 返回(cal - this)的毫秒值
	 */
	public long getTimeDelta(Calendar cal)
	{
		long checkingTime = cal.getTimeInMillis();
		Calendar c = this.setupFor(checkingTime);
		//System.err.println(DateTimeUtil.getTimeString(c.getTimeInMillis()));
		return c.getTimeInMillis() - checkingTime;
	}
	
	/**
	 * 使用此对象的时间设置创建一个指定时间的日历对象, 
	 * 目的是为了将指定时间调整到最接近此对象设置的时间.
	 * 
	 * 此过程中, 将使用指定时间创建一个日历对象,
	 * 此对象的各时间域设置值将被拷贝到新创建的日历对象的对应域,
	 * 但是此对象未设置的时间域将保留由该日历对象的旧值.
	 * 例如: 指定的时间参数为9月10日, 此对象仅设置了月份为12月,
	 * 其它域留空, 则最终值将为12月10日.
	 * @param checkingTime
	 * @return
	 */
	public Calendar setupFor(long checkingTime)
	{
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(checkingTime);
		
		if(month > 0)
			c.set(Calendar.MONTH, month - 1);
		
		if(hour >= 0)
			c.set(Calendar.HOUR_OF_DAY, hour);
		
		if(minute >= 0)
			c.set(Calendar.MINUTE, minute);
		
		if(day > 0)
		{
			c.set(Calendar.DAY_OF_MONTH, day);
		}
		else if(week > 0)
		{
			c.set(Calendar.DAY_OF_WEEK, week+1);
		}
		
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}

	/**
	 * 判断此对象表示的时间是否在指定 Calendar 表示的时间之后，返回判断结果。
	 * @param cal
	 * @return 
	 */
	public boolean after(Calendar cal)
	{
		return this.getTimeDelta(cal) < 0;
		

	}

	/**
	 * 判断此对象表示的时间是否在指定 Calendar 表示的时间之前，返回判断结果。
	 * @param cal
	 * @return 
	 */
	public boolean before(Calendar cal)
	{
		return this.getTimeDelta(cal) > 0;
		
	}
	
	/**
	 * @return the month
	 */
	public int getMonth()
	{
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(int month)
	{
		this.month = month;
	}

	/**
	 * @return the day
	 */
	public int getDay()
	{
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(int day)
	{
		this.day = day;
	}

	/**
	 * @return the week
	 */
	public int getWeek()
	{
		return week;
	}

	/**
	 * @param week the week to set
	 */
	public void setWeek(int week)
	{
		this.week = week;
	}

	/**
	 * @return the hour
	 */
	public int getHour()
	{
		return hour;
	}

	/**
	 * @param hour the hour to set
	 */
	public void setHour(int hour)
	{
		this.hour = hour;
	}

	/**
	 * @return the minute
	 */
	public int getMinute()
	{
		return minute;
	}

	/**
	 * @param minute the minute to set
	 */
	public void setMinute(int minute)
	{
		this.minute = minute;
	}
	
	public ServerTaskType getServerTaskType() {
		if (month > 0)
			return ServerTaskType.YEARLY;
		if (week > 0)
			return ServerTaskType.WEEKLY;
		if (day > 0)
			return ServerTaskType.MONTHLY;
		if (hour > 0)
			return ServerTaskType.DAILY;
		else
			return ServerTaskType.HOURLY;
	}
	
	public static void main(String[] args)
	{
		final Calendar now = Calendar.getInstance();
		
		CheckTime ct = new CheckTime(-1, -1, 2, 17, 50);
		Calendar t = ct.setupFor(now.getTimeInMillis());
		
		System.err.println(DateTimeConstants.getTimeString(t.getTimeInMillis()));
		if(t.getTimeInMillis() > now.getTimeInMillis())
		{
			t.setTimeInMillis(t.getTimeInMillis() - ServerTaskType.WEEKLY.getPeriodic());
			System.err.println(DateTimeConstants.getTimeString(t.getTimeInMillis()));
		}

		
//		long delta = ct.getTimeDelta(now);
//		System.err.println(DateTimeConstants.buildTimeString(Math.abs(delta)));
		
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		
		System.err.println(cal.get(Calendar.YEAR));
		System.err.println(cal.get(Calendar.MONTH) + 1);
		System.err.println(cal.get(Calendar.DAY_OF_MONTH));
		System.err.println(cal.get(Calendar.DAY_OF_WEEK) - 1);
		System.err.println(cal.get(Calendar.HOUR_OF_DAY));
		System.err.println(cal.get(Calendar.MINUTE));
		System.err.println(cal.get(Calendar.SECOND));
		
		System.err.println("-------------------------------");
		cal.set(Calendar.DAY_OF_WEEK, 3);
		System.err.println(cal.get(Calendar.YEAR));
		System.err.println(cal.get(Calendar.MONTH) + 1);
		System.err.println(cal.get(Calendar.DAY_OF_MONTH));
		System.err.println(cal.get(Calendar.DAY_OF_WEEK) - 1);
		System.err.println(cal.get(Calendar.HOUR_OF_DAY));
		System.err.println(cal.get(Calendar.MINUTE));
		System.err.println(cal.get(Calendar.SECOND));
	}
}
