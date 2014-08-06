package com.doteyplay.core.util.servertask;

import com.doteyplay.core.server.servertaskimpl.AbstractTaskItem;
import com.doteyplay.core.server.task.Task;

/**
 * 服务器定时重复调度任务
 *
 */
public interface ServerTask<T extends ServerTaskInfo, P extends IServerTaskDBManager> extends Task
{
	/**
	 * 持久化指定任务的最近的执行时间
	 * @param info 任务信息
	 * @param lastUpdate 最近的执行时间
	 */
	public void afterTaskExecuted(T info, long lastUpdate);
	
	/**
	 * 增加额外执行项
	 */
	public void addTaskItem(AbstractTaskItem item);
	
	/**
	 * 设定活动执行项
	 */
	public boolean setOpen(String itemClassName, boolean open);
}
