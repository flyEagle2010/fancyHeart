package com.doteyplay.task.base;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * ����ļƻ����������
 * 
 * @author lipengbin
 * 
 */
public abstract class AbstractTaskService
{
	private static final Logger logger = Logger.getLogger(AbstractTaskService.class);

	/**
	 * ��ǰ��������������ص����б�
	 */
	protected List<TaskCallbackHandler> handlerList = new CopyOnWriteArrayList<TaskCallbackHandler>();

	/**
	 * ��Ӽƻ�����
	 * 
	 * @param task
	 * @return
	 */
	public TaskCallbackHandler scheduleAtFixedRate(Task task)
	{
		ScheduledFuture<?> future = getExecutor().scheduleAtFixedRate(new TaskRunnable(task), getDelay(),
				getPeriod(), TimeUnit.MILLISECONDS);
		TaskCallbackHandler handler = new TaskCallbackHandler(task, future, this);
		handlerList.add(handler);
		return handler;
	}

	/**
	 * ��Ӽƻ�����
	 * 
	 * @param task
	 * @param delay
	 * @return
	 */
	public TaskCallbackHandler scheduleAtFixedRate(Task task, long delay)
	{
		ScheduledFuture<?> future = getExecutor().scheduleAtFixedRate(new TaskRunnable(task), delay,
				getPeriod(), TimeUnit.MILLISECONDS);
		TaskCallbackHandler handler = new TaskCallbackHandler(task, future, this);
		handlerList.add(handler);
		return handler;
	}

	/**
	 * ��Ӽƻ�����
	 * 
	 * @param task
	 * @param delay
	 * @param period
	 * @return
	 */
	public TaskCallbackHandler scheduleAtFixedRate(Task task, long delay, long period)
	{
		ScheduledFuture<?> future = getExecutor().scheduleAtFixedRate(new TaskRunnable(task), delay, period,
				TimeUnit.MILLISECONDS);
		TaskCallbackHandler handler = new TaskCallbackHandler(task, future, this);
		handlerList.add(handler);
		return handler;
	}

	/**
	 * һ���Եļƻ�����
	 * 
	 * @param task
	 * @param delay
	 * @param period
	 * @return
	 */
	public TaskCallbackHandler scheduleOnceTask(Task task, long delay)
	{
		ScheduledFuture<?> future = getExecutor().schedule(new TaskRunnable(task), delay,
				TimeUnit.MILLISECONDS);
		TaskCallbackHandler handler = new TaskCallbackHandler(task, future, this);
		handlerList.add(handler);
		return handler;
	}

	/**
	 * �Ƴ�ָ����Handler
	 * 
	 * @param handler
	 */
	public void removeHandler(TaskCallbackHandler handler)
	{
		handlerList.remove(handler);
	}

	/**
	 * ͳһ���мƻ�����
	 */
	public void info()
	{
		if (logger.isInfoEnabled())
			logger.info("��" + getName() + "��ִ���߳�����,Core=" + getExecutor().getCorePoolSize() + ",active="
					+ getExecutor().getActiveCount() + ",complateNum="
					+ getExecutor().getCompletedTaskCount() + ",taskNum=" + handlerList.size());
	}

	/**
	 * ִ��ֹͣ�ķ���
	 */
	public void shutdown()
	{
		for (TaskCallbackHandler handler : handlerList)
		{
			handler.cancel();
		}
		logger.error("ֹͣ���������");
	}

	/**
	 * ��ȡϵͳ�����ִ����
	 * 
	 * @return ScheduledExecutorService
	 * 
	 */
	protected abstract ScheduledThreadPoolExecutor getExecutor();

	/**
	 * ��ȡִ������
	 * 
	 * @return
	 */
	protected abstract long getPeriod();

	/**
	 * ��ȡĬ����ʱ����
	 * 
	 * @return
	 */
	protected abstract long getDelay();

	/**
	 * ���ص�ǰ��������
	 * 
	 * @return
	 */
	protected abstract String getName();
}
