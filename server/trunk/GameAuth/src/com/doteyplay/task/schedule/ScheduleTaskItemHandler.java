package com.doteyplay.task.schedule;

import org.apache.log4j.Logger;

import com.doteyplay.task.schedule.base.ScheduleTask;
import com.doteyplay.task.schedule.base.ScheduleTaskInfo;
import com.doteyplay.task.schedule.base.ScheduleTaskParameter;

public class ScheduleTaskItemHandler implements ScheduleTask<ScheduleTaskInfo, ScheduleTaskParameter>
{

	private static final Logger logger = Logger.getLogger(ScheduleTaskItemHandler.class);
	/**
	 * �����������Ŀ������������������ʵ���ʼ����ʱ���������Ŀ�ļ���
	 */
	private AbstractTaskItemContainer itemContainer = new SimpleTaskItemContainer();

	@Override
	public void setParameter(ScheduleTaskParameter parameter)
	{
	}

	@Override
	public void afterTaskExecuted(ScheduleTaskInfo info, long lastUpdate)
	{
	}

	@Override
	public void addTaskItem(AbstractTaskItem item)
	{
		itemContainer.addItem(item);
	}

	@Override
	public void run()
	{
		if (logger.isInfoEnabled())
			logger.info("��ʼִ�и���������ȡ���������");
		try
		{
			boolean flag = itemContainer.executeTask();
			if (flag)
			{
				logger.error("�������гɹ�");
			}
			else
			{
				logger.error("�������з������, �����б�Ϊ�ա�");
			}
		}
		catch (Exception e)
		{
			logger.error("ִ�и���������ȳ����쳣���쳣��Ϣ��" + e.getMessage(), e);
		}
		if (logger.isInfoEnabled())
			logger.info("�������������ϡ���������");
	}

}
