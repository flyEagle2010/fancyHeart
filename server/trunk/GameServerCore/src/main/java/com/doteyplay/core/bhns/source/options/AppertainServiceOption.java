package com.doteyplay.core.bhns.source.options;

import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.core.bhns.ISimpleService;
import com.doteyplay.core.bhns.ServiceInfo;
import com.doteyplay.core.bhns.source.IServiceOption;

//服务自身的定义及相关信息扩展
public class AppertainServiceOption extends BaseServiceOption implements IServiceOption
{
	private int masterPortalId;

	@Override
	public byte configType()
	{
		return IServiceOption.CONFIG_TYPE_APPERTAIN;
	}

	public AppertainServiceOption(int ptid, Class<? extends ISimpleService> svrclass, int masterPortalId)
	{
		super(ptid, svrclass);
		this.masterPortalId = masterPortalId;
	}

	public int getMasterPortalId()
	{
		return masterPortalId;
	}

	public ServiceEndpointInfo getEndpointInfo(int endpointId)
	{
		ServiceEndpointInfo r = null;
		ServiceInfo tmpMasterInfo = BOServiceManager.getServiceInfo(masterPortalId);
		if (tmpMasterInfo != null)
			return tmpMasterInfo.option().getEndpointInfo(endpointId);
		else
			return null;
	}

}
