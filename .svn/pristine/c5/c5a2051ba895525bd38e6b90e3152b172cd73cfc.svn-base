package com.doteyplay.core.bhns.source.source.cluster;

import com.doteyplay.core.bhns.AbstractSimpleService;
import com.doteyplay.core.bhns.INetSupport;
import com.doteyplay.core.bhns.IServicePortal;
import com.doteyplay.core.bhns.ISimpleService;
import com.doteyplay.core.bhns.data.ServiceData;
import com.doteyplay.core.bhns.event.IMultipleServiceEvent;
import com.doteyplay.core.bhns.net.ClientSocketFactory;
import com.doteyplay.core.bhns.portal.RemotePortal;
import com.doteyplay.core.bhns.source.IEndpointSource;
import com.doteyplay.core.bhns.source.IServiceOption;
import com.doteyplay.core.bhns.source.options.ServiceEndpointInfo;
import com.doteyplay.luna.client.SynchronicClientManager;

public class RemoteEndpointSource implements IEndpointSource, INetSupport
{
	private IServiceOption serviceOption;
	private byte endpointId;
	private RemotePortal<? extends ISimpleService> portalProxy;
	private SynchronicClientManager netChannel;

	public RemoteEndpointSource(IServiceOption serviceOption, byte endpointId)
	{
		this.serviceOption = serviceOption;
		this.endpointId = endpointId;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize()
	{
		try
		{
			this.portalProxy = new RemotePortal(serviceOption, endpointId,
					serviceOption.getSvrClass(), this);
		}
		catch (Exception e)
		{
			this.portalProxy = null;
			e.printStackTrace();
		}
	}

	@Override
	public boolean isAvailable()
	{
		return (portalProxy != null);
	}

	@Override
	public boolean supportCluster()
	{
		return (serviceOption.configType() == IServiceOption.CONFIG_TYPE_CLUSTER);
	}

	@Override
	public IServicePortal<? extends ISimpleService> getServicePortalImpl()
	{
		return portalProxy;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ISimpleService> T activeService(long svrid, boolean isOrderActive)
	{
		return (T) portalProxy.activeService(svrid, isOrderActive);
	}
	
	@Override
	public boolean isActive(long svrid)
	{
		return portalProxy.isActive(svrid);
	}

	@Override
	public AbstractSimpleService<?> createServiceImpInstance()
	{
		return null;
	}

	@Override
	public void destroyService(long svrid)
	{
		portalProxy.destroyService(svrid);
	}

	@Override
	public String postPortalCommand(int portalId,String requestCommand)
	{
		return portalProxy.doPortalCommand(portalId, requestCommand);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ISimpleService> T findService(int portalId, long svrid)
	{
		return (T) portalProxy.findService(portalId, svrid);
	}
	
	public void clearRemoteProxy(long serviceId)
	{
		portalProxy.clearRemoteProxy(serviceId);
	}

	@Override
	public String getDataBlocks()
	{
		return null;
	}

	@Override
	public byte getEndpointId()
	{
		return endpointId;
	}

	@Override
	public int getPortalId()
	{
		return this.serviceOption.portalId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ISimpleService> T getPortalService(int portalId)
	{
		return (T) portalProxy.getPortalService();
	}

	@Override
	public boolean isLocalImplemention()
	{
		return false;
	}

	@Override
	public void triggerEvent(int eventid, String methodSignature, Object[] args)
	{
	}

	@Override
	public void triggerMultipleEvent(int eventid, String methodSignature,
			Object key, Object[] args)
	{
		throw new UnsupportedOperationException("multiple event unsupport remote");
	}
	
	@Override
	public String getConfigFile()
	{
		return null;
	}

	@Override
	public int getVersion()
	{
		return 0;
	}
	
	@Override
	public SynchronicClientManager getNetChannel()
	{
		if (this.netChannel == null)
		{
			ServiceEndpointInfo tmpServiceUnitInfo = serviceOption.getEndpointInfo(this.endpointId);
			if (tmpServiceUnitInfo != null)
				this.netChannel = ClientSocketFactory.getClientManager(tmpServiceUnitInfo.getEndpointIP(),
						tmpServiceUnitInfo.getEndpointPort());
		}
		return netChannel;
	}

	@Override
	public SynchronicClientManager relocateNetChannel()
	{
		if(this.netChannel != null)
		{
			ServiceEndpointInfo tmpServiceUnitInfo = serviceOption.getEndpointInfo(this.endpointId);
			if(tmpServiceUnitInfo != null)
				this.netChannel.relocate(tmpServiceUnitInfo.getEndpointIP(),
						tmpServiceUnitInfo.getEndpointPort());
		}
		return this.netChannel;
	}

	@Override
	public boolean updateServiceImplClz(Class<? extends ISimpleService> clz)
	{
		return false;
	}

	@Override
	public Class<? extends ISimpleService> getServiceImplClz()
	{
		return null;
	}

	@Override
	public ServiceData getServiceData(long serviceId)
	{
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ISimpleService> T findService(long svrid)
	{
		return (T)this.findService(this.serviceOption.portalId(), svrid);
	}

	@Override
	public boolean regMultipleEventListener(IMultipleServiceEvent listener)
	{
		throw new UnsupportedOperationException("multiple event unsupport remote");
	}

	@Override
	public void rmMultipleEventListener(int eventid, Object key)
	{
		throw new UnsupportedOperationException("multiple event unsupport remote");
	}

	@Override
	public boolean isSyncPortalCommand(int portalId, String requestCommand)
	{
		throw new UnsupportedOperationException("isSyncPortalCommand unsupport remote");
	}
}
