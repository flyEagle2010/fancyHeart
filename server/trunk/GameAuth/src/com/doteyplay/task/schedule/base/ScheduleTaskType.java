package com.doteyplay.task.schedule.base;

import com.doteyplay.utils.DateTimeConstants;

/**
 * ���������������������
 * 
 * @author Leo Yang (leo8002@sina.com) 2009-7-15����03:40:25
 * 
 */
public enum ScheduleTaskType
{

	/**
	 * ÿСʱ
	 */
	HOURLY,
	/**
	 * ÿ��
	 */
	DAILY,
	/**
	 * ÿ��
	 */
	WEEKLY,
	/**
	 * ÿ��
	 */
	MONTHLY,
	/**
	 * ÿ��
	 */
	YEARLY;

	private final static long[] periodics = { DateTimeConstants.HOUR, DateTimeConstants.DAY,
			DateTimeConstants.WEEK, DateTimeConstants.MONTH, DateTimeConstants.YEAR };

	/**
	 * ��ȡ�����͵�����(��λ:����)
	 * 
	 * @return
	 */
	public long getPeriodic()
	{
		return periodics[this.ordinal()];
	}

	/**
	 * ��ȡ��Ӧ����ֵ��ServerTaskTypeö�ٶ���, �������������׳�ArrayIndexOutOfBoundsException
	 * 
	 * @param value
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public static ScheduleTaskType getByValue(int value)
	{
		return values()[value];
	}
}
