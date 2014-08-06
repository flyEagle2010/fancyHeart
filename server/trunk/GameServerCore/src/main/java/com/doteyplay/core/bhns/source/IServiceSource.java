package com.doteyplay.core.bhns.source;

import com.doteyplay.core.bhns.AbstractSimpleService;
import com.doteyplay.core.bhns.ISimpleService;

public interface IServiceSource
{
	public void initialize();

	public int getVersion(byte endpointId);

	public String getDataBlocks(byte endpointId);

	public String getConfigFile(byte endpointId);

	public boolean isAvailable();

	public boolean isLocalImplemention(byte endpointId);
	
	public boolean isActive(long svrid, byte endpointId);
	
	public String postPortalCommand(int portalId,String requestCommand,byte endpointId);
	
	public <T extends ISimpleService> T activeService(long svrid, byte endpointId, boolean isOrderActive);

	public <T extends ISimpleService> T findService(int portalId, long svrid);

	public void destroyService(long svrid);

	public byte endpointIdOfserviceId(long svrid);

	public byte[] endpointIdList();
	
	public IEndpointSource getEndpoint(byte endpointId);

	public <T extends ISimpleService> T getPortalService(int portalId, byte endpointId);

	public <T extends ISimpleService> T getLocalPortalService(int portalId);

	public void triggerEvent(int eventid, String methodSignature, Object[] args);

	public AbstractSimpleService<?> createServiceImpInstance(byte endpointId);

	public IEndpointSource getLocalEndPoint();
	
	public int getEndPointSize();
	
	public boolean isSyncPortalCommand(int portalId, String requestCommand);
}
