package com.doteyplay.core.bhns;

import java.util.Collection;

import com.doteyplay.net.protocol.IServicePipeline;

/**
 * 服务聚合形态
 * @author 
 *
 * 此接口是规范服务portal的远程调用组织结构，也就是从调用其他远程服务的角度来看，每个服务应该提供的组织形态
 * 
 */
public interface IServiceAssembly
{
	/**
	 * 从服务聚合中获得一个服务接口
	 * 
	 * @return IBehavior
	 */
	public ISimpleService getService(int portalid,long serviceId);
	
	/**
	 * 从服务聚合中激活一个服务接口
	 * @param isOrderActive TODO
	 * @param sessionId TODO
	 * @return IBehavior
	 */
	public ISimpleService activeService(int portalid,long serviceId,byte endpointId, boolean isOrderActive);
	
	/**
	 * 从服务集群中获得一个无状态接口
	 * 
	 * @return IBehavior
	 */
	public ISimpleService getDefaultService(int portalid,byte endpointId);
		
	/**
	 * 从服务聚合中获得所有活跃接口
	 * 
	 * @return IBehavior
	 */
	public Collection<ISimpleService> getActiveServices();
	
	/**
	 * 从服务聚合中释放一个服务接口
	 * 
	 * @return IBehavior
	 */
	public void destroyService(int portalid,long serviceId);
	
	/**
	 * 获取消息分发
	 * @return
	 */
	public IServicePipeline getPipline();
	
}
