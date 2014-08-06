package com.doteyplay.task.schedule.base;

/**
 * ��������ʱ�ظ���������������Ϣ
 * 
 * @author Leo Yang (leo8002@sina.com) 2009-7-15����05:55:51
 * 
 */
public interface ScheduleTaskInfo
{
	/**
	 * ��ȡID
	 * 
	 * @return
	 */
	public int getId();

	/**
	 * ��ȡ�����������
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * ��ȡ���������������, ��ӦServerTaskTypeö������.
	 * 
	 * @return
	 * @see com.t4game.mmorpg.core.util.servertask.ScheduleTaskType
	 */
	public ScheduleTaskType getServerTaskType();

	/**
	 * ��ȡ��������޶��·�(1-12), ���޶��򷵻�-1
	 * 
	 * @return
	 */
	public int getMonth();

	/**
	 * ��ȡ��������޶�����(1-31), ���޶��򷵻�-1
	 * 
	 * @return
	 */
	public int getDay();

	/**
	 * ��ȡ��������޶�����(1-7), ���޶��򷵻�-1
	 * 
	 * @return
	 */
	public int getWeek();

	/**
	 * ��ȡ��������޶���ʱ��(0-23)
	 * 
	 * @return
	 */
	public int getHour();

	/**
	 * ��ȡ��������޶��ķ���(0-59)
	 * 
	 * @return
	 */
	public int getMinute();

	/**
	 * ִ�����������, �������ʵ��com.t4game.mmorpg.task.Task.ServerTask, ���ұ������޲����췽��.
	 * 
	 * @return
	 */
	public String getTaskClassName();

	/**
	 * ��ȡ���������һ��ִ�е�ʱ���
	 * 
	 * @return �����ĵ����������ϣ�������һ����������ִ��һ��ʱ����1, ���򷵻�0��ʾ����Ҫ����ִ��.
	 *         �Ѿ�����ȹ�ķ��ش洢�����ִ�е�ʱ���.
	 */
	public long getLastExecuteTime();

	/**
	 * ��ȡ���������ִ�е�ʱ�侫��,��λ:����; ����ֵС��1, ��ȡĬ��ֵ5.
	 * 
	 * @return
	 */
	public int getSchedulePrecision();

	/**
	 * ��ȡ�����Ĳ�������, �����������޲����췽��
	 * 
	 * @return
	 */
	public String getParameterClassName();

	/**
	 * ��ȡ��Ҫ����������
	 */
	public String[] getItemClassName();
}
