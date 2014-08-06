package com.doteyplay.task.schedule.base;

import com.doteyplay.task.base.Task;
import com.doteyplay.task.schedule.AbstractTaskItem;

/**
 * ��������ʱ�ظ���������
 * 
 * @author Leo Yang (leo8002@sina.com) 2009-7-15����06:06:37
 * 
 */
public interface ScheduleTask<T extends ScheduleTaskInfo, P extends ScheduleTaskParameter> extends Task
{
	/**
	 * ���ù����Ĳ������
	 * 
	 * @param parameter
	 */
	public void setParameter(P parameter);

	/**
	 * �־û�ָ�����������ִ��ʱ��
	 * 
	 * @param info
	 *            ������Ϣ
	 * @param lastUpdate
	 *            ����ִ��ʱ��
	 */
	public void afterTaskExecuted(T info, long lastUpdate);

	/**
	 * ���Ӷ���ִ����
	 */
	public void addTaskItem(AbstractTaskItem item);
}
