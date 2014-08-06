package com.doteyplay.core.bhns.location;

import com.doteyplay.core.bhns.ISimpleService;

public interface ISvrLocationService extends ISimpleService
{
	public final static int PORTAL_ID = 1;

	public byte getEndpointIdOfserviceId(int portalId, long serviceId);

	public void changeLocationOfserviceId(int portalId, long serviceId, byte endpointId);
}
