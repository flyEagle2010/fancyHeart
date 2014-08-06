package com.doteyplay.luna.single;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

/**
 * 单独连接分发控制的Controller，需要子类实现
 */
public interface SingleActionController {
	/**
	 * 根据Decoder的指令编号获得对应的Action
	 * @param message
	 * @return
	 */
	public void doAction(IoSession session,IoBuffer buffer);
	/**
	 * 当客户端关闭的时候需会调用这个方法
	 * @param session
	 */
	public void sessionClose(IoSession session);
}
