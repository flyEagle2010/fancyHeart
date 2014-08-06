package com.doteyplay.core.server.task;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;


/**
 * 抽象的计划任务服务器
 */
public abstract class AbstractTaskService {
	private static final Logger logger=Logger.getLogger(AbstractTaskService.class);
	
	/**
	 * 当前服务包含的所有任务回调的列表
	 */
	protected List<TaskCallbackHandler> handlerList=new CopyOnWriteArrayList<TaskCallbackHandler>();
	
	/**
	 * 添加计划任务
	 * @param task
	 * @return
	 */
	public TaskCallbackHandler scheduleAtFixedRate(Task task){
		ScheduledFuture<?> future=getExecutor().scheduleAtFixedRate(new TaskRunnable(task), getDelay(), getPeriod(), TimeUnit.MILLISECONDS);
		TaskCallbackHandler handler=new TaskCallbackHandler(task,future,this);
		handlerList.add(handler);
		return handler;
	}
	/**
	 * 添加计划任务
	 * @param task
	 * @param delay
	 * @return
	 */
	public TaskCallbackHandler scheduleAtFixedRate(Task task,long delay){
		ScheduledFuture<?> future=getExecutor().scheduleAtFixedRate(new TaskRunnable(task), delay, getPeriod(), TimeUnit.MILLISECONDS);
		TaskCallbackHandler handler=new TaskCallbackHandler(task,future,this);
		handlerList.add(handler);
		return handler;
	}
	/**
	 * 添加计划任务
	 * @param task
	 * @param delay
	 * @param period
	 * @return
	 */
	public TaskCallbackHandler scheduleAtFixedRate(Task task,long delay,long period){
		ScheduledFuture<?> future=getExecutor().scheduleAtFixedRate(new TaskRunnable(task),delay, period, TimeUnit.MILLISECONDS);
		TaskCallbackHandler handler=new TaskCallbackHandler(task,future,this);
		handlerList.add(handler);
		return handler;
	}
	/**
	 * 一次性的计划任务
	 * @param task
	 * @param delay
	 * @param period
	 * @return
	 */
	public TaskCallbackHandler scheduleOnceTask(Task task,long delay){
		ScheduledFuture<?> future=getExecutor().schedule(new TaskRunnable(task),delay,TimeUnit.MILLISECONDS);
		TaskCallbackHandler handler=new TaskCallbackHandler(task,future,this);
		handlerList.add(handler);
		return handler;
	}
	/**
	 * 移除指定的Handler
	 * @param handler
	 */
	public void removeHandler(TaskCallbackHandler handler){
		handlerList.remove(handler);
	}
	/**
	 * 统一运行计划任务
	 */
	public void info(){
		if(logger.isInfoEnabled())
			logger.info("【"+getName()+"】执行线程数量,Core="+getExecutor().getCorePoolSize()+",active="+getExecutor().getActiveCount()+",complateNum="+getExecutor().getCompletedTaskCount()+",taskNum="+handlerList.size());
	}
	/**
	 * 执行停止的方法
	 */
	public void shutdown(){
		for(TaskCallbackHandler handler:handlerList){
			handler.cancel();
		}
		logger.error("停止任务服务器");
	}
	/**
	 * 获取系统具体的执行器
	 * @return
	 * ScheduledExecutorService
	 * 
	 */
	protected abstract ScheduledThreadPoolExecutor getExecutor();
	/**
	 * 获取执行周期
	 * @return
	 */
	protected abstract long getPeriod();
	/**
	 * 获取默认延时周期
	 * @return
	 */
	protected abstract long getDelay();
	/**
	 * 返回当前服务的名称
	 * @return
	 */
	protected abstract String getName();
}
