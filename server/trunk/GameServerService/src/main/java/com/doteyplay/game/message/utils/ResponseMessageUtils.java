package com.doteyplay.game.message.utils;

import com.doteyplay.game.domain.role.Role;
import com.doteyplay.game.message.common.CommonResponseMessage;

/**
 * @className:ResponseMessageUtils.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年7月18日 下午6:14:14
 */
public class ResponseMessageUtils {

	public static  void sendResponseMessage(int resultType,int state,Role role){
		CommonResponseMessage message = new CommonResponseMessage();
		message.setResultType(resultType);
		message.setState(state);
		role.sendMsg(message);
		
	}
	
	
}
