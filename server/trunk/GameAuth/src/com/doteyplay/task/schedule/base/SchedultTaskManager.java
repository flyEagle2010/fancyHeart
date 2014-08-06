package com.doteyplay.task.schedule.base;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.doteyplay.task.SimpleTaskManager;
import com.doteyplay.task.base.TaskCallbackHandler;
import com.doteyplay.utils.DateTimeConstants;

public class SchedultTaskManager
{

	private static SchedultTaskManager instance = new SchedultTaskManager();

	/**
	 * KEYΪ��������ʱ�ظ���������ID��VALUEΪ�õ��������ִ����ServerTaskUnit
	 */
	private Map<Integer, SchedultTaskUnit> units;

	private SchedultTaskManager()
	{
	}

	/**
	 * ��ȡ��������Ψһʵ��
	 * 
	 * @return
	 */
	public static SchedultTaskManager getInstance()
	{
		return instance;
	}

	/**
	 * ��ʼ���˹�������Ҫ���ȵ�����������Ϣ,ע��:ֻ�ܳ�ʼ��һ��.
	 * 
	 * @param infos
	 */
	public synchronized void initialize(Collection<? extends ScheduleTaskInfo> infos)
	{
		if (this.units != null)
			throw new IllegalStateException(this.getClass().getSimpleName() + "�ѳ�ʼ��");

		units = new ConcurrentHashMap<Integer, SchedultTaskUnit>();

		for (ScheduleTaskInfo info : infos)
		{
			try
			{
				SchedultTaskUnit unit = new SchedultTaskUnit(info);
				units.put(info.getId(), unit);
			}
			catch (RuntimeException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * �����˹�����, ��ʼ�������
	 */
	public boolean startService()
	{
		if (units == null || units.isEmpty())
			return false;

		for (SchedultTaskUnit unit : units.values())
		{
			int precision = unit.getInfo().getSchedulePrecision();
			if (precision < 1)
				precision = 5;
			TaskCallbackHandler handle = SimpleTaskManager.getService().scheduleAtFixedRate(unit,
					DateTimeConstants.SECOND * 2, DateTimeConstants.MINUTE * precision);
			unit.setHandle(handle);
		}
		return true;
	}

	public long getServerTaskLastExecuteTime(int id)
	{
		try
		{
			return units.get(id).getLastExecuteTime();
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException("ָ�������񲻴��ڣ�" + id);
		}
	}
}
