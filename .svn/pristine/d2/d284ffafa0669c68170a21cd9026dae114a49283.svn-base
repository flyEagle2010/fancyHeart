package com.doteyplay.luna.client;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.doteyplay.luna.client.container.AsynchronismConnectionPool;
import com.doteyplay.luna.client.container.ClientConnection;
import com.doteyplay.luna.common.action.ActionController;
import com.doteyplay.luna.common.message.EncoderMessage;

/**
 * 执行异步操作的Manager,该类被调用
 */
public class AsynchronismClientManager
{
	private static Logger logger = Logger
			.getLogger(SynchronicClientManager.class.getName());
	/**
	 * 连接池对象
	 */
	private AsynchronismConnectionPool pool;
	/**
	 * 连接信息
	 */
	private ConnectionInfo info;

	public AsynchronismClientManager()
	{
	}

	/**
	 * 同服务器连接需要进行的初始化信息
	 * 
	 * @param connectionInfo
	 */
	public void initial(ConnectionInfo connectionInfo,
			ActionController controller)
	{
		this.info = connectionInfo;
		this.pool = new AsynchronismConnectionPool(this.info, controller);
		this.pool.initial();
	}

	/**
	 * 返回当前连接池的状态
	 * 
	 * @return
	 */
	public Integer getConnectionState()
	{
		return this.pool.getState();
	}

	/**
	 * 供外界调用的方法,连接独享写操作，写完进行释放
	 * 
	 * @param buffer
	 * @return
	 */
	public boolean invoke(EncoderMessage request)
	{
		try
		{
			ClientConnection session = this.pool.getConnect();
			if (session != null)
			{
				session.write(request);// 写消息
				return true;
			}
			else
				logger.error("【RPC失败】由于远端的服务器不能提供服务！" + this.info.toString());
		} catch (Exception e)
		{
			logger.error(
					"【RPC失败】由于远端的服务器不能提供服务，或者本地连接耗尽！" + this.info.toString(), e);
			
		}
		return false;
	}
	
	/**
	 * 供外界调用的方法,连接独享写操作，写完进行释放
	 * 
	 * @param buffer
	 * @return
	 */
	public static boolean invoke(EncoderMessage request,IoSession session)
	{
		try
		{
			if (session != null)
			{
				session.write(request);// 写消息
				return true;
			}
			else
				logger.error("【RPC失败】由于远端的服务器不能提供服务！" + session.getRemoteAddress());
		} catch (Exception e)
		{
			logger.error(
					"【RPC失败】由于远端的服务器不能提供服务，或者本地连接耗尽！" + session.getRemoteAddress(), e);
			
		}
		return false;
	}

	/**
	 * 执行关闭操作
	 */
	public void close()
	{
		this.pool.close();
	}

}
