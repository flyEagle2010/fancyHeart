package com.doteyplay.game.action.chat;

import com.doteyplay.core.action.ActionAnnotation;
import com.doteyplay.core.action.ServiceMessageAction;
import com.doteyplay.exception.MessageProcessException;
import com.doteyplay.game.message.chat.ChatTagMessage;
import com.doteyplay.game.service.bo.chat.IChatService;

/**
 * 注册或关闭
 * ChatMessageAction
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年7月9日 下午5:22:12
 */

public @ActionAnnotation(message = com.doteyplay.game.message.chat.ChatTagMessage.class) class ChatTagMessageAction implements ServiceMessageAction<ChatTagMessage, IChatService>{

	@Override
	public void processMessage(ChatTagMessage message, IChatService service)
			throws MessageProcessException {
		// TODO Auto-generated method stub
		//1开启2关闭
		int type = message.getType();
		if(type ==1){
			service.register();
		}else if(type ==2){
			service.cancleRegister();
		}
	}
	
	private ChatTagMessageAction(){}

}
