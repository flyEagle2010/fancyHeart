package com.doteyplay.luna.client.container;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.doteyplay.luna.client.ConnectionInfo;
import com.doteyplay.luna.common.action.ActionController;

public class AsynchronismConnectionPool
{

	private static Logger logger = Logger
			.getLogger(AsynchronismConnectionPool.class.getName());

	/**
	 * 连接池数量
	 */
	private static int CONNECT_NUM = 1;
	/**
	 * 连接信息
	 */
	private ConnectionInfo connectionInfo;
	/**
	 * 控制Action分发的类
	 */
	private ActionController actionController;

	/**
	 * 目前是5个链接，随机选择
	 */
	private List<ClientConnection> connections = new ArrayList<ClientConnection>(
			CONNECT_NUM);

	public AsynchronismConnectionPool()
	{

	}

	/**
	 * 
	 * @param info
	 *            服务器连接信息
	 * @param controller
	 *            控制消息分发的Action ，当所有的消息无返回的时候，可以设置为NULL
	 */
	public AsynchronismConnectionPool(ConnectionInfo info,
			ActionController controller)
	{
		this.connectionInfo = info;
		this.actionController = controller;
	}

	/**
	 * 初始化操作，主要功能是同服务器建立指定数量的连接
	 */
	public void initial()
	{
		for (int i = 0; i < CONNECT_NUM; i++)
		{
			ClientConnection connection = new ClientConnection(connectionInfo,
					actionController);
			connection.initial();
			connections.add(connection);
		}
	}

	/**
	 * 这里需要判断一下,本方法必须同步，防止资源竞争产生消息错乱,当连接资源不够的时候会创建新的连接，保证正常通信。
	 * 
	 * @return
	 */
	public ClientConnection getConnect()
	{
		ClientConnection con = connections.get(0);
		if (con != null)
		{
			return con.get();
		} else
		{
			ClientConnection connection = new ClientConnection(connectionInfo,
					actionController);
			connection.initial();
			connections.add(0, connection);
			return connection.get();
		}
	}

	/**
	 * 获取当前连接器的状态
	 * 
	 * @return
	 */
	public Integer getState()
	{
		return connections.get(0).getState();
	}

	/**
	 * 关闭所有链接
	 */
	public void close()
	{
		logger.info("关闭连接.");
		for (ClientConnection con : connections)
		{
			con.getSession().close(true);
		}
	}
}
