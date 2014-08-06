package com.doteyplay.core.server.servertaskimpl;

import org.apache.log4j.Logger;

import com.doteyplay.core.configloader.TaskHandlerRegistry;
import com.doteyplay.core.server.task.TaskCallbackHandler;
import com.doteyplay.core.util.servertask.ServerTask;
import com.doteyplay.core.util.servertask.ServerTaskInfo;
import com.doteyplay.core.util.servertask.IServerTaskDBManager;

/**
 * 通用计划任务执行器
 * 
 */
public class CommonUpdateTaskImpl implements
		ServerTask<ServerTaskInfo, IServerTaskDBManager> {

	private static final Logger logger = Logger
			.getLogger(CommonUpdateTaskImpl.class);
	/**
	 * 所有任务的条目容器对象，在容器具体实例初始化的时候完成了条目的加载
	 */
	private AbstractTaskItemContainer itemContainer = new CommonItemContainer();


	@Override
	public void afterTaskExecuted(ServerTaskInfo info, long nextUpdate) {
		info.setNextExecuteTime(nextUpdate);
		TaskHandlerRegistry.getInstance().getDbManager().updateTaskInfo(info);
	}

	@Override
	public void addTaskItem(AbstractTaskItem item) {
		itemContainer.addItem(item);		
	}

	@Override
	public void run() {
		if (logger.isInfoEnabled())
			logger.info("开始执行更新任务调度。。。。。");
		try {
			boolean flag = itemContainer.executeTask();
			if (flag) {
				logger.error("任务运行成功");
			} else {
				logger.error("任务运行发生错误, 任务列表为空。");
			}
		} catch (Exception e) {
			logger.error("执行更新任务调度出现异常，异常信息：" + e.getMessage(), e);
		}
		if (logger.isInfoEnabled())
			logger.info("更新任务调度完毕。。。。。");
	}

	@Override
	public boolean setOpen(String itemClassName, boolean open){
		return itemContainer.setOpen(itemClassName, open);
	}	
	
}
