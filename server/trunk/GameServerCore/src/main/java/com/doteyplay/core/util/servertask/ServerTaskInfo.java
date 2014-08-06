package com.doteyplay.core.util.servertask;


/**
 * 服务器定时重复调度任务配置信息
 * 
 *
 */
public interface ServerTaskInfo
{
	/**
	 * 获取ID
	 * @return
	 */
	public int getId();
	
	/**
	 * 获取调度任务名称
	 * @return
	 */
	public String getName();
	
	/**
	 * 获取任务调度周期类型, 对应ServerTaskType枚举类型.
	 * @return
	 * @see com.doteyplay.core.util.servertask.ServerTaskType
	 */
	public ServerTaskType getServerTaskType();
	
	/**
	 * 获取任务调度限定月份(1-12), 无限定则返回-1
	 * @return
	 */
	public int getMonth();

	/**
	 * 获取任务调度限定日期(1-31), 无限定则返回-1
	 * @return
	 */
	public int getDay();

	/**
	 * 获取任务调度限定星期(1-7), 无限定则返回-1
	 * @return
	 */
	public int getWeek();

	/**
	 * 获取任务调度限定的时间(0-23)
	 * @return
	 */
	public int getHour();

	/**
	 * 获取任务调度限定的分钟(0-59)
	 * @return
	 */
	public int getMinute();
	/**
	 * 获取此任务最后一次执行的时间戳
	 * @return 新增的调度任务如果希望服务器一启动就立即执行一次时返回1, 
	 * 否则返回0表示不需要立即执行. 已经正常调度过的返回存储的最后执行的时间戳.
	 */
	public long getNextExecuteTime();
	
	
	public void setNextExecuteTime(long nextExecuteTime);
	
	/**
	 * 获取此任务调度执行的时间精度,单位:分钟; 如果返回值小于1, 则取默认值5.
	 * @return
	 */
	public int getSchedulePrecision();
	
	/**
	 * 获取需要启动项类名
	 */
	public String[] getItemClassName();
}
