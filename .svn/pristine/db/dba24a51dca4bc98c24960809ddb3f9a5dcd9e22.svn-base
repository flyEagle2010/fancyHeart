package com.doteyplay.task.schedule.base;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.doteyplay.task.base.Task;
import com.doteyplay.task.base.TaskCallbackHandler;
import com.doteyplay.task.schedule.AbstractTaskItem;
import com.doteyplay.utils.DateTimeConstants;

/**
 * ServerTask�ĵ��ȵ�����ִ�е�Ԫ
 * 
 * @author Leo Yang (leo8002@sina.com) 2009-7-15����03:39:30
 * 
 */
class SchedultTaskUnit implements Task
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SchedultTaskUnit.class);

	private final ScheduleTaskInfo info;

	private final ScheduleTaskType taskType;

	private final CheckTime checkTime;

	private final ScheduleTask task;

	private final ScheduleTaskParameter parameter;

	private long lastUpdate;

	private TaskCallbackHandler handle = null;

	private List<AbstractTaskItem> items;

	@SuppressWarnings("unchecked")
	public SchedultTaskUnit(ScheduleTaskInfo info)
	{
		// TODO LEO: more check
		this.info = info;
		this.taskType = info.getServerTaskType();
		this.checkTime = new CheckTime(info.getMonth(), info.getDay(), info.getWeek(), info.getHour(), info
				.getMinute());

		this.lastUpdate = info.getLastExecuteTime();
		if (this.lastUpdate <= 0)
			this.setupLastUpdateTime();

		try
		{
			Class cls = Class.forName(info.getTaskClassName());
			this.task = (ScheduleTask) cls.newInstance();
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException(info.getName() + "��ʼ������ִ����ʧ��", e);
		}

		if (info.getParameterClassName() == null || info.getParameterClassName().length() == 0)
		{
			this.parameter = null;
		}
		else
		{
			try
			{
				Class c = Class.forName(info.getParameterClassName());
				this.parameter = (ScheduleTaskParameter) c.newInstance();
				this.task.setParameter(parameter);
			}
			catch (Exception e)
			{
				throw new IllegalArgumentException(info.getName() + "��ʼ�����������ʧ��", e);
			}
		}

		/**
		 * ���ؾ�����Ŀ
		 */
		if (info.getItemClassName() != null && info.getItemClassName().length > 0)
		{

			try
			{
				for (String className : info.getItemClassName())
				{
					// logger.error(className);
					Class c = Class.forName(className);
					task.addTaskItem((AbstractTaskItem) c.newInstance());
				}
			}
			catch (Exception e)
			{
				throw new IllegalArgumentException(info.getName() + "��ʼ�����������ʧ��", e);
			}

		}

		if (logger.isInfoEnabled())
			logger.info("��������ʱ��������[" + info.getName() + "]�ϴε���ʱ��:"
					+ DateTimeConstants.getTimeString(this.lastUpdate));
	}

	// �������һ�θ��µ�ʱ��
	private void setupLastUpdateTime()
	{
		long now = System.currentTimeMillis();
		Calendar cal = this.checkTime.setupFor(now);
		this.lastUpdate = cal.getTimeInMillis();
		if (this.lastUpdate > now)
		{
			this.lastUpdate -= taskType.getPeriodic();
		}
	}

	@Override
	public void run()
	{
		final Calendar now = Calendar.getInstance();
		final long nowTime = now.getTimeInMillis();
		// �ϴθ������������ʱ��
		final long delta = nowTime - lastUpdate;
		// ���ϴθ���������ʱ�����������ٴθ��µ�Ҫ��
		if (delta >= taskType.getPeriodic())
			doTask();
	}

	// ִ��������������
	@SuppressWarnings("unchecked")
	private void doTask()
	{
		try
		{
			task.run();

			// �������һ�θ��µ�ʱ��
			this.setupLastUpdateTime();

			// TODO LEO:Ҳ����Ҫ���ǵ������ڵ�����������洢������ʱ���ʱ��
			// ���˸���ʱ����־û�
			task.afterTaskExecuted(info, this.lastUpdate);

			if (logger.isInfoEnabled())
			{
				logger.info("���ȷ�������ʱ�������:[" + info.getName() + "], ������ʱ������Ϊ:"
						+ DateTimeConstants.getTimeString(this.lastUpdate));
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * @param handle
	 *            the handle to set
	 */
	void setHandle(TaskCallbackHandler handle)
	{
		this.handle = handle;
	}

	/**
	 * @return the info
	 */
	ScheduleTaskInfo getInfo()
	{
		return info;
	}

	long getLastExecuteTime()
	{
		return lastUpdate;
	}
}
