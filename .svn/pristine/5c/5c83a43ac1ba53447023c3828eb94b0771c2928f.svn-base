package com.doteyplay.core.bhns.gateway;

import java.util.List;

import com.doteyplay.core.bhns.ISimpleService;
import com.doteyplay.game.BOConst;
import com.doteyplay.net.message.AbstractMessage;

public interface IGateWayService extends ISimpleService
{
	public final static int PORTAL_ID = 2;

	public void kick(long userId,boolean worldLogout);
	
	public void kickAll();
	
	public void broadcastMessage(List<Long> serviceIds,AbstractMessage message);
	
	public void sendMessage(byte[] messageBytes);
	
	public void sendMessage(byte[] messageBytes,long sessionId);
}