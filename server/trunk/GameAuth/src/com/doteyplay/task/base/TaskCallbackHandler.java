package com.doteyplay.task.base;

import java.util.concurrent.ScheduledFuture;

import org.apache.log4j.Logger;

/**
 * �ƻ�����ص���Handler
 * 
 * @author lipengbin
 * 
 */
public class TaskCallbackHandler
{
	private static final Logger logger = Logger.getLogger(TaskCallbackHandler.class);
	/**
	 * ��Ӧ���������
	 */
	private Task task;
	/**
	 * һ���ӳٵģ��ƻ������첽ִ�еĽ��
	 */
	private ScheduledFuture<?> future;
	/**
	 * ������Service
	 */
	private AbstractTaskService service;

	public TaskCallbackHandler(Task t, ScheduledFuture<?> f, AbstractTaskService s)
	{
		this.task = t;
		this.future = f;
		this.service = s;
		if (logger.isDebugEnabled())
			logger.debug("task=" + task);
	}

	public void cancel(boolean mayInterruptIfRunning)
	{
		if (mayInterruptIfRunning)
		{
			cancelInRunning();
		}
		else
		{
			cancel();
		}
	}

	/**
	 * ���˳���������������У��ȴ��������н����˳���
	 */
	public void cancel()
	{
		if (logger.isDebugEnabled())
			logger.debug("��ȡ��ƻ�����." + task);
		service.removeHandler(this);
		future.cancel(false);
	}

	/**
	 * ǿ���˳�����������������У�Ҳǿ���˳���
	 */
	public void cancelInRunning()
	{
		if (logger.isDebugEnabled())
			logger.debug("ǿ��ȡ��ƻ�����." + task);
		service.removeHandler(this);
		future.cancel(true);
	}

	public void setFuture(ScheduledFuture<?> future)
	{
		this.future = future;
	}
}
