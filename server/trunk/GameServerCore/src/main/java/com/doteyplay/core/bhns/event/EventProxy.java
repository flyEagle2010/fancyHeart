package com.doteyplay.core.bhns.event;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.core.bhns.IMethodCollection;
import com.doteyplay.core.bhns.ServiceInfo;
import com.doteyplay.core.bhns.portal.SimpleMethodCollection;
import com.doteyplay.core.bhns.source.IEndpointSource;

/**
 * 事件源代理
 * 
 * @author 
 * 
 */
public class EventProxy<T extends IServiceEvent>
{
	private int eventId;
	private Constructor<?> boProxyConstructor;
	private IMethodCollection methodMap;
	private T eventServiceImpl;
	private List<ServiceInfo> eventListenChannels;// 需要广播的渠道
	private Set<Integer> eventListenChannelIds;
	
	private boolean isSingleton;
	private Object key; // 如果是非单例的则需要使用key

	@SuppressWarnings("unchecked")
	public EventProxy(int eventid, Class<T> interfaceClass, boolean issingleton ,Object key)
	{
		this.eventId = eventid;
		this.isSingleton = issingleton;
		this.key = key;
		eventListenChannels = new CopyOnWriteArrayList<ServiceInfo>();
		eventListenChannelIds = new CopyOnWriteArraySet<Integer>();
		methodMap = new SimpleMethodCollection(interfaceClass);
		Class<?> clazzProxy = Proxy.getProxyClass(interfaceClass.getClassLoader(), interfaceClass);
		try
		{
			boProxyConstructor = clazzProxy.getConstructor(InvocationHandler.class);
			eventServiceImpl = (T) boProxyConstructor.newInstance(new EventProxyEntrance(this));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void regListener(ServiceInfo svinfo)
	{
		if (svinfo == null || svinfo.getPortalId() <= 0)
			return;

		synchronized (eventListenChannels)
		{
			
			if (!eventListenChannelIds.contains(svinfo.getPortalId()))
			{
				eventListenChannelIds.add(svinfo.getPortalId());
				eventListenChannels.add(svinfo);
			}
		}
	}

	public Object doService(Method method, Object[] args)
	{
		if (method != null)
		{
			String tmpMethodSignature = this.methodMap.getMethodSignature(method);
			if(isSingleton)
			{
				for (ServiceInfo tmpServiceInfo : eventListenChannels)
				{
					if (tmpServiceInfo.isAvailable())
					{
						tmpServiceInfo.triggerEvent(eventId, tmpMethodSignature, args);
					}
				}
			}
			else
			{
				Map<Integer, List<IEndpointSource>> sources = BOServiceManager.getLocalServiceSources();
				for(List<IEndpointSource> tmpLst : sources.values())
				{
					if(tmpLst != null)
					{
						for(IEndpointSource source : tmpLst)
							source.triggerMultipleEvent(eventId, tmpMethodSignature,key, args);
					}	
				}
			}
		}
		return null;
	}

	public int getEventId()
	{
		return eventId;
	}

	public T getEventImpl()
	{
		return eventServiceImpl;
	}

}
