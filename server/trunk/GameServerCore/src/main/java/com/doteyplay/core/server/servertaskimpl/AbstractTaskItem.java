package com.doteyplay.core.server.servertaskimpl;

/**
 * 抽象的任务活动条目
 * 
 */
public abstract class AbstractTaskItem {

	/**
	 * 任务的名称
	 */
	protected String name;
	
	/**
	 * 该任务项是否关闭
	 */
	protected boolean open = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOpen(boolean open){
		this.open = open;
	}
	
	public boolean isOpen(){
		return open;
	}
	
	/**
	 * 公共调用的方法
	 */
	public abstract void invoke();
}
