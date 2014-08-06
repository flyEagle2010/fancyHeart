package com.doteyplay.core.bhns.location;

import java.util.HashMap;
import java.util.Map;

//安全第一，就不搞删除操作了，只做修改部份同步
public class ServiceLocationInfo
{
	private int portalId;
	private Map<Long, Byte> serviceLocationMap;

	public ServiceLocationInfo(int portalId)
	{
		this.portalId = portalId;
		serviceLocationMap = new HashMap<Long, Byte>();
	}

	public int getPortalId()
	{
		return portalId;
	}

	public synchronized void changeLocation(long serviceId, byte endpointId)
	{
		Byte tmpLocation = serviceLocationMap.get(serviceId);
		if (tmpLocation == null)
			serviceLocationMap.put(serviceId, endpointId);
		else if (tmpLocation.byteValue() != endpointId)
			serviceLocationMap.put(serviceId, endpointId);
	}

	//没有删除，此处不做同步
	public byte getLocation(long serviceId)
	{
		Byte tmpLocation = serviceLocationMap.get(serviceId);
		return (tmpLocation != null) ? tmpLocation.byteValue() : (byte) -1;
	}
}
