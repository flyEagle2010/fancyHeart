package com.doteyplay.game.action.login;


import com.doteyplay.core.action.ActionAnnotation;
import com.doteyplay.core.action.ServiceMessageAction;
import com.doteyplay.game.message.login.LoginGameMessage;
import com.doteyplay.game.service.bo.virtualworld.IVirtualWorldService;

public @ActionAnnotation(message = com.doteyplay.game.message.login.LoginGameMessage.class)
class LoginGameMessageAction implements ServiceMessageAction<LoginGameMessage,IVirtualWorldService>
{

	/* (non-Javadoc)
	 * @see com.doteyplay.net.message.IMessageAction#processMessage(com.doteyplay.net.message.AbstractMessage, java.lang.Object)
	 */
	public void processMessage(LoginGameMessage message, IVirtualWorldService service)
	{
		service.doLogin(message.getSessionId(), message.getAccount(),message.getSessionKey(),message.getAreaId());
	}

	private LoginGameMessageAction()
	{
	}

}


