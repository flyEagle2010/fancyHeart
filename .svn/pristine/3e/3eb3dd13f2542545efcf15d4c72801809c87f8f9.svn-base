package com.doteyplay.game.task;

import com.doteyplay.core.server.task.AbstractTaskService;
import com.doteyplay.core.task.CommonTaskManager;
import com.doteyplay.game.task.impl.SceneTaskService;
import com.doteyplay.game.task.impl.UserTaskService;

/**
 * 系统所有的计划任务都是通过这个类进行管理的
 * 
 */
public class TaskManager
{
	/**
	 * 用户任务服务器
	 */
	private AbstractTaskService userTaskService = UserTaskService.getInstance();

	/**
	 * 单例对象
	 */
	private static TaskManager manager = new TaskManager();

	private TaskManager()
	{
	}

	public static TaskManager getInstance()
	{
		return manager;
	}

	public AbstractTaskService getCommonTaskService()
	{
		return CommonTaskManager.getInstance().getCommonTaskService();
	}

	public AbstractTaskService getUserTaskService()
	{
		return userTaskService;
	}

	/**
	 * 停止所有的计划任务。这个执行是有顺序的。
	 */
	public void shutDownAll()
	{
		CommonTaskManager.getInstance().shutdown();
		userTaskService.shutdown();
	}
}
