package com.doteyplay.core.bhns.eventservice;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.doteyplay.core.bhns.AbstractSimpleService;
import com.doteyplay.core.bhns.event.IServiceEvent;
import com.doteyplay.core.util.xml.IParamterSupport;
import com.doteyplay.core.util.xml.ISimpleParamters;
import com.doteyplay.core.util.xml.XmlFileSupport;
/**
 * 事件服务,，提供特定事件的侦听目标列表下载
 * 
 * @author 
 * 
 */
public class SvrEventService extends AbstractSimpleService<ISvrEventService> implements ISvrEventService,
		IParamterSupport
{
	private static final Logger logger = Logger.getLogger(SvrEventService.class);
	private String EVENT_CONFIG_FILE = "/event_service.xml";
	private Map<Integer, SvrEventInfo> eventDefinitions;

	public SvrEventService()
	{
		eventDefinitions = new ConcurrentHashMap<Integer, SvrEventInfo>();		
	}
	
	@Override
	public int getPortalId()
	{
		return ISvrEventService.PORTAL_ID;
	}

	public void initialize(String eventcfgfile)
	{
		if (eventcfgfile != null && eventcfgfile.length() > 0)
			EVENT_CONFIG_FILE = eventcfgfile;
		XmlFileSupport.parseXmlFromResource(EVENT_CONFIG_FILE, this);
	}

	@Override
	public boolean isSingleton()
	{
		return true;
	}

	@Override
	public void putParamter(ISimpleParamters paramter)
	{
		if ("EVENT".equals(paramter.getDataName()))
		{
			try
			{
				String tmpSource = paramter.getValue("SOURCE");
				if (tmpSource == null || tmpSource.length() <= 0)
				{
					logger.error("event source is null,source=" + tmpSource);
					return;
				}

				Class<?> tmpClass = Class.forName(tmpSource);
				if (tmpClass == null || !IServiceEvent.class.isAssignableFrom(tmpClass))
				{
					logger.error("event source is invalid,source=" + tmpSource);
					return;
				}

				Field tmpField = tmpClass.getField("EVENT_ID");
				if (tmpField == null
						|| (!int.class.isAssignableFrom(tmpField.getType()) && !Integer.class
								.isAssignableFrom(tmpField.getType())))
				{
					logger.error("event need define a static integer field named EVENT_ID,source="
							+ tmpSource);
					return;
				}

				int tmpEventId = tmpField.getInt(null);
				if (tmpEventId <= 0)
				{
					logger.error("event EVENT_ID field must >0,source=" + tmpSource);
					return;
				}

				SvrEventInfo tmpInfo = new SvrEventInfo(tmpEventId);
				tmpInfo.setSource(tmpSource);
				eventDefinitions.put(tmpEventId, tmpInfo);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onComplete()
	{

	}

	@Override
	public String getEventSource(int eventid)
	{
		if (eventid <= 0)
			return null;

		SvrEventInfo tmpSvrEventInfo = eventDefinitions.get(eventid);
		if (tmpSvrEventInfo != null)
			return tmpSvrEventInfo.getSource();
		else
			return null;
	}

	@Override
	public boolean isActiveEvent(int eventid)
	{
		if (eventid <= 0)
			return false;

		SvrEventInfo tmpSvrEventInfo = eventDefinitions.get(eventid);
		if (tmpSvrEventInfo != null)
			return tmpSvrEventInfo.isActiveEvent();
		else
			return false;
	}

	@Override
	public String getListenerList(int eventid)
	{
		if (eventid <= 0)
			return "";

		SvrEventInfo tmpSvrEventInfo = eventDefinitions.get(eventid);
		if (tmpSvrEventInfo != null)
			return tmpSvrEventInfo.getlisteningDetail();
		else
			return "";
	}

	@Override
	public void registerListener(int eventid, int portalid)
	{
		if (eventid <= 0)
			return;

		SvrEventInfo tmpSvrEventInfo = eventDefinitions.get(eventid);
		if (tmpSvrEventInfo != null)
			tmpSvrEventInfo.addListener(portalid);
	}

	@Override
	public void messageReceived(byte[] bytes,long sessionId)
	{
		throw new UnsupportedOperationException();
	}
}
