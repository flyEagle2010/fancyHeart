package com.doteyplay.core.util.servertask;

import java.util.Calendar;

import com.doteyplay.constant.DateTimeConstants;



/**
 * 服务器任务调度周期类型
 * 
 */
public enum ServerTaskType
{ 

	/**
	 * 每小时
	 */
	HOURLY,
	/**
	 * 每日
	 */
	DAILY,
	/**
	 * 每周
	 */
	WEEKLY,
	/**
	 * 每月
	 */
	MONTHLY,
	/**
	 * 每年
	 */
	YEARLY;
	
	private final static long[] periodics =
	{
		DateTimeConstants.HOUR,
		DateTimeConstants.DAY,
		DateTimeConstants.WEEK,
		DateTimeConstants.MONTH,
		DateTimeConstants.YEAR
	};
	
	/**
	 * 获取此类型的周期(单位:毫秒)
	 * @return
	 */
	public long getPeriodic()
	{
		return periodics[this.ordinal()];
	}
	
	/**
	 * 获取对应索引值的ServerTaskType枚举对象, 如果索引错误则抛出ArrayIndexOutOfBoundsException
	 * @param value
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public static ServerTaskType getByValue(int value)
	{
		return values()[value];
	}
	
	private final static int[] unit =
	{
		Calendar.HOUR_OF_DAY,
		Calendar.DAY_OF_YEAR,
		Calendar.WEEK_OF_YEAR,
		Calendar.MONTH,
		Calendar.YEAR
	};
	
	public void getNextTime(Calendar lastExecuteTime){
		lastExecuteTime.add(unit[this.ordinal()], 1);
	}
}
