package com.doteyplay.core.bhns.source.options;

import org.apache.log4j.Logger;

import com.doteyplay.core.bhns.ISimpleService;
import com.doteyplay.core.bhns.source.IServiceOption;

public class ClusterServiceOption extends BaseServiceOption implements IServiceOption
{
	private static final Logger logger = Logger.getLogger(ClusterServiceOption.class);

	public final static byte MAX_UNIT_ID = 127;

	private ServiceEndpointInfo[] endpointList;

	public ClusterServiceOption(int ptid, Class<? extends ISimpleService> svrclass, byte maxEndPointId)
	{
		super(ptid, svrclass);
		if (maxEndPointId < 1 || maxEndPointId > MAX_UNIT_ID)
			maxEndPointId = MAX_UNIT_ID;
		endpointList = new ServiceEndpointInfo[maxEndPointId + 1];
	}

	@Override
	public byte configType()
	{
		return IServiceOption.CONFIG_TYPE_CLUSTER;
	}

	public void regEndpointInfo(byte endpointId, String endpointIP, int endpointPort)
	{
		if (endpointId >= 0 && endpointId < endpointList.length && endpointIP != null && endpointIP.length() >= 0
				&& endpointPort > 0 && endpointPort < 65535 && endpointList[endpointId] == null)
		{
			endpointList[endpointId] = new ServiceEndpointInfo(endpointId, endpointIP, endpointPort);
		} else
		{
			logger.error("register endpointinfo error:portalId=" + this.portalId() + "endpointId=" + endpointId
					+ ",endpointIP=" + endpointIP + ",endpointPort=" + endpointPort);
		}
	}

	public int getMaxEndpointId()
	{
		return endpointList.length-1;
	}

	public ServiceEndpointInfo getEndpointInfo(int endpointId)
	{
		if (endpointId >= 0 && endpointId < endpointList.length && endpointList[endpointId] != null)
			return endpointList[endpointId];
		else
			return null;
	}

	public String getEndpointIP(int endpointId)
	{
		if (endpointId >= 0 && endpointId < endpointList.length && endpointList[endpointId] != null)
			return endpointList[endpointId].getEndpointIP();
		else
			return null;
	}

	public int getEndpointPort(int endpointId)
	{
		if (endpointId >= 0 && endpointId < endpointList.length && endpointList[endpointId] != null)
			return endpointList[endpointId].getEndpointPort();
		else
			return 0;
	}
}
