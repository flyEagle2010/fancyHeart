package com.doteyplay.luna.single;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.filter.codec.ProtocolCodecFilter;

import com.doteyplay.luna.client.ConnectionInfo;

/**
 * 单独连接目标服务器对象
 */
public class SingleClientManager
{
	/**
	 * 连接对象，关键对象
	 */
	private SingleConnection singleConnection;
	/**
	 * 连接信息
	 */
	private ConnectionInfo connectionInfo;
	/**
	 * 解码Filter
	 */
	private ProtocolCodecFilter codecFilter;
	/**
	 * 执行逻辑处理的Handler
	 */
	private SingleProtocolHandler handller;

	/**
	 * 
	 * @param connectionInfo
	 * @param actionController
	 * @param codecFilter
	 */
	public SingleClientManager(ConnectionInfo connectionInfo,
			SingleProtocolHandler actionController,
			ProtocolCodecFilter codecFilter)
	{
		this.connectionInfo = connectionInfo;
		this.handller = actionController;
		this.codecFilter = codecFilter;
		init();
	}

	/**
	 * 初始化连接对象
	 * 
	 * @return
	 */
	public boolean init()
	{
		singleConnection = new SingleConnection(this.connectionInfo,
				this.handller, this.codecFilter);
		if (singleConnection.getSession() == null)
			return false;
		else
			return true;
	}

	/**
	 * 
	 * @param obj
	 */
	public void setAttribute(String key, Object obj)
	{
		singleConnection.getSession().setAttribute(key, obj);
	}

	/**
	 * 发送消息给目标服务器
	 * 
	 * @param ioBuffer
	 */
	public void sendMessage(IoBuffer ioBuffer)
	{
		singleConnection.getSession().write(ioBuffer);
		System.out.println("=================");
	}
}
