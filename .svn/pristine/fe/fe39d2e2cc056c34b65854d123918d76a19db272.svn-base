package com.doteyplay.luna.client.container;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.doteyplay.luna.client.ConnectionInfo;
import com.doteyplay.luna.common.action.ActionController;
import com.doteyplay.luna.common.message.EncoderMessage;
import com.doteyplay.luna.common.protocol.DefaultProtocolHandler;
import com.doteyplay.luna.common.protocol.codec.DefaultProtocolCodecFactory;

public class ClientConnection implements Comparable<ClientConnection>
{
	private static Logger logger = Logger.getLogger(ClientConnection.class
			.getName());
	/**
	 * 线程数量
	 */
	private static final int THREAD_NUM = Runtime.getRuntime().availableProcessors() +  1;
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
	 * 没有连接
	 */
	public static final int NOCONNECT = 0;
	/**
	 * 正在建立连接
	 */
	public static final int CONNECTING = 1;
	/**
	 * 已经连接上
	 */
	public static final int CONNECTED = 2;
	/**
	 * 当前Session的状态:0
	 */
	private AtomicInteger state = new AtomicInteger(NOCONNECT);
	/**
	 * 连接信息
	 */
	private ConnectionInfo connectionInfo;
	/**
	 * 异步传输的Session对象
	 */
	private IoSession ioSession;
	/**
	 * Nio连接对象
	 */
	private NioSocketConnector connector;
	/**
	 * 控制Action分发的类
	 */
	private ActionController actionController;
	/**
	 * 使用次数
	 */
	private long used = 0;

	public ClientConnection(ConnectionInfo connectionInfo,
			ActionController actionController)
	{
		this.connectionInfo = connectionInfo;
		this.actionController = actionController;
	}

	/**
	 * 初始化操作，主要功能是同服务器建立指定数量的连接
	 */
	public void initial()
	{
		initConnector();// 初始化连接器
		this.ioSession = this.newSession();
		if (this.ioSession == null)
		{
			this.state.set(NOCONNECT);
		} else
		{
			this.state.set(CONNECTED);
		}
		if (this.state.get() == CONNECTED)
		{
			logger.error("连接建立成功");
		} else
		{
			logger.error("连接建立失败");
		}
	}

	/**
	 * 返回当前链接对象
	 * 
	 * @return
	 */
	public ClientConnection get()
	{
		if (this.state.get() == CONNECTING)
		{// 连接中马上返回
			return this;
		} else if (this.state.get() == CONNECTED && this.ioSession != null
				&& this.ioSession.isConnected() && !this.ioSession.isClosing())
		{
			used++;
			return this;
		} else
		{
			initial(); // 重连
			return this;
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param request
	 */
	public void write(EncoderMessage request)
	{
		if (ioSession != null)
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
		this.connector = new NioSocketConnector(THREAD_NUM);// Nio连接对象
		this.connector.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new DefaultProtocolCodecFactory()));
		this.connector.getSessionConfig().setReceiveBufferSize(BUFFER_SIZE);
		this.connector.getSessionConfig().setTcpNoDelay(TCP_NO_DELAY);
		this.connector.setHandler(new DefaultProtocolHandler(
				this.actionController));
		this.connector.setConnectTimeoutMillis(CONNECT_TIMEOUT_MILLIS);
		this.connector.getSessionConfig().setUseReadOperation(true);// 设置可以读取
	}

	/**
	 * 创建新的连接对象
	 * 
	 * @return
	 */
	private IoSession newSession()
	{
		try
		{
			this.state.set(CONNECTING);
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
			return future.getSession();
		} catch (Exception e)
		{
			this.state.set(NOCONNECT);
			connector.dispose();
			logger.error("无法创建连接，可能远程主机不可用,释放连接资源！"
					+ this.connectionInfo.getServerAddress() + ":"
					+ this.connectionInfo.getServerPort());
			return null;
		}
	}

	public IoSession getSession()
	{
		return this.ioSession;
	}

	/**
	 * 获取当前连接器的状态
	 * 
	 * @return
	 */
	public Integer getState()
	{
		return this.state.get();
	}

	/**
	 * 
	 */
	@Override
	public int compareTo(ClientConnection o)
	{
		if (this.used > o.used)
			return -1;
		else
			return 1;
	}
}
