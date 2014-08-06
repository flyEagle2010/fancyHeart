package com.doteyplay.core.bhns.location;

import com.doteyplay.core.bhns.AbstractSimpleService;
import com.doteyplay.core.bhns.portal.AbstractLocalPortal;
import com.doteyplay.core.bhns.source.IEndpointSource;
import com.doteyplay.core.bhns.source.IServiceOption;

public class SvrLocationPortal extends AbstractLocalPortal<ISvrLocationService>
{
	private SvrLocationService svrLocationService;

	public SvrLocationPortal(IServiceOption option, IEndpointSource source)
	{
		super(option, source);
		svrLocationService = new SvrLocationService();
//		svrLocationService.init(this, null, 0, (byte) 0, option.getMethodMap(), 0);
	}

	@Override
	public void initEventListerner()
	{

	}

	@Override
	public void initializePortal()
	{
		this.bindPortalService(svrLocationService);
	}

	@Override
	protected void initializeService(AbstractSimpleService<ISvrLocationService> service)
	{

	}

	@Override
	public void releaseService(ISvrLocationService svr)
	{

	}

}
