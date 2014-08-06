package com.doteyplay.core.bhns.net;

import java.util.HashMap;
import java.util.Map;

import com.doteyplay.luna.client.ConnectionInfo;
import com.doteyplay.luna.client.SynchronicClientManager;

/**
 * Socket管理,用于创建并管理Scoket连接
 * 
 * @author 
 * 
 */
public class ClientSocketFactory
{

	private static final long MAX_TIME_OUT = 5 * 1000;

	private static Map<String, SynchronicClientManager> synchronicMap = new HashMap<String, SynchronicClientManager>();

	public static SynchronicClientManager getClientManager(String ip, int port)
	{
		String key = ip + ":" + port;
		if (!synchronicMap.containsKey(key))
		{
			synchronicMap.put(key, createClientManager(ip, port));
		}
		return synchronicMap.get(key);
	}

	private static SynchronicClientManager createClientManager(String ip,
			int port)
	{
		ConnectionInfo info = new ConnectionInfo(ip, port, 120, MAX_TIME_OUT);
		SynchronicClientManager client = new SynchronicClientManager();
		client.initial(info);
		return client;
	}
}
