package com.doteyplay.core.server.servertaskimpl;

import com.doteyplay.core.util.servertask.ServerTaskInfo;
import com.doteyplay.core.util.servertask.ServerTaskType;

public class TimerTaskInfo implements ServerTaskInfo
{

	private int PKId;

	private String name;

	private int taskType;

	private int month;

	private int week;

	private int date;

	private int hour;

	private int minute;

	private long lastExecuteTime = 0l;

	private String description;

	private int schedulePrecision;

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

	public long getNextExecuteTime()
	{
		return lastExecuteTime;
	}

	public void setNextExecuteTime(long lastExecuteTime)
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
		sb.append("\t lastExecuteTime = " + lastExecuteTime);
		sb.append("\t description = " + description);
		return sb.toString();
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
	public ServerTaskType getServerTaskType()
	{
		return ServerTaskType.getByValue(taskType);
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

}
