package com.doteyplay.game.service.bo.tollgate;

import com.doteyplay.core.bhns.AbstractSimpleService;
import com.doteyplay.core.bhns.portal.AbstractLocalPortal;
import com.doteyplay.core.bhns.source.IEndpointSource;
import com.doteyplay.core.bhns.source.IServiceOption;
import com.doteyplay.game.service.pipeline.DefaultServicePipeline;

public class TollgateInfoWayPortal extends
		AbstractLocalPortal<ITollgateInfoService> {
	TollgateInfoService _portalService;

	public TollgateInfoWayPortal(IServiceOption option, IEndpointSource source) {
		super(option, source);
		_portalService = new TollgateInfoService();
	}

	@Override
	public void initEventListerner() {

	}

	@Override
	public void initializePortal() {
		this.bindPortalService(_portalService);
		this.bindPipline(DefaultServicePipeline.getInstance());
	}

	@Override
	protected void initializeService(
			AbstractSimpleService<ITollgateInfoService> arg0) {
		((ITollgateInfoService)arg0).initlize();
	}

	@Override
	public void releaseService(ITollgateInfoService arg0) {
		((ITollgateInfoService)arg0).release();
	}

}
