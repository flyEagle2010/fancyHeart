package com.doteyplay.core.bhns;

public interface IServiceInfoAssembly
{
	public ServiceInfo get_service_info(int portalid);

	public void broadcast_service_location(int portalId, long svrid, byte endpointId);
	
	public void sync_service_location(int portalId, long svrid, byte endpointId);
	

}
