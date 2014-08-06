package com.doteyplay.game.task.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.doteyplay.core.server.task.AbstractTaskService;
import com.doteyplay.core.service.CoreServiceConstants;

/**
 * 用户计划任务服务
 * 
 */
public class UserTaskService extends AbstractTaskService
{
	/**
	 * 具体执行的线程池对象
	 */
	private final ScheduledThreadPoolExecutor scheduler = (ScheduledThreadPoolExecutor) Executors
			.newScheduledThreadPool(16);
	/**
	 * 单例对象
	 */
	private static UserTaskService service = new UserTaskService();

	/**
	 * 第一次执行的延迟时间
	 */
	private final static long initialDelay = 0;

	private UserTaskService()
	{
	}

	public static AbstractTaskService getInstance()
	{
		return service;
	}

	@Override
	protected long getDelay()
	{
		return initialDelay;
	}

	@Override
	protected ScheduledThreadPoolExecutor getExecutor()
	{
		return scheduler;
	}

	@Override
	protected String getName()
	{
		return "用户计划任务";
	}

	@Override
	protected long getPeriod()
	{
		return CoreServiceConstants.MILLISECOND_PER_FRAME;
	}
}
