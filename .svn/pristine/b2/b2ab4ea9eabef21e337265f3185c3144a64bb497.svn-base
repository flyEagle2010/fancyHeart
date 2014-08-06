package com.doteyplay.core.bhns.portal;

import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.core.bhns.INetSupport;
import com.doteyplay.core.bhns.IServicePortal;
import com.doteyplay.core.bhns.ISimpleService;
import com.doteyplay.core.bhns.ServiceInfo;
import com.doteyplay.core.bhns.rmi.ServiceProxy;
import com.doteyplay.core.bhns.source.IServiceOption;

/**
 * 远端服务在本地的服务代理入口
 * 
 * @author 
 * 
 */
public class RemotePortal<T extends ISimpleService> implements IServicePortal<T>
{
	private byte endpointId;
	protected IServiceOption option;
	private ServiceProxy<T> serviceProxy;

	public RemotePortal(IServiceOption option, byte endpointId,Class<T> interfaceClass, INetSupport netSupport)
	{
		this.option = option;
		this.endpointId=endpointId;
		this.serviceProxy = new ServiceProxy<T>(option.portalId(),this.endpointId, interfaceClass, netSupport);
	}

	// IBOService******************
	@Override
	public int getPortalId()
	{
		return option.portalId();
	}

	@Override
	public byte getEndpointId()
	{
		return endpointId;
	}
	
	@Override
	public boolean isActive(long serviceId)
	{
		return serviceProxy.isActive(serviceId);
	}

	@Override
	public ISimpleService findService(long serviceId)
	{
		return findService(this.getPortalId(), serviceId);
	}

	@Override
	public ISimpleService findService(int portalid, long serviceId)
	{
		ISimpleService r = serviceProxy.getServiceProxy(serviceId);
		if (r == null)
		{
			ServiceInfo tmpServiceInfo = BOServiceManager.getServiceInfo(portalid);
			if (tmpServiceInfo != null)
				r = tmpServiceInfo.option().getDummyService();
		}
		return r;
	}
	
	public void clearRemoteProxy(long serviceId)
	{
		serviceProxy.clearRemoteProxy(serviceId);
	}

	@Override
	public String doPortalCommand(int portalId,String requestCommand)
	{
		return serviceProxy.doPortalCommand(portalId,requestCommand);
	}
	
	@Override
	public T activeService(long serviceId, boolean isOrderActive)
	{
		return serviceProxy.activeServiceProxy(serviceId,isOrderActive);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getPortalService()
	{
		T r = serviceProxy.getPortalServiceProxy();
		if (r == null)
			r = (T) option.getDummyService();
		return r;
	}

	@Override
	public void destroyService(long serviceId)
	{
		serviceProxy.clearServiceProxy(serviceId);
	}

	@Override
	public boolean isProxy()
	{
		return true;
	}

}
