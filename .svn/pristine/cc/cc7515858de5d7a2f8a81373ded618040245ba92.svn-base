package com.doteyplay.core.bhns.event;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 事件侦听器，放置在LocalPortal中
 * 
 * @author 
 * 
 */
public class LocalMultipleEventListener implements IMultipleEventListener
{
	/**
	 * key : eventImpl.getClass().getField("EVENT_ID")  value :Map<Object,EventTrigger>
	 * 
	 * Object : eventImpl.getListenerKey()   value : EventTrigger
	 * 
	 * 拥有事件Id. 
	 */
	private Map<Integer, Map<Object,EventTrigger>> _eventTriggerMap;

	public LocalMultipleEventListener()
	{
		this._eventTriggerMap = new ConcurrentHashMap<Integer, Map<Object,EventTrigger>>();
	}

	public void triggerMultipleEvent(int eventid, String methodSignature,Object key, Object[] args)
	{
		Map<Object,EventTrigger> triggers = _eventTriggerMap.get(eventid);
		if(triggers != null)
		{
			EventTrigger tmpTrigger = triggers.get(key);
			if (tmpTrigger == null)
			{
				return;
			}
			tmpTrigger.invokeMethod(methodSignature, args);
		}
	}

	public void regTrigger(IMultipleServiceEvent eventImpl)
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

		if (tmpEventId > 0)
		{
			Map<Object,EventTrigger> tmpMap = this._eventTriggerMap.get(tmpEventId);
			if(tmpMap == null)
			{
				tmpMap = new ConcurrentHashMap<Object, EventTrigger>();
				this._eventTriggerMap.put(tmpEventId, tmpMap);
			}
			
			EventTrigger tmpTrigger = new EventTrigger(eventImpl);
			tmpMap.put(eventImpl.getListenerKey(), tmpTrigger);
		}
	}
	
	public void rmMultipleTrigger(int eventid,Object key)
	{
		Map<Object,EventTrigger> tmpMap = this._eventTriggerMap.get(eventid);
		if(tmpMap == null)
			return;
		tmpMap.remove(key);
	}
}
