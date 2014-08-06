package com.doteyplay.luna.single;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.doteyplay.luna.client.ConnectionInfo;

/**
 * 连接对象
 */
public class SingleConnection
{
	private static Logger logger = Logger.getLogger(SingleConnection.class);
	/**
	 * 缓冲区大小
	 */
	private static final int BUFFER_SIZE = 1024 * 8;
	/**
	 * 是否延迟发送
	 */
	private static final boolean TCP_NO_DELAY = true;
	/**
	 * 设置连接超时时间
	 */
	private static final long CONNECT_TIMEOUT_MILLIS = 10 * 1000L;
	/**
	 * 异步传输的Session对象
	 */
	private IoSession ioSession;
	/**
	 * Nio连接对象
	 */
	private NioSocketConnector connector;
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
	 * 默认的构造函数
	 * 
	 * @param connectionInfo
	 * @param actionController
	 * @param codecFilter
	 */
	public SingleConnection(ConnectionInfo connectionInfo,
			SingleProtocolHandler actionController,
			ProtocolCodecFilter codecFilter)
	{
		this.connectionInfo = connectionInfo;
		this.handller = actionController;
		this.codecFilter = codecFilter;
		initial();
	}

	/**
	 * 初始化操作，主要功能是同服务器建立指定数量的连接
	 */
	public void initial()
	{
		initConnector();// 初始化连接器
		try
		{
			if (logger.isDebugEnabled())
				logger.debug("创建新的连接对象！"
						+ this.connectionInfo.getServerAddress() + ":"
						+ this.connectionInfo.getServerPort());
			ConnectFuture future = this.connector
					.connect(new InetSocketAddress(this.connectionInfo
							.getServerAddress(), this.connectionInfo
							.getServerPort()));
			;
			future.awaitUninterruptibly();
			this.ioSession = future.getSession();
		} catch (Exception e)
		{
			connector.dispose();
			logger.error("无法创建连接，可能远程主机不可用,释放连接资源！"
					+ this.connectionInfo.getServerAddress() + ":"
					+ this.connectionInfo.getServerPort());
			this.ioSession = null;
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param request
	 */
	public void write(IoBuffer request)
	{
		this.ioSession.write(request);
	}

	/**
	 * 初始化连接属性
	 */
	private void initConnector()
	{
		if (logger.isDebugEnabled())
			logger.debug("初始化连接器:" + this.connectionInfo.getServerAddress()
					+ ":" + this.connectionInfo.getServerPort());
		this.connector = new NioSocketConnector(1);// Nio连接对象
		this.connector.getFilterChain().addLast("codec", codecFilter);
		this.connector.getSessionConfig().setReceiveBufferSize(BUFFER_SIZE);
		this.connector.getSessionConfig().setTcpNoDelay(TCP_NO_DELAY);
		this.connector.setHandler(handller);
		this.connector.setConnectTimeoutMillis(CONNECT_TIMEOUT_MILLIS);
		this.connector.getSessionConfig().setUseReadOperation(true);// 设置可以读取
	}

	/**
	 * 获取当前会话信息
	 * 
	 * @return
	 */
	public IoSession getSession()
	{
		return this.ioSession;
	}
}
