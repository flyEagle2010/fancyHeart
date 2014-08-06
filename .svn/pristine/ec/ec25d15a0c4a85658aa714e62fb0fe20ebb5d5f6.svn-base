package com.doteyplay.net;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public abstract class AbstractTCPClient {
	public static final long CONNECT_TIMEOUT = 30 * 1000;

	protected String hostName;

	protected int port;

	protected InetSocketAddress serverAddress;

	protected NioSocketConnector connector;
	
	protected ConnectFuture future;
	
	protected IoSession session;

	protected IoHandler sessionHandler;
	
	private ProtocolCodecFactory protocolCodeFactory;

	protected boolean connected = false;

	public AbstractTCPClient() {

	}
	
	public void setConnectPort(int port)
	{
		this.port = port;
	}
	
	public int getConnectPort()
	{
		return port;
	}
	
	public void setHostName(String hostName)
	{
		this.hostName = hostName;
	}
	
	public String getHostName()
	{
		return hostName;
	}
	
	public void start() {
		serverAddress = new InetSocketAddress(hostName, port);
		connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
		connector.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(protocolCodeFactory));
		connector.setHandler(sessionHandler);
		future = connector.connect(serverAddress);
		future.awaitUninterruptibly();
		if (future.isConnected())
		{
			connected = true;
			session = future.getSession();
		}
	}

	public void stop() {
		dispose();
		connector.dispose();
		connector = null;
		connected = false;
	}

	public abstract void dispose();

	public IoSession getSession() {
		return session;
	}

	public IoHandler getSessionHandler() {
		return sessionHandler;
	}

	public void setSessionHandler(IoHandler sessionHandler) {
		this.sessionHandler = sessionHandler;
	}

	public ProtocolCodecFactory getProtocolCodeFactory() {
		return protocolCodeFactory;
	}

	public void setProtocolCodeFactory(ProtocolCodecFactory protocolCodeFactory) {
		this.protocolCodeFactory = protocolCodeFactory;
	}
}// Class AbstractTCPClient
