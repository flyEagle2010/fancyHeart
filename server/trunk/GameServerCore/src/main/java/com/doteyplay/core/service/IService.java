/**
 * 
 */
package com.doteyplay.core.service;


/**
 * 服务接口
 *
 */
public interface IService {
	String getserviceId();
	
	boolean initialize();
	
	boolean startService();
	boolean stopService();

	void release();
	
	byte getState();
	boolean isRunning();
}
