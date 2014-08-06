package com.doteyplay.core.bhns.eventservice;

import com.doteyplay.core.bhns.ISimpleService;
/**
 * 事件服务接口，提供特定事件的侦听目标列表
 * 
 * @author 
 * 
 */
public interface ISvrEventService extends ISimpleService
{
	public final static int PORTAL_ID = 0;

	public String getEventSource(int eventid);

	public String getListenerList(int eventid);

	public void registerListener(int eventid, int portalid);

	public boolean isActiveEvent(int eventid);

}
