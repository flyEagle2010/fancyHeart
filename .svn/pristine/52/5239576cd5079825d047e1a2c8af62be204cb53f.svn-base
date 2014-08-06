package com.doteyplay.game.service.bo.gateway;

import java.util.List;

import org.apache.mina.core.session.IoSession;

import com.doteyplay.core.bhns.AbstractSimpleService;
import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.game.service.bo.virtualworld.IVirtualWorldService;
import com.doteyplay.game.service.runtime.GlobalSessionCache;
import com.doteyplay.net.message.AbstractMessage;
import com.doteyplay.net.message.DefaultMessage;

public class GameGateWayService extends
		AbstractSimpleService<IGameGateWayService> implements
		IGameGateWayService
{

	@Override
	public void kick(long userId,boolean worldLogout)
	{
	}

	@Override
	public void kickAll()
	{
	}

	@Override
	public int getPortalId()
	{
		return PORTAL_ID;
	}

	@Override
	public void loginNotify(long userId, long sessionId)
	{
		IoSession session = GlobalSessionCache.getInstance()
				.getSessionBySessionId(sessionId);

		if(session != null && session.isConnected())
			GlobalSessionCache.getInstance().add(userId, session,getServiceId());
		else
		{
			//由于登陆还没有完成就已经关掉程序下线了，所以要补一下世界服务器下线通知
			IVirtualWorldService virtualWorldService = BOServiceManager
					.findDefaultService(IVirtualWorldService.PORTAL_ID);
			virtualWorldService.doLogout(getServiceId());
		}
	}

//	public void messageReceived(byte[] messageBytes, long sessionId)
//	{
//		throw new UnsupportedOperationException("gateway service unsuppot message recv");
//	}
	
	public void sendMessage(byte[] messageBytes)
	{
		DefaultMessage message = new DefaultMessage();
		message.setData(messageBytes);
		
		GlobalSessionCache.getInstance().sendMessageByRoleId(message, getServiceId());
	}

	@Override
	public void broadcastMessage(List<Long> roleIds, AbstractMessage message)
	{
	}

	@Override
	public void sendMessage(byte[] messageBytes, long sessionId)
	{
		
	}

}
