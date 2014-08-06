package com.doteyplay.game.startservice;

import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.core.bhns.BOServiceConfig;
import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.core.bhns.ServiceInfo;
import com.doteyplay.core.bhns.active.ActiveServiceHolder;
import com.doteyplay.core.bhns.source.IEndpointSource;
import com.doteyplay.core.bhns.source.options.ServiceEndpointInfo;
import com.doteyplay.core.server.service.IServerService;
import com.doteyplay.core.server.service.IServerServiceException;
import com.doteyplay.game.BOConst;
import com.doteyplay.game.config.ServerConfig;
import com.doteyplay.game.service.bo.gateway.IGameGateWayService;
import com.doteyplay.game.service.bo.virtualworld.IVirtualWorldService;
import com.doteyplay.luna.client.ConnectionInfo;
import com.doteyplay.luna.common.threadpool.IUserPkIdDecoder;
import com.doteyplay.net.AbstractTCPServer;

public class ServiceService extends AbstractTCPServer implements IServerService
{
	/**
	 * 单例模式
	 */
	private static ServiceService service = new ServiceService();

	private ServiceService()
	{
	}

	public static ServiceService getInstance()
	{
		return service;
	}

	@Override
	public void onDown() throws IServerServiceException
	{
	}

	@Override
	public void onReady() throws IServerServiceException
	{
		BOServiceConfig.loadService(ServerConfig.COMMON_SERVICE_PATH,
				ServerConfig.LOCAL_SERVICE_PATH);
		ActiveServiceHolder.initialize();
		// 检查
		BOServiceConfig.checkServiceList(BOConst.class, null, null);
		BOServiceManager.initialService();
	}

	@Override
	public void onStart() throws IServerServiceException
	{
	}

	@Override
	public void dispose()
	{
	}

	public static boolean isWorldService()
	{
		return BOServiceManager
				.findLocalEndpointId(IVirtualWorldService.PORTAL_ID) >= 0;
	}
	
	public static boolean isGatewayService()
	{
		return BOServiceManager
				.findLocalEndpointId(IGameGateWayService.PORTAL_ID) >= 0;
	}

	/**
	 * @param endpointId
	 * @return
	 */
	public static ConnectionInfo getGatewayConnectionInfo(int endpointId)
	{
		ServiceInfo servcieInfo = BOServiceManager.getServiceInfo(IGameGateWayService.PORTAL_ID);
		ServiceEndpointInfo sepi = servcieInfo.option().getEndpointInfo(endpointId);
		if(sepi != null)
		{
			ConnectionInfo info = new ConnectionInfo(sepi.getEndpointIP(), sepi.getEndpointPort(), 0, 0);
			return info;
		}
		return null;
	}
}
