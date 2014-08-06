package com.doteyplay.core.bhns.eventservice;

import com.doteyplay.core.bhns.AbstractSimpleService;
import com.doteyplay.core.bhns.portal.AbstractLocalPortal;
import com.doteyplay.core.bhns.source.IEndpointSource;
import com.doteyplay.core.bhns.source.IServiceOption;

/**
 * 事件服务管理器
 * 
 * @author 
 * 
 */
public class ServiceEventPortal extends AbstractLocalPortal<ISvrEventService>
{
	private SvrEventService svrEventService;

	public ServiceEventPortal(IServiceOption option, IEndpointSource source)
	{
		super(option, source);
		svrEventService = new SvrEventService();
		svrEventService.init(this, null, 0, (byte) 0, option.getMethodMap(), 0);
	}

	@Override
	protected AbstractSimpleService<ISvrEventService> createServiceInstance()
	{
		return svrEventService;
	}

	@Override
	protected void initializeService(AbstractSimpleService<ISvrEventService> service)
	{

	}

	@Override
	public void releaseService(ISvrEventService svr)
	{

	}

	@Override
	public void initEventListerner()
	{
	}

	@Override
	public void initializePortal()
	{
		svrEventService.initialize(source.getConfigFile());
		this.bindPortalService(svrEventService);
	}
}
