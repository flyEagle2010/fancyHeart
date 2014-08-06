package com.doteyplay.core.bhns.eventservice;

import java.util.ArrayList;
import java.util.List;
/**
 * 事件配置信息及侦听目标列表
 * 
 * @author 
 * 
 */
public class SvrEventInfo
{
	private int eventId;
	private String source;
	private boolean activeEvent;
	private List<Integer> listeningPortals;
	private String listeningDetail;

	public SvrEventInfo(int eventid)
	{
		this.eventId = eventid;
		listeningPortals = new ArrayList<Integer>();
		listeningDetail = "";
	}

	public int getEventId()
	{
		return eventId;
	}

	public String getSource()
	{
		return source;
	}

	public void setSource(String source)
	{
		this.source = source;
	}

	public boolean isActiveEvent()
	{
		return activeEvent;
	}

	public void setActiveEvent(boolean isActiveEvent)
	{
		this.activeEvent = isActiveEvent;
	}

	public void addListener(int portalid)
	{
		if (portalid <= 0)
			return;

		synchronized (listeningPortals)
		{
			boolean found = false;
			int i = 0;
			while (!found && i < listeningPortals.size())
			{
				if (listeningPortals.get(i).intValue() == portalid)
				{
					found = true;
				}
				i++;
			}
			if (!found)
			{
				listeningPortals.add(portalid);
				if (listeningDetail.length() > 0)
					listeningDetail = listeningDetail + "," + String.valueOf(portalid);
				else
					listeningDetail = String.valueOf(portalid);
			}
		}
	}

	public String getlisteningDetail()
	{
		return listeningDetail;
	}

}
