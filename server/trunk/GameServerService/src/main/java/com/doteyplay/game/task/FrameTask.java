package com.doteyplay.game.task;

import com.doteyplay.core.server.task.Task;
import com.doteyplay.game.util.FrameUpateManager;
import com.doteyplay.game.util.IFrameUpdatable;

/**
 * 帧频管理器的计划任务
 * 
 * @param <T>
 */
public class FrameTask<T extends IFrameUpdatable> implements Task
{
	private FrameUpateManager<T> update;

	public FrameTask(FrameUpateManager<T> f)
	{
		this.update = f;
	}

	@Override
	public void run()
	{
		update.update();
	}

}
