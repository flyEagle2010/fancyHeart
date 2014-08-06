package com.doteyplay.task.schedule.base;

import java.util.Calendar;

import com.doteyplay.utils.DateTimeConstants;

/**
 * ʱ�����
 * @author Leo Yang (leo8002@sina.com)
 * 2009-6-15����04:21:33
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
	 * ���췽��, ���в�������Ϊ����
	 */
	public CheckTime()
	{
	}

	/**
	 * ���췽��, ������Ҫ���Ĳ�������Ϊ����
	 * @param month 1-12
	 * @param day 1-31
	 * @param week 1-7
	 * @param hour 0 - 23
	 * @param minute 0 - 59
	 */
	public CheckTime(int month, int day, int week, int hour, int minute)
	{
		if(day > 0 && week > 0)
			throw new IllegalArgumentException("����ͬʱ�������ں�����");
		this.month = month;
		this.day = day;
		this.week = week;
		this.hour = hour;
		this.minute = minute;
	}
	
	/**
	 * ��ȡ�˶������õ�ʱ����ָ������ʱ��Ĳ�ֵ(��λ:����)
	 * @param cal
	 * @return ����(cal - this)�ĺ���ֵ
	 */
	public long getTimeDelta(Calendar cal)
	{
		long checkingTime = cal.getTimeInMillis();
		Calendar c = this.setupFor(checkingTime);
		//System.err.println(DateTimeUtil.getTimeString(c.getTimeInMillis()));
		return c.getTimeInMillis() - checkingTime;
	}
	
	/**
	 * ʹ�ô˶����ʱ�����ô���һ��ָ��ʱ����������, 
	 * Ŀ����Ϊ�˽�ָ��ʱ�������ӽ�˶������õ�ʱ��.
	 * 
	 * �˹����, ��ʹ��ָ��ʱ�䴴��һ���������,
	 * �˶���ĸ�ʱ��������ֵ�����������´������������Ķ�Ӧ��,
	 * ���Ǵ˶���δ���õ�ʱ���򽫱����ɸ��������ľ�ֵ.
	 * ����: ָ����ʱ�����Ϊ9��10��, �˶�����������·�Ϊ12��,
	 * ����������, ������ֵ��Ϊ12��10��.
	 * @param checkingTime
	 * @return
	 */
	public Calendar setupFor(long checkingTime)
	{
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(checkingTime);
		
		if(month > 0)
			c.set(Calendar.MONDAY, month - 1);
		
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
	 * �жϴ˶����ʾ��ʱ���Ƿ���ָ�� Calendar ��ʾ��ʱ��֮�󣬷����жϽ��
	 * @param cal
	 * @return 
	 */
	public boolean after(Calendar cal)
	{
		return this.getTimeDelta(cal) < 0;
	}

	/**
	 * �жϴ˶����ʾ��ʱ���Ƿ���ָ�� Calendar ��ʾ��ʱ��֮ǰ�������жϽ��
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
	
	public ScheduleTaskType getServerTaskType() {
		if (month > 0)
			return ScheduleTaskType.YEARLY;
		if (week > 0)
			return ScheduleTaskType.WEEKLY;
		if (day > 0)
			return ScheduleTaskType.MONTHLY;
		if (hour > 0)
			return ScheduleTaskType.DAILY;
		else
			return ScheduleTaskType.HOURLY;
	}
	
	public static void main(String[] args)
	{
		final Calendar now = Calendar.getInstance();
		
		CheckTime ct = new CheckTime(-1, -1, 2, 17, 50);
		Calendar t = ct.setupFor(now.getTimeInMillis());
		
		System.err.println(DateTimeConstants.getTimeString(t.getTimeInMillis()));
		if(t.getTimeInMillis() > now.getTimeInMillis())
		{
			t.setTimeInMillis(t.getTimeInMillis() - ScheduleTaskType.WEEKLY.getPeriodic());
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
