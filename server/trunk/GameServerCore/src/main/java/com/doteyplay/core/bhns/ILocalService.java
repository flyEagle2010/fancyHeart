package com.doteyplay.core.bhns;


/**
 * 本地服务需要实现的服务管理功能接口
 * 
 * @author 
 * 
 */
public interface ILocalService extends IServiceData
{
	public int getPortalId();

	public long getServiceId();
	
	public byte getEndpointId();

	public void init(IServiceAssembly serviceportal, IServiceCreator servicecreator, long serviceId,byte endpointId,
			IMethodCollection methodmap, int version);

	public <T extends ISimpleService> T getService(int portalid);

	public <T extends ISimpleService> T getService(int portalid, long serviceId);

	public <T extends ISimpleService> T getDefaultService(int portalid,byte endpointid);

	public Object invokeMethod(String methodSignature, Object[] args);

	public int getVersion();

	public boolean isConfiged();
}
