package com.doteyplay.core.server.servertaskimpl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

/**
 * 需要执行的任务条目的容器
 */
public abstract class AbstractTaskItemContainer {
	private static final Logger logger=Logger.getLogger(AbstractTaskItemContainer.class);
	/**
	 * 日常任务条目的容器
	 */
	protected List<AbstractTaskItem> taskItemList=new CopyOnWriteArrayList<AbstractTaskItem>();
	
	public AbstractTaskItemContainer(){
		init();
	}
	/**
	 * 初始化的方法
	 */
	public abstract void init();
	/**
	 * 执行任务
	 * @return
	 */
	public boolean executeTask(){
        logger.error("执行定时任务的条目");
		if(taskItemList!=null&&taskItemList.size()>0){
			for(AbstractTaskItem item:taskItemList){
                logger.error(item.getName()+"。。。。。开始执行"+(new Date()));
                try {
                	if(item.isOpen())
                		item.invoke();
                	else
                		logger.error(item.getName()+"没有开放");                	
                } catch (Exception e){
					logger.error("执行定时任务条目异常:" + (new Date()), e);
				}
			}
			logger.error("执行完毕"+(new Date()));
			return true;
		}
		else{
			return false;
		}
	}
	
	public void addItem(AbstractTaskItem item) {
		taskItemList.add(item);
	}
	
	public boolean setOpen(String itemClassName, boolean open){
		for(AbstractTaskItem item:taskItemList){
			if(item.getClass().getSimpleName().equals(itemClassName)){
				item.setOpen(open);
				return true;
			}
		}
		return false;
	}
}
