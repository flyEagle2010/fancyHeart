package com.doteyplay.game.task.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.doteyplay.core.server.task.AbstractTaskService;
import com.doteyplay.core.service.CoreServiceConstants;
import com.doteyplay.game.task.FrameTask;
import com.doteyplay.game.util.FrameUpateManager;

/**
 * 除了场景帧频，其他的帧频通过这个计划任务帧频管理器进行管理。
 * 
 */
public class FrameTaskService extends AbstractTaskService
{
	/**
	 * 具体执行的线程池对象
	 */
	private final ScheduledThreadPoolExecutor scheduler = (ScheduledThreadPoolExecutor) Executors
			.newScheduledThreadPool(8);
	/**
	 * 单例对象
	 */
	private static FrameTaskService service = new FrameTaskService();

	private FrameTaskService()
	{
	}

	public static FrameTaskService getInstance()
	{
		return service;
	}

	@Override
	protected long getDelay()
	{
		return CoreServiceConstants.MILLISECOND_PER_FRAME;
	}

	@Override
	protected ScheduledThreadPoolExecutor getExecutor()
	{
		return scheduler;
	}

	@Override
	protected String getName()
	{
		return "通用帧频任务";
	}

	@Override
	protected long getPeriod()
	{
		return CoreServiceConstants.MILLISECOND_PER_FRAME;
	}

	/**
	 * 增加帧频管理器
	 * 
	 * @param update
	 */
	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public void addUpdateManager(FrameUpateManager<?> update)
	{
		this.scheduleAtFixedRate(new FrameTask(update));
	}

}
