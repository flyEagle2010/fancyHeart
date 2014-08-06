package com.doteyplay.core.bhns;

import com.doteyplay.core.bhns.data.ServiceData;
import com.doteyplay.net.message.AbstractMessage;

/**
 * 简单服务接口
 * 
 * @author 
 * 
 */
public interface ISimpleService
{
	public int getPortalId();
	
	public byte getEndpointId();

	public long getServiceId();

	public boolean isValid();
	
	public boolean isProxy();

	public boolean isSingleton();
	
	public boolean bindServiceData(ServiceData servicedata); 
	
	public void messageReceived(byte[] bytes,long sessionId);
	
	public void sendMessage(AbstractMessage message);
}
