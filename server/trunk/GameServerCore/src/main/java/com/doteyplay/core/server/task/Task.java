package com.doteyplay.core.server.task;

/**
 * 所有的计划任务必须实现该接口
 */
public interface Task {
	/**
	 * 执行任务, 注意:此方法不能抛出任何异常
	 */
	void run();
}
