package com.doteyplay.game.message.system;

import com.doteyplay.core.server.task.Task;
import com.doteyplay.game.domain.common.SessionHolder;

public class ACKTask implements Task
{
	private SessionHolder holder;

	public ACKTask(SessionHolder holder)
	{
		this.holder = holder;
	}

	@Override
	public void run()
	{
		holder.update();
	}
}
