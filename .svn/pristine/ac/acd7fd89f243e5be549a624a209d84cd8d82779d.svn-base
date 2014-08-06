package com.doteyplay.core.bhns.location;

import java.util.HashMap;
import java.util.Map;

import com.doteyplay.core.bhns.AbstractSimpleService;

//安全第一，就不搞删除操作了
public class SvrLocationService extends AbstractSimpleService<ISvrLocationService> implements ISvrLocationService
{
	private Map<Integer, ServiceLocationInfo> portalLocationMap = new HashMap<Integer, ServiceLocationInfo>();

	@Override
	public int getPortalId()
	{
		return ISvrLocationService.PORTAL_ID;
	}

	@Override
	public boolean isSingleton()
	{
		return true;
	}

	@Override
	public synchronized void changeLocationOfserviceId(int portalId, long serviceId, byte endpointId)
	{
		if (portalId >= 0 && serviceId >= 0)
		{
			ServiceLocationInfo tmpServiceLocationInfo = portalLocationMap.get(portalId);
			if (tmpServiceLocationInfo == null)
			{
				tmpServiceLocationInfo = new ServiceLocationInfo(portalId);
				portalLocationMap.put(portalId, tmpServiceLocationInfo);
			}
			tmpServiceLocationInfo.changeLocation(serviceId, endpointId);
		}
	}

	// 不删除，此处不做同步
	@Override
	public byte getEndpointIdOfserviceId(int portalId, long serviceId)
	{
		ServiceLocationInfo tmpServiceLocationInfo = portalLocationMap.get(portalId);
		return (tmpServiceLocationInfo != null) ? tmpServiceLocationInfo.getLocation(serviceId) : (byte)-1;
	}

	@Override
	public void messageReceived(byte[] bytes,long sessionId)
	{
		throw new UnsupportedOperationException();
	}
}
