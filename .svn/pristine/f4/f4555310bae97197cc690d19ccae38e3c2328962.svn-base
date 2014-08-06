package com.doteyplay.core.messageagent.net.client;

import com.doteyplay.luna.client.AsynchronismClientManager;
import com.doteyplay.luna.client.ConnectionInfo;

public class MessageAgentClientService
{

	private static final long MAX_TIME_OUT = 5 * 1000;

	private AsynchronismClientManager client;
	private ConnectionInfo info;

	public MessageAgentClientService(String messageAgentIp,
			short messageAgentPort)
	{
		client = new AsynchronismClientManager();
		info = new ConnectionInfo(messageAgentIp, messageAgentPort, 120,
				MAX_TIME_OUT);
		client.initial(info, new MessageAgentClientController()); // 本地服务其实不做什么
	}

	public AsynchronismClientManager getClient()
	{
		return client;
	}
}
