/**
 * 
 */
package com.doteyplay.core.service;

public final class CoreServiceConstants {
	/**
	 * 每秒帧数
	 */
	public static final int FRAMES_PER_SECOND = 10;
	/**
	 * 场景帧调度任务执行时间间隔，单位为毫秒
	 */
	public static final int MILLISECOND_PER_FRAME = 100;

	/**
	 * 定时任务服务
	 * 简介: 周更新任务/日更新任务等
	 */
	public static final String SERVICE_ID_TIMED_TASK_MANAGER="TimedTaskManager"; 
	/**
	 * 任务调度服务管理器
	 * 简介: 统一进行线程启动/配置管理/调度等
	 */
	public static final String SERVICE_ID_TASK_SERVICE_MANAGER="TaskServiceManager"; 
	
	/**
	 * 同步方法标识
	 */
	public static final String SYNC_METHOD_FLAG = "sync"; 
}
