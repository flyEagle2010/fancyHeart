package com.doteyplay.support.auth;

import com.doteyplay.luna.common.message.EncoderMessage;
import com.doteyplay.support.ISupportService;

public class AuthHandler
{
	private ISupportService service = null;
	
	private AuthHandler()
	{
	}
	
	public void init(ISupportService service)
	{
		this.service = service;
	}
	
	public void sendCheckAuthReq(long sessionId,String account,String sessionKey,int areaId)
	{
		EncoderMessage message = new EncoderMessage((short) AuthCommand.CHECK_AUTH, true);
		message.putLong(sessionId);
		message.putString(account);
		message.putString(sessionKey);
		message.putInt(areaId);
		
		service.invoke(message);
	}
	
	///////////////////////////////////////////////////////////////
	private static final AuthHandler instance = new AuthHandler();
	
	public static final AuthHandler getInstance()
	{
		return instance;
	}
}
