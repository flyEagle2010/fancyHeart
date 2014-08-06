package com.doteyplay.core.util.servertask;

import java.util.Calendar;

import org.apache.log4j.Logger;

import com.doteyplay.constant.DateTimeConstants;
import com.doteyplay.core.configloader.TaskHandlerRegistry;
import com.doteyplay.core.server.servertaskimpl.AbstractTaskItem;
import com.doteyplay.core.server.task.Task;
import com.doteyplay.core.util.CheckTime;

/**
 * ServerTask的调度的真正执行单元
 * 
 */
class ServerTaskUnit implements Task
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ServerTaskUnit.class);

	private ServerTaskInfo info;

	private final ServerTaskType taskType;

	private final CheckTime checkTime;

	private long lastUpdate;

	private long nextUpdateTime;

	private final ServerTask<ServerTaskInfo, IServerTaskDBManager> task;

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public ServerTaskUnit(ServerTaskInfo info)
	{
		this.info = info;
		this.taskType = info.getServerTaskType();
		this.checkTime = new CheckTime(info.getMonth(), info.getDay(),
				info.getWeek(), info.getHour(), info.getMinute());

		try
		{
			this.task = (ServerTask) TaskHandlerRegistry.getInstance()
					.getTaskHandlerClz().newInstance();
		} catch (Exception e)
		{
			throw new IllegalArgumentException(info.getName() + "初始化任务执行类失败", e);
		}

		IServerTaskDBManager dbManager = TaskHandlerRegistry.getInstance()
				.getDbManager();
		try
		{
			ServerTaskInfo tmpInfo = dbManager.getServerTaskInfo(info.getId());
			if (tmpInfo != null)
				this.nextUpdateTime = tmpInfo.getNextExecuteTime();
			else
			{
				this.setupNextUpdateTime();
				this.info.setNextExecuteTime(this.nextUpdateTime);
				dbManager.saveServerTaskInfo(this.info);
			}

		} catch (Exception e)
		{
			throw new IllegalArgumentException(info.getName() + "初始化任务参数类失败", e);
		}

		if (this.nextUpdateTime <= 0)
			this.setupNextUpdateTime();

		/**
		 * 加载具体项目
		 */
		if (info.getItemClassName() != null
				&& info.getItemClassName().length > 0)
		{

			try
			{
				for (String className : info.getItemClassName())
				{
					Class c = Class.forName(className);
					task.addTaskItem((AbstractTaskItem) c.newInstance());
				}
			} catch (Exception e)
			{
				throw new IllegalArgumentException(info.getName()
						+ "初始化任务参数类失败", e);
			}

		}

		if (logger.isInfoEnabled())
			logger.info("服务器定时调度任务[" + info.getName() + "]下次调度时间:"
					+ DateTimeConstants.getTimeString(this.nextUpdateTime));
	}

	private void setupNextUpdateTime()
	{
		long now = System.currentTimeMillis();
		Calendar cal = this.checkTime.setupFor(now);
		lastUpdate = cal.getTimeInMillis();
		if (this.lastUpdate > now)
			this.lastUpdate -= taskType.getPeriodic();
		else
			taskType.getNextTime(cal);
		nextUpdateTime = cal.getTimeInMillis();
	}

	@Override
	public void run()
	{
		final Calendar now = Calendar.getInstance();
		final long nowTime = now.getTimeInMillis();
		if (nowTime > nextUpdateTime)
			doTask();
	}

	// 执行真正的任务调度
	private void doTask()
	{
		try
		{
			task.run();

			// 设置最后一次更新的时间
			this.setupNextUpdateTime();

			// 将此更新时间戳持久化
			task.afterTaskExecuted(info, this.nextUpdateTime);

			if (logger.isInfoEnabled())
			{
				logger.info("调度服务器定时任务完毕:[" + info.getName() + "], 下次执行时间设置为:"
						+ DateTimeConstants.getTimeString(this.nextUpdateTime));
			}
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * @return the info
	 */
	ServerTaskInfo getInfo()
	{
		return info;
	}

	long getLastExecuteTime()
	{
		return lastUpdate;
	}

	long getNextExecuteTime()
	{
		return nextUpdateTime;
	}

	public void setOpen(String itemClassName, boolean open)
	{
		this.task.setOpen(itemClassName, open);
	}
}
