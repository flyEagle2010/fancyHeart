package com.doteyplay.core.bhns.event;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.core.bhns.ServiceInfo;
import com.doteyplay.core.bhns.eventservice.ISvrEventService;

/**
 * 事件源，放置在LocalPortal中
 * 
 * @author 
 * 
 */
public class LocalEventSource
{
	private final static long CHECK_LISTENER_INTERVAL = 180000l;

	private Map<Integer, EventProxy<?>> _eventProxyMap;
	private Map<Integer,Long> nextListernerChackStampMap;

	private LocalEventSource()
	{
		this._eventProxyMap = new ConcurrentHashMap<Integer, EventProxy<?>>();
		this.nextListernerChackStampMap = new HashMap<Integer, Long>();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T extends IServiceEvent> T create_event(int eventid,boolean issingleton, Object key)
	{
		if (eventid <= 0)
			return null;

		EventProxy<?> tmpProxy = null;
		if(issingleton)
		{
			tmpProxy = _eventProxyMap.get(eventid);
			if (tmpProxy == null)
			{
				ISvrEventService tmpEventService = BOServiceManager.findDefaultService(
						ISvrEventService.PORTAL_ID, (byte) 0);
				if (tmpEventService == null)
				{
					return null;
				}

				String tmpClassSource = tmpEventService.getEventSource(eventid);
				try
				{
					Class<?> tmpEventClass = Class.forName(tmpClassSource);
					if (tmpEventClass == null || !IServiceEvent.class.isAssignableFrom(tmpEventClass))
					{
						return null;
					}

					tmpProxy = new EventProxy(eventid, tmpEventClass,issingleton,key);
					_eventProxyMap.put(eventid, tmpProxy);

				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			ISvrEventService tmpEventService = BOServiceManager.findDefaultService(
					ISvrEventService.PORTAL_ID, (byte) 0);
			if (tmpEventService == null)
			{
				return null;
			}

			String tmpClassSource = tmpEventService.getEventSource(eventid);
			try
			{
				Class<?> tmpEventClass = Class.forName(tmpClassSource);
				if (tmpEventClass == null || !IServiceEvent.class.isAssignableFrom(tmpEventClass))
				{
					return null;
				}

				tmpProxy = new EventProxy(eventid, tmpEventClass,issingleton,key);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		Long nextListernerChackStamp = nextListernerChackStampMap.get(eventid);
		if(nextListernerChackStamp == null)
			nextListernerChackStamp = 0l;
		
		if (issingleton && tmpProxy != null && System.currentTimeMillis() > nextListernerChackStamp)
		{
			ISvrEventService tmpEventService = BOServiceManager.findDefaultService(
					ISvrEventService.PORTAL_ID, (byte) 0);
			if (tmpEventService == null)
			{
				return null;
			}

			String tmpListeners = tmpEventService.getListenerList(eventid);
			if (tmpListeners != null)
			{
				String[] tmpListenerArray = tmpListeners.split(",");
				int tmpPortalId;
				ServiceInfo tmpServiceInfo;
				for (int i = 0; i < tmpListenerArray.length; i++)
				{
					try
					{
						tmpPortalId = Integer.valueOf(tmpListenerArray[i]);
					} catch (Exception e)
					{
						tmpPortalId = -1;
						e.printStackTrace();
					}
					tmpServiceInfo = BOServiceManager.getServiceInfo(tmpPortalId);
					if (tmpServiceInfo != null)
						tmpProxy.regListener(tmpServiceInfo);
				}
			}
			nextListernerChackStampMap.put(eventid, System.currentTimeMillis() + CHECK_LISTENER_INTERVAL);
		}
		if (tmpProxy == null)
			return null;
		else
			return (T) tmpProxy.getEventImpl();
	}

	//单例
	private static final LocalEventSource instance = new LocalEventSource();
	
	@SuppressWarnings("unchecked")
	public static <T extends IServiceEvent> T createEvent(int eventid, boolean issingleton,Object key)
	{
		return (T)instance.create_event(eventid, issingleton,key);
	}
	
}
