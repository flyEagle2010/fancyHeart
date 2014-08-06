package com.doteyplay.core.bhns.dummy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * 虚代理调用入口
 * 
 */
public class DummyServiceProxyEntrance implements InvocationHandler
{
	private static Set<String> localMethodSet = new HashSet<String>();

	static
	{
		localMethodSet.add("getPortalId");
		localMethodSet.add("getserviceId");
		localMethodSet.add("isValid");
		localMethodSet.add("isProxy");
		localMethodSet.add("isSingleton");
	}

	private int portalId;
	private boolean isValid;
	private DummyServiceProxy<?> serviceProxy;

	public DummyServiceProxyEntrance(int portalid, DummyServiceProxy<?> serviceproxy, boolean isvalid)
	{
		this.portalId = portalid;
		this.isValid = isvalid;
		this.serviceProxy = serviceproxy;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		if (localMethodSet.contains(method.getName()))
		{
			if ("isProxy".equals(method.getName()))
			{
				return false;
			}
			else if ("isValid".equals(method.getName()))
			{
				return isValid;
			}
			else if ("isSingleton".equals(method.getName()))
			{
				return true;
			}
			else if ("getPortalId".equals(method.getName()))
			{
				return portalId;
			}
			else if ("getserviceId".equals(method.getName()))
			{
				return 0;
			}
			return null;
		}
		else
		{
			return serviceProxy.invokeService(method, args);
		}
	}
}
