package com.doteyplay.luna.common.action;

import org.apache.mina.core.session.IoSession;

import com.doteyplay.luna.common.message.DecoderMessage;
import com.doteyplay.luna.common.message.EncoderMessage;

/**
 * 基本发送消息的Action
 */
public abstract class BaseAction {

	/**
	 * 所有的响应必须实现的方法
	 * 
	 * @param message
	 */
	public abstract void doAction(IoSession session, DecoderMessage message);

	/**
	 * 游戏的逻辑,提供给外界直接调用游戏逻辑的方法
	 * 
	 * @param roleId
	 */
	public abstract void doAction(long roleId);

	/**
	 * 发送消息通用的方法
	 * 
	 * @param session
	 * @param message
	 */
	public void messageSent(IoSession session, EncoderMessage message) {
		session.write(message);
	}

}
