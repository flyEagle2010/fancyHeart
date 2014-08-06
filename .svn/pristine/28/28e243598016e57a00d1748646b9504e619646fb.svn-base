package com.doteyplay.core.util.servertask;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.doteyplay.constant.DateTimeConstants;
import com.doteyplay.core.configloader.TaskHandlerRegistry;
import com.doteyplay.core.server.task.TaskCallbackHandler;
import com.doteyplay.core.service.AbstractService;
import com.doteyplay.core.service.CoreServiceConstants;
import com.doteyplay.core.task.CommonTaskManager;

/**
 * 服务器定时重复调度任务管理器
 * 
 */
public class ServerTaskManager extends AbstractService
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ServerTaskManager.class);

	private static ServerTaskManager instance = new ServerTaskManager();

	/**
	 * KEY为服务器定时重复调度任务ID，VALUE为该调度任务的执行器ServerTaskUnit
	 */
	private Map<Integer, ServerTaskUnit> units;

	/**
	 * 新增的任务
	 */
	private Map<Integer, ServerTaskUnit> addUnits;

	private ServerTaskManager()
	{
		super(CoreServiceConstants.SERVICE_ID_TIMED_TASK_MANAGER);
	}

	/**
	 * 获取管理器的唯一实例
	 * 
	 * @return
	 */
	public static ServerTaskManager getInstance()
	{
		return instance;
	}

	@Override
	public boolean initialize()
	{
		// List<TimerTaskManager> infos = TimerTaskManagerDataProvider
		// .getInstance().getTimerTaskManagerList();

		return super.initialize();
	}

	/**
	 * 初始化此管理器需要调度的任务配置信息,注意:只能初始化一次.
	 * 
	 * @param infos
	 */
	public synchronized void initialize(
			Collection<? extends ServerTaskInfo> infos)
	{
		if (this.units != null)
			throw new IllegalStateException(this.getClass().getSimpleName()
					+ "已初始化");

		units = new ConcurrentHashMap<Integer, ServerTaskUnit>();
		addUnits = new ConcurrentHashMap<Integer, ServerTaskUnit>();

		for (ServerTaskInfo info : infos)
		{
			try
			{
				ServerTaskUnit unit = new ServerTaskUnit(info);
				if (units.containsKey(info.getId()))
					logger.error("已经存在id=" + info.getId() + "的任务项");
				else
					units.put(info.getId(), unit);
			} catch (RuntimeException e)
			{
				e.printStackTrace();
			}
		}
	}

	public synchronized boolean reload()
	{
		TaskHandlerRegistry.reload();
		Collection<? extends ServerTaskInfo> infos = TaskHandlerRegistry
				.getInstance().getTaskInfo();
		for (ServerTaskInfo info : infos)
		{
			try
			{
				ServerTaskUnit unit = new ServerTaskUnit(info);
				if (units.containsKey(info.getId()))
					logger.error("已经存在id=" + info.getId() + "的任务项");
				else
				{
					addUnits.put(info.getId(), unit);
					units.put(info.getId(), unit);
				}
			} catch (RuntimeException e)
			{
				e.printStackTrace();
			}
		}

		if (addUnits == null || addUnits.isEmpty())
			return false;

		for (ServerTaskUnit unit : addUnits.values())
		{
			int precision = unit.getInfo().getSchedulePrecision();
			if (precision < 1)
				precision = 5;
			CommonTaskManager
					.getInstance()
					.getCommonTaskService()
					.scheduleAtFixedRate(unit, DateTimeConstants.SECOND * 2,
							DateTimeConstants.SECOND * precision);
		}
		addUnits.clear();
		return true;
	}

	/**
	 * 启动此管理器, 开始任务调度
	 */
	@Override
	public boolean startService()
	{
		if (units == null || units.isEmpty())
			return false;

		for (ServerTaskUnit unit : units.values())
		{
			int precision = unit.getInfo().getSchedulePrecision();
			if (precision < 1)
				precision = 5;
			CommonTaskManager
					.getInstance()
					.getCommonTaskService()
					.scheduleAtFixedRate(unit, DateTimeConstants.SECOND * 2,
							DateTimeConstants.SECOND * precision);
		}
		return true;
	}

	/**
	 * 关闭此管理器(此实现什么也不做, 保留)
	 */
	@Override
	public boolean stopService()
	{
		return true;
	}

	public long getServerTaskLastExecuteTime(int id)
	{
		try
		{
			return units.get(id).getLastExecuteTime();
		} catch (Exception e)
		{
			throw new IllegalArgumentException("指定的任务不存在：" + id);
		}
	}

	public boolean setOpen(String name, String itemClassName, boolean open)
	{
		if (units == null || units.isEmpty())
			return false;

		for (ServerTaskUnit unit : units.values())
		{
			// 找到了
			if (unit.getInfo().getName().equals(name))
			{
				unit.setOpen(itemClassName, open);
			}
		}

		return true;
	}
}
