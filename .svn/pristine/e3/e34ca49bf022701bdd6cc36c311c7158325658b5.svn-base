package com.doteyplay.game.action.chat;

import com.doteyplay.core.action.ActionAnnotation;
import com.doteyplay.core.action.ServiceMessageAction;
import com.doteyplay.exception.MessageProcessException;
import com.doteyplay.game.message.chat.ChatMessage;
import com.doteyplay.game.service.bo.chat.IChatService;

/**
 * ChatMessageAction
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年7月9日 下午5:22:12
 */

public @ActionAnnotation(message = com.doteyplay.game.message.chat.ChatMessage.class) class ChatMessageAction implements ServiceMessageAction<ChatMessage, IChatService>{

	@Override
	public void processMessage(ChatMessage message, IChatService service)
			throws MessageProcessException {
		// TODO Auto-generated method stub
		if(message.getType()==1){
			
			service.doGm(message.getSource());
		}else{
			service.sendMsg(message.getSource());
		}
		
	}
	
	private ChatMessageAction(){}

}
