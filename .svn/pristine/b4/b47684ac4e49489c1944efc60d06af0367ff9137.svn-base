package com.doteyplay.game.action.role;


import com.doteyplay.core.action.ActionAnnotation;
import com.doteyplay.core.action.ServiceMessageAction;
import com.doteyplay.game.message.role.CreateRoleMessage;
import com.doteyplay.game.service.bo.virtualworld.IVirtualWorldService;

public @ActionAnnotation(message = com.doteyplay.game.message.role.CreateRoleMessage.class)
class CreateRoleMessageAction implements ServiceMessageAction<CreateRoleMessage,IVirtualWorldService>
{

	/* (non-Javadoc)
	 * @see com.doteyplay.net.message.IMessageAction#processMessage(com.doteyplay.net.message.AbstractMessage, java.lang.Object)
	 */
	public void processMessage(CreateRoleMessage message, IVirtualWorldService service)
	{
		service.createRole(message.getSpriteId(), message.getName(), message.getSessionId());
	}

	private CreateRoleMessageAction()
	{
	}

}


