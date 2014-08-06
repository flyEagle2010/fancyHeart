package com.doteyplay.core.bhns.source.options;

import com.doteyplay.core.bhns.ISimpleService;
import com.doteyplay.core.bhns.source.IServiceOption;

public class NormalServiceOption extends ClusterServiceOption
{
	public NormalServiceOption(int ptid, Class<? extends ISimpleService> svrclass, String endpointIp, int endpointPort)
	{
		super(ptid, svrclass, (byte)1);
		this.regEndpointInfo((byte) 0, endpointIp, endpointPort);
	}

	@Override
	public byte configType()
	{
		return IServiceOption.CONFIG_TYPE_NORMAL;
	}

	public String getServiceIP()
	{
		return this.getEndpointIP(0);
	}

	public int getServicePort()
	{
		return this.getEndpointPort(0);
	}

}
