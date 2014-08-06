package com.doteyplay.net;

import com.doteyplay.CommonConfig;
import com.doteyplay.luna.server.LunaServer;
import com.doteyplay.net.action.ActionManager;

public class NetServer
{
	private LunaServer server = null;
	
	private NetServer()
	{
	}

	public void init()
	{
		server = new LunaServer(CommonConfig.PORT, ActionManager
				.getInstance());
		server.start(false);
	}
	
	public LunaServer getNetServer()
	{
		return server;
	}
	
	/////////////////////////////////////////
	private final static NetServer instance = new NetServer();
	public final static NetServer getInstance()
	{
		return instance;
	}
}
