/**
 * 
 */
package com.doteyplay.core.service;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局服务管理器
 *
 */
public class ServiceManager {

	private static ServiceManager instance;
	
	public static final String SERVICE_ID_ROOT="SERVICE_ROOT";
	private final Map<String, IService> serviceMap=new HashMap<String, IService>();
	
	public static final ServiceManager getInstance()
	{
		if(instance==null)
			instance=new ServiceManager();
		return instance;
	}

	public final void registerService(String serviceId, IService service)
	{
		serviceMap.put(serviceId, service);
	}
	
	public final IService getService(String serviceId)
	{
		return serviceMap.get(serviceId);
	}
	
	public final IService removeService(String serviceId)
	{
		return serviceMap.remove(serviceId);
	}
}
