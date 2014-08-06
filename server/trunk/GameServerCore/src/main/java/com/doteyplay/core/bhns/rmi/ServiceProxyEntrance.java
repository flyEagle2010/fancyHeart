package com.doteyplay.core.bhns.rmi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * 服务代理调用入口
 * 
 * @author 
 * 
 */
public class ServiceProxyEntrance implements InvocationHandler
{
	private static Set<String> localMethodSet = new HashSet<String>();

	static
	{
		localMethodSet.add("getPortalId");
		localMethodSet.add("getserviceId");
		localMethodSet.add("isProxy");
		localMethodSet.add("isValid");
		localMethodSet.add("isSingleton");
	}

	private int portalId;
	private ServiceProxy<?> serviceProxy;
	private long serviceId;

	public ServiceProxyEntrance(int portalid, ServiceProxy<?> serviceproxy, long serviceId)
	{
		this.portalId = portalid;
		this.serviceProxy = serviceproxy;
		this.serviceId = serviceId;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		if (localMethodSet.contains(method.getName()))
		{
			if ("isProxy".equals(method.getName()))
			{
				return true;
			}
			else if ("isValid".equals(method.getName()))
			{
				return true;
			}
			else if ("isSingleton".equals(method.getName()))
			{
				return false;
			}
			else if ("getPortalId".equals(method.getName()))
			{
				return portalId;
			}
			else if ("getserviceId".equals(method.getName()))
			{
				return serviceId;
			}
			return null;
		}
		else
		{
			byte internalFlag=ProxyConst.FLAG_SERVICE_INVOKE;
			if ("isActive".equals(method.getName()))
			{
				internalFlag=ProxyConst.FLAG_SERVICE_IS_ACTIVE;
			}
			else if ("activeNotify".equals(method.getName()))
			{
				internalFlag=ProxyConst.FLAG_SERVICE_ACTIVE_NOTIFY;
			}			
			return serviceProxy.doService(portalId,internalFlag,serviceId, method,args);
		}
	}
}
