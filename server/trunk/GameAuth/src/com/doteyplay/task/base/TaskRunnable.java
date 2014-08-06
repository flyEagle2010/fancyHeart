package com.doteyplay.task.base;

import org.apache.log4j.Logger;

/**
 * �����е�task��ʵ�ַ�װ���̶߳��� ͬʱ��run��ʱ���쳣�����˲��񣬷�ֹ���ڳ���Ĵ���������ִ��ʧ��
 * 
 * @author lipengbin
 * 
 */
public class TaskRunnable implements Runnable
{
	private static final Logger logger = Logger.getLogger(TaskRunnable.class);
	/**
	 * �������
	 */
	private Task task;

	public TaskRunnable(Task t)
	{
		this.task = t;
	}

	@Override
	public void run()
	{
		try
		{
			this.task.run();
		}
		catch (Exception e)
		{
			logger.error("ִ�мƻ������ʱ�����쳣", e);
		}
	}
}
