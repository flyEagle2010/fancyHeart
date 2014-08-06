package com.doteyplay.game.service.bo.virtualworld.chat;

import com.doteyplay.game.constants.chat.ChatConstant;
import com.doteyplay.game.service.bo.virtualworld.chat.resolve.GoodsDataTypeResolve;
import com.doteyplay.game.service.bo.virtualworld.chat.resolve.IChatResolve;
import com.doteyplay.game.service.bo.virtualworld.chat.resolve.DataTypeResolve;

/**
 * @className:PatameterFactory.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年7月10日 上午11:41:41
 */
public class ResolvePatameterFactory {

	public static IChatResolve getResolve(ChatConstant chatConstantType){
		if(chatConstantType.isDataValueType()){
			return new DataTypeResolve(chatConstantType);
		}
		
		if(chatConstantType.isGoodsType()){
			return new GoodsDataTypeResolve(chatConstantType);
		}
		return null;
	}
	
	public static ChatConstant getChatConstantType(String source){
		for (ChatConstant element : ChatConstant.values()) {
			if(source.startsWith(element.getSuffix())){
				return element;
			}
		}
		throw new RuntimeException("数据不合法!");
		
	}
}
