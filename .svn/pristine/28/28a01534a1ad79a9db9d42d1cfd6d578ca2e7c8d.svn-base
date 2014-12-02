package com.doteyplay.game.service.bo.chat;

import com.doteyplay.core.bhns.AbstractSimpleService;
import com.doteyplay.core.bhns.portal.AbstractLocalPortal;
import com.doteyplay.core.bhns.source.IEndpointSource;
import com.doteyplay.core.bhns.source.IServiceOption;
import com.doteyplay.game.service.pipeline.DefaultServicePipeline;

public class ChatWayPortal extends
		AbstractLocalPortal<IChatService> {
	ChatService _portalService;

	public ChatWayPortal(IServiceOption option, IEndpointSource source) {
		super(option, source);
		_portalService = new ChatService();
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
			AbstractSimpleService<IChatService> arg0) {
		((IChatService)arg0).initlize();
	}

	@Override
	public void releaseService(IChatService arg0) {
		((IChatService)arg0).release();
	}

}
