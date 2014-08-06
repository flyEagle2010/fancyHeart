package com.doteyplay.game.service.bo.gateway;

import com.doteyplay.core.bhns.AbstractSimpleService;
import com.doteyplay.core.bhns.portal.AbstractLocalPortal;
import com.doteyplay.core.bhns.source.IEndpointSource;
import com.doteyplay.core.bhns.source.IServiceOption;

public class GameGateWayPortal extends AbstractLocalPortal<IGameGateWayService>
{
	NSGameGateWayService _portalService;
	
	public GameGateWayPortal(IServiceOption option, IEndpointSource source)
	{
		super(option, source);
		_portalService=new NSGameGateWayService();
	}

	@Override
	public void initEventListerner()
	{

	}

	@Override
	public void initializePortal()
	{
		this.bindPortalService(_portalService);
	}

	@Override
	protected void initializeService(AbstractSimpleService<IGameGateWayService> arg0)
	{
	}

	@Override
	public void releaseService(IGameGateWayService arg0)
	{
		
	}

}
