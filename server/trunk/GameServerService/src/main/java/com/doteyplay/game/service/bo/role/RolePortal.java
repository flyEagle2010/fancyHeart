package com.doteyplay.game.service.bo.role;

import com.doteyplay.core.bhns.AbstractSimpleService;
import com.doteyplay.core.bhns.portal.AbstractLocalPortal;
import com.doteyplay.core.bhns.source.IEndpointSource;
import com.doteyplay.core.bhns.source.IServiceOption;
import com.doteyplay.game.service.pipeline.DefaultServicePipeline;

public class RolePortal extends AbstractLocalPortal<IRoleService>
{
	RoleService _portalService;
	
	public RolePortal(IServiceOption option, IEndpointSource source)
	{
		super(option, source);
		_portalService=new RoleService();
		
	}

	@Override
	public void initEventListerner()
	{

	}

	@Override
	public void initializePortal()
	{
		this.bindPortalService(_portalService);
		this.bindPipline(DefaultServicePipeline.getInstance());
	}

	@Override
	protected void initializeService(AbstractSimpleService<IRoleService> arg0)
	{
		((IRoleService)arg0).initialize();
	}

	@Override
	public void releaseService(IRoleService arg0)
	{

	}

}
