package com.doteyplay.game.action.gm;

import com.doteyplay.core.action.ActionAnnotation;
import com.doteyplay.core.action.ServiceMessageAction;
import com.doteyplay.exception.MessageProcessException;
import com.doteyplay.game.message.chat.ChatMessage;
import com.doteyplay.game.service.bo.virtualworld.IVirtualWorldService;

/**
 * ChatMessageAction
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年7月9日 下午5:22:12
 */

public @ActionAnnotation(message = com.doteyplay.game.message.chat.ChatMessage.class) class ChatMessageAction implements ServiceMessageAction<ChatMessage, IVirtualWorldService>{

	@Override
	public void processMessage(ChatMessage message, IVirtualWorldService service)
			throws MessageProcessException {
		// TODO Auto-generated method stub
		service.doGm(message.getSource());
		
	}
	
	private ChatMessageAction(){}

}
