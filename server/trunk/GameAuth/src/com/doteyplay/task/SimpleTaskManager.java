package com.doteyplay.task;

import com.doteyplay.task.base.AbstractTaskService;
import com.doteyplay.task.base.SimpleTaskService;

public class SimpleTaskManager
{
	private static AbstractTaskService taskService = SimpleTaskService.getInstance();

	public static void shutdown()
	{
		taskService.shutdown();
	}

	public static AbstractTaskService getService()
	{
		return taskService;
	}
}
