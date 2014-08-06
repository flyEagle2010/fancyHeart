package com.doteyplay.core.bhns.event;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.doteyplay.core.bhns.eventservice.ISvrEventService;
import com.doteyplay.core.bhns.portal.AbstractLocalPortal;

/**
 * 事件侦听器，放置在LocalPortal中
 * 
 * @author 
 * 
 */
public class LocalSingletonEventListener implements IEventListener
{
	private AbstractLocalPortal<?> _localPortal;
	private Map<Integer, EventTrigger> _eventTriggerMap;

	public LocalSingletonEventListener(AbstractLocalPortal<?> localportal)
	{
		this._localPortal = localportal;
		this._eventTriggerMap = new ConcurrentHashMap<Integer, EventTrigger>();
	}

	public void triggerEvent(int eventid, String methodSignature, Object[] args)
	{
		EventTrigger tmpTrigger = _eventTriggerMap.get(eventid);
		if (tmpTrigger == null)
		{
			return;
		}

		tmpTrigger.invokeMethod(methodSignature, args);
	}

	public void regTrigger(IServiceEvent eventImpl)
	{
		if (eventImpl == null)
			return;

		int tmpEventId = 0;
		try
		{
			Field tmpField = eventImpl.getClass().getField("EVENT_ID");
			if (tmpField != null)
				tmpEventId = tmpField.getInt(null);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		if (tmpEventId > 0 && !_eventTriggerMap.containsKey(tmpEventId))
		{
			EventTrigger tmpTrigger = new EventTrigger(eventImpl);
			this._eventTriggerMap.put(tmpEventId, tmpTrigger);

			ISvrEventService tmpEventService = (ISvrEventService) _localPortal.getDefaultService(
					ISvrEventService.PORTAL_ID, (byte) 0);
			if (tmpEventService != null)
			{
				tmpEventService.registerListener(tmpEventId, _localPortal.getPortalId());
			}
		}
	}
}
