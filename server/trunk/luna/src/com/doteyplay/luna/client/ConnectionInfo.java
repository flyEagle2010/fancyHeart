package com.doteyplay.luna.client;

public class ConnectionInfo
{
	/**
	 * 服务器地址
	 */
	private String serverAddress;

	/**
	 * 服务器端口
	 */
	private int serverPort;
	/**
	 * 连接生存时长
	 */
	private long connectTime;

	/**
	 * 最大超时
	 */
	private long maxTimeOut;

	public ConnectionInfo(String address, int port, long maxTimeOut, long connectTime)
	{
		this.connectTime = connectTime;
		this.serverAddress = address;
		this.serverPort = port;
		this.maxTimeOut = maxTimeOut;
	}

	/**
	 * 测试服务器连接信息是否完整。
	 * 
	 * @return
	 */
	public boolean checkConnectionInfo()
	{
		return true;
	}

	public long getMaxTimeOut()
	{
		return this.maxTimeOut;
	}

	public long getConnectTime()
	{
		return this.connectTime;
	}

	public void setConnectTime(int connectTime)
	{
		this.connectTime = connectTime;
	}

	public String getServerAddress()
	{
		return this.serverAddress;
	}

	public void setServerAddress(String serverAddress)
	{
		this.serverAddress = serverAddress;
	}

	public int getServerPort()
	{
		return this.serverPort;
	}

	public void setServerPort(int serverPort)
	{
		this.serverPort = serverPort;
	}

	public String toString()
	{
		return "服务器地址：" + this.getServerAddress() + "。服务器端口："
				+ this.getServerPort();
	}
}
