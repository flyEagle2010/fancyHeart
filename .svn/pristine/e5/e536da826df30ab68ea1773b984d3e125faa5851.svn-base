package com.doteyplay.game.service.bo.virtualworld.chat;

import com.doteyplay.game.constants.chat.ChatConstant;
import com.doteyplay.game.service.bo.virtualworld.chat.handle.IChatHandler;
import com.doteyplay.game.service.bo.virtualworld.chat.patameter.IPatameterObject;
import com.doteyplay.game.service.bo.virtualworld.chat.resolve.IChatResolve;

/**
 * @className:GMTestHandle.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年7月10日 上午11:49:13
 */
public class GMTestHandle {

	public static void main(String[] args) {
		
		String source = "money[2][1000]";
		
		ChatConstant chatConstantType = ResolvePatameterFactory.getChatConstantType(source);
		
		IChatResolve igmPatameter = ResolvePatameterFactory.getResolve(chatConstantType);
		IPatameterObject patameterObject = igmPatameter.handlePatameter(source);
		IChatHandler gmProcess = ChatHanderFactory.getInstance().getGMProcess(chatConstantType);
		gmProcess.handle(patameterObject);
		
	}
}
