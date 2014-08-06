package com.doteyplay.game.service.bo.item;

import com.doteyplay.core.bhns.AbstractSimpleService;
import com.doteyplay.core.bhns.portal.AbstractLocalPortal;
import com.doteyplay.core.bhns.source.IEndpointSource;
import com.doteyplay.core.bhns.source.IServiceOption;
import com.doteyplay.game.service.pipeline.DefaultServicePipeline;

public class ItemPortal extends AbstractLocalPortal<IItemService>
{
	ItemService _portalService;
	
	public ItemPortal(IServiceOption option, IEndpointSource source)
	{
		super(option, source);
		_portalService=new ItemService();
		
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
	protected void initializeService(AbstractSimpleService<IItemService> arg0)
	{
		((IItemService)arg0).initialize();
	}

	@Override
	public void releaseService(IItemService arg0)
	{

	}

}
