package com.doteyplay.game.startservice;

import org.apache.log4j.Logger;

import com.doteyplay.core.server.service.IServerService;
import com.doteyplay.core.server.service.IServerServiceException;
import com.doteyplay.support.auth.AuthService;

public class SupportService implements IServerService
{
	private static final Logger logger = Logger.getLogger(SupportService.class);
	private static SupportService service = new SupportService();

	private SupportService()
	{
	}

	public static SupportService getInstance()
	{
		return service;
	}

	@Override
	public void onDown() throws IServerServiceException
	{
	}

	@Override
	public void onReady() throws IServerServiceException
	{
		AuthService.getInstance();
	}

	@Override
	public void onStart() throws IServerServiceException
	{
	}

}
