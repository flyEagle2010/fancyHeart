package com.doteyplay.core.server.task;

import org.apache.log4j.Logger;


/**
 * 将所有的task的实现封装的线程对象，
 * 同时在run的时候将异常进行了捕获，防止由于程序的错误导致任务执行失败
 */
public class TaskRunnable implements Runnable {
	private static final Logger logger=Logger.getLogger(TaskRunnable.class);
	/**
	 * 任务对象
	 */
	private Task task;
	
	public TaskRunnable(Task t){
		this.task=t;
	}
	
	@Override
	public void run() {
		try{
			this.task.run();
		}
		catch(Exception e){
			logger.error("执行计划任务的时候发生异常",e);
		}
	}
}
