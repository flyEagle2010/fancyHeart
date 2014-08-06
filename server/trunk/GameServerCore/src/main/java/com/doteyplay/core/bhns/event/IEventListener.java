package com.doteyplay.core.bhns.event;

/**
 * 事件侦听接口
 * 
 * @author 
 * 
 */
public interface IEventListener
{
	public void triggerEvent(int eventid, String methodSignature, Object[] args);
}
