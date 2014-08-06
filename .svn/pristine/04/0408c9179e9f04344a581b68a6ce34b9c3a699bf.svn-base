package com.doteyplay.core.task.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.doteyplay.core.server.task.AbstractTaskService;
import com.doteyplay.core.service.CoreServiceConstants;

/**
 * 通用的任务服务提供器
 * 
 */
public class CommonTaskService extends AbstractTaskService {

	/**
	 * 具体执行的线程池对象
	 */
	private final ScheduledThreadPoolExecutor scheduler = (ScheduledThreadPoolExecutor) Executors
			.newScheduledThreadPool(16);
	/**
	 * 第一次执行的延迟时间
	 */
	private static long initialDelay = 20001;

	/**
	 * 单例对象
	 */
	private static CommonTaskService instance = new CommonTaskService();

	private CommonTaskService() {
	}

	public static CommonTaskService getInstance() {
		return instance;
	}

	@Override
	protected long getDelay() {
		return initialDelay;
	}

	@Override
	protected ScheduledThreadPoolExecutor getExecutor() {
		return scheduler;
	}

	@Override
	protected String getName() {
		return "通用任务服务器";
	}

	@Override
	protected long getPeriod() {
		return CoreServiceConstants.MILLISECOND_PER_FRAME;
	}

}
