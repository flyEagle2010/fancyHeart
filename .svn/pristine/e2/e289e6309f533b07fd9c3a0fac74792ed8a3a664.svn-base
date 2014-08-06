package com.doteyplay.core.server.task;

import java.util.concurrent.ScheduledFuture;

import org.apache.log4j.Logger;

/**
 * 计划任务回调的Handler
 * 
 */
public class TaskCallbackHandler {
	private static final Logger logger = Logger
			.getLogger(TaskCallbackHandler.class);
	/**
	 * 对应的任务对象
	 */
	private Task task;
	/**
	 * 一个延迟的，计划任务异步执行的结果
	 */
	private ScheduledFuture<?> future;
	/**
	 * 所属的Service
	 */
	private AbstractTaskService service;

	public TaskCallbackHandler(Task t, ScheduledFuture<?> f,
			AbstractTaskService s) {
		this.task = t;
		this.future = f;
		this.service = s;
		if (logger.isDebugEnabled())
			logger.debug("task=" + task);
	}

	public void cancel(boolean mayInterruptIfRunning) {
		if (mayInterruptIfRunning) {
			cancelInRunning();
		} else {
			cancel();
		}
	}

	/**
	 * 正常退出，如果任务在运行，等待任务运行结束退出。
	 */
	public void cancel() {
		if (logger.isDebugEnabled())
			logger.debug("正常取消计划任务." + task);
		service.removeHandler(this);
		future.cancel(false);
	}

	/**
	 * 强制退出任务，如果任务在运行，也强制退出。
	 */
	public void cancelInRunning() {
		if (logger.isDebugEnabled())
			logger.debug("强制取消计划任务." + task);
		service.removeHandler(this);
		future.cancel(true);
	}

	public void setFuture(ScheduledFuture<?> future) {
		this.future = future;
	}
}
