package com.doteyplay.core.bhns.event;

/**
 * 事件源接口
 * 
 * @author 
 * 
 */
public interface IEventSource
{
	public <T extends IServiceEvent> T createSingletonEvent(int eventId);
	
	public <T extends IMultipleServiceEvent> T createMultipleEvent(int eventId,Object key);
}
