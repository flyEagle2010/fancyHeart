package com.doteyplay.core.bhns.source.source.cluster;

import com.doteyplay.core.bhns.AbstractSimpleService;
import com.doteyplay.core.bhns.IServicePortal;
import com.doteyplay.core.bhns.ISimpleService;
import com.doteyplay.core.bhns.data.ServiceData;
import com.doteyplay.core.bhns.event.IMultipleServiceEvent;
import com.doteyplay.core.bhns.source.IEndpointSource;
import com.doteyplay.core.bhns.source.options.AppertainServiceOption;

public class AppertainEndpointSource implements IEndpointSource
{
	private AppertainServiceOption serviceOption;
	private IEndpointSource masterEndpoint;

	public AppertainEndpointSource(AppertainServiceOption serviceOption, IEndpointSource masterEndpoint)
	{
		this.serviceOption = serviceOption;
		this.masterEndpoint = masterEndpoint;
	}

	@Override
	public void initialize()
	{
	}

	@Override
	public boolean supportCluster()
	{
		return masterEndpoint.supportCluster();
	}

	@Override
	public boolean isLocalImplemention()
	{
		return masterEndpoint.isLocalImplemention();
	}

	@Override
	public int getVersion()
	{
		return 0;
	}

	@Override
	public String getConfigFile()
	{
		return null;
	}

	@Override
	public byte getEndpointId()
	{
		return masterEndpoint.getEndpointId();
	}

	@Override
	public int getPortalId()
	{
		return serviceOption.portalId();
	}

	@Override
	public String getDataBlocks()
	{
		return null;
	}

	@Override
	public boolean isAvailable()
	{
		return true;
	}

	@Override
	public IServicePortal<? extends ISimpleService> getServicePortalImpl()
	{
		return null;
	}

	@Override
	public AbstractSimpleService<?> createServiceImpInstance()
	{
		return null;
	}

	@Override
	public String postPortalCommand(int portalId,String requestCommand)
	{
		return this.masterEndpoint.postPortalCommand(portalId, requestCommand);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ISimpleService> T findService(int portalId, long svrid)
	{
		return (T) this.masterEndpoint.findService(portalId, svrid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ISimpleService> T getPortalService(int portalId)
	{
		return (T) serviceOption.getDummyService();
	}

	@Override
	public void triggerEvent(int eventid, String methodSignature, Object[] args)
	{
		this.masterEndpoint.triggerEvent(eventid, methodSignature, args);
	}
	
	@Override
	public void triggerMultipleEvent(int eventid, String methodSignature,
			Object key, Object[] args)
	{
		this.masterEndpoint.triggerMultipleEvent(eventid, methodSignature, key, args);
	}

	@Override
	public <T extends ISimpleService> T activeService(long svrid, boolean isOrderActive)
	{
		return null;
	}

	@Override
	public boolean isActive(long svrid)
	{
		return this.masterEndpoint.isActive(svrid);
	}
	
	@Override
	public void destroyService(long svrid)
	{

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
		this.masterEndpoint.regMultipleEventListener(listener);
		return true;
	}

	@Override
	public void rmMultipleEventListener(int eventid, Object key)
	{
		this.masterEndpoint.rmMultipleEventListener(eventid, key);
	}

	@Override
	public boolean isSyncPortalCommand(int portalId, String requestCommand)
	{
		return this.masterEndpoint.isSyncPortalCommand(portalId, requestCommand);
	}
}
