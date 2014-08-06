package com.doteyplay.core.server.service;


/**
 * GameServer内包含的常用的服务对象
 *
 */
public interface IServerService {
	/**
	 * 准备数据
	 * @return
	 */
	public void onReady() throws IServerServiceException;
	/**
	 * 执行启动
	 * @return
	 */
	public void onStart()  throws IServerServiceException;
	/**
	 * 执行停止操作
	 * @return
	 */
	public void onDown()  throws IServerServiceException;
}
