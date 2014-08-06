package com.doteyplay.task.schedule;

import com.doteyplay.task.schedule.base.ScheduleTaskInfo;
import com.doteyplay.task.schedule.base.ScheduleTaskType;

public class ScheduleInfo implements ScheduleTaskInfo
{

	private int PKId;

	private String name;

	private int taskType;

	private int month;

	private int week;

	private int date;

	private int hour;

	private int minute;

	private String className;

	private long lastExecuteTime;

	private String description;

	private int schedulePrecision;

	private String parameterClass;

	private String[] itemClass;

	public int getPKId()
	{
		return PKId;
	}

	public void setPKId(int id)
	{
		PKId = id;
	}

	@Override
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getTaskType()
	{
		return taskType;
	}

	public void setTaskType(int taskType)
	{
		this.taskType = taskType;
	}

	public int getMonth()
	{
		return month;
	}

	public void setMonth(int month)
	{
		this.month = month;
	}

	public int getDate()
	{
		return date;
	}

	public void setDate(int date)
	{
		this.date = date;
	}

	public int getHour()
	{
		return hour;
	}

	public void setHour(int hour)
	{
		this.hour = hour;
	}

	public int getMinute()
	{
		return minute;
	}

	public void setMinute(int minute)
	{
		this.minute = minute;
	}

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}

	public long getLastExecuteTime()
	{
		return lastExecuteTime;
	}

	public void setLastExecuteTime(long lastExecuteTime)
	{
		this.lastExecuteTime = lastExecuteTime;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the parameterClass
	 */
	public String getParameterClass()
	{
		return parameterClass;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.t4game.mmorpg.core.util.servertask.ScheduleTaskInfo#getParameterClassName()
	 */
	@Override
	public String getParameterClassName()
	{
		return parameterClass;
	}

	/**
	 * @param parameterClass
	 *            the parameterClass to set
	 */
	public void setParameterClass(String parameterClass)
	{
		this.parameterClass = parameterClass;
	}

	public int getDay()
	{
		return date;
	}

	@Override
	public int getId()
	{
		return PKId;
	}

	@Override
	public ScheduleTaskType getServerTaskType()
	{
		return ScheduleTaskType.getByValue(taskType);
	}

	@Override
	public String getTaskClassName()
	{
		return className;
	}

	public int getWeek()
	{
		return week;
	}

	@Override
	public int getSchedulePrecision()
	{
		return schedulePrecision;
	}

	public void setSchedulePrecision(int schedulePrecision)
	{
		this.schedulePrecision = schedulePrecision;
	}

	public void setWeek(int week)
	{
		this.week = week;
	}

	@Override
	public String[] getItemClassName()
	{
		return itemClass;
	}

	public void setItemClassName(String[] itemClass)
	{
		this.itemClass = itemClass;
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("PKId = " + PKId);
		sb.append("\t name = " + name);
		sb.append("\t taskType = " + taskType);
		sb.append("\t month = " + month);
		sb.append("\t date = " + date);
		sb.append("\t hour = " + hour);
		sb.append("\t minute = " + minute);
		sb.append("\t className = " + className);
		sb.append("\t lastExecuteTime = " + lastExecuteTime);
		sb.append("\t description = " + description);
		return sb.toString();
	}
}
