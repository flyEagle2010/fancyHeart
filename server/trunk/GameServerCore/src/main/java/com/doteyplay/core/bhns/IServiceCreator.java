package com.doteyplay.core.bhns;

import com.doteyplay.core.bhns.data.ServiceData;

public interface IServiceCreator
{
	public void configInstance(ILocalService newinstance, long serviceId);
	
	public void resetServiceData(ILocalService c, ServiceData newServiceData);
}
