/**
 * 
 */
package com.doteyplay.core.service;


/**
 * 抽象服务基类
 *
 */
public abstract class AbstractService implements IService {
	
	private final String serviceId;
	protected byte serviceState;
	
	public AbstractService(String serviceId) {
		this.serviceId = serviceId;
	}

	/* (non-Javadoc)
	 * @see com.doteyplay.core.service.IService#getserviceId()
	 */
	@Override
	public final String getserviceId() {
		return serviceId;
	}

	@Override
	public boolean initialize() {
		ServiceManager.getInstance().registerService(serviceId, this);
		return true;
	}
	@Override
	public void release() {
		//从全局服务管理器移除自己
		ServiceManager.getInstance().removeService(serviceId);
	}
	
	@Override
	public boolean startService() {
		return true;
	}
	
	@Override
	public boolean stopService() {
		return true;
	}
	
	@Override
	public final byte getState() {
		return serviceState;
	}

	@Override
	public final boolean isRunning() {
		return serviceState==ServiceState.SERVICE_STATE_STARTED;
	}
}
