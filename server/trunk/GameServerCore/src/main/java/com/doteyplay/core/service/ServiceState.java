/**
 * 
 */
package com.doteyplay.core.service;

/**
 * @author dell
 *
 */
public class ServiceState {
	/**
	 * 未初始化
	 */
	public static final byte SERVICE_STATE_NONE=0;
	/**
	 * 已初始化
	 */
	public static final byte SERVICE_STATE_INITED=1;
	/**
	 * 已启动
	 */
	public static final byte SERVICE_STATE_STARTED=2;
	/**
	 * 已停止
	 */
	public static final byte SERVICE_STATE_STOPED=3;
}
