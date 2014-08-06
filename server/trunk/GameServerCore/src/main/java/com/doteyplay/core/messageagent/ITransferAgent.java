package com.doteyplay.core.messageagent;

import java.util.List;

import com.doteyplay.net.message.AbstractMessage;

/**
 * 传递消息接口 
 * 
 */
public interface ITransferAgent
{

	public void sendMessage(long roleId, AbstractMessage message);

	public void broadcastMessage(List<Long> roleIdLst,
			AbstractMessage message);
}
