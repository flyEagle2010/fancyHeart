package com.doteyplay.game.task.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.doteyplay.core.server.task.AbstractTaskService;
import com.doteyplay.core.service.CoreServiceConstants;

/**
 * 场景计划任务
 * 
 */
public class SceneTaskService extends AbstractTaskService
{

	/**
	 * 具体执行的线程池对象
	 */
	private final ScheduledThreadPoolExecutor scheduler = (ScheduledThreadPoolExecutor) Executors
			.newScheduledThreadPool(16);

	/**
	 * 单例对象
	 */
	private static SceneTaskService service = new SceneTaskService();

	/**
	 * 第一次执行的延迟时间
	 */
	private final static long initialDelay = 20001;

	private SceneTaskService()
	{
	}

	public static SceneTaskService getInstance()
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
		return "场景计划任务";
	}

	@Override
	protected long getPeriod()
	{
		return CoreServiceConstants.MILLISECOND_PER_FRAME;
	}

}
