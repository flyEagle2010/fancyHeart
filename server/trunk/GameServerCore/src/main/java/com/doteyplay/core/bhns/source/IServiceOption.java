package com.doteyplay.core.bhns.source;

import com.doteyplay.core.bhns.IMethodCollection;
import com.doteyplay.core.bhns.ISimpleService;
import com.doteyplay.core.bhns.source.options.ServiceEndpointInfo;

public interface IServiceOption
{
	public final byte CONFIG_TYPE_NORMAL = 0;
	public final byte CONFIG_TYPE_CLUSTER = 1;
	public final byte CONFIG_TYPE_APPERTAIN = 2;

	public int portalId();

	public byte configType();

	public Object invokeMethod(ISimpleService service, String methodSignature, Object[] args);

	public Object invokeDummy(String methodSignature, Object[] args);

	public IMethodCollection getMethodMap();

	public ISimpleService getDummyService();

	public Class<? extends ISimpleService> getSvrClass();
	
	public ServiceEndpointInfo getEndpointInfo(int endpointId);
	
	public boolean isSyncMethod(String methodSignature);
}
