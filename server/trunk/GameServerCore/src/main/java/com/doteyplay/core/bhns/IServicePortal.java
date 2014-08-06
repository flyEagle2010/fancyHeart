package com.doteyplay.core.bhns;

/**
 * 网络通道获取
 * 
 * @author 
 * 
 * 此接口是规范poltal内部结构，也就是从服务自身管理角度(不考虑调用其他远程服务)来看，服务应该提供的基本组织形态
 * 
 */

public interface IServicePortal<T extends ISimpleService>
{
	public int getPortalId(); // 此服务提供的服务标识，目前设计每个服务对外只提供一种行为
	
	public byte getEndpointId();

	public String doPortalCommand(int portalId,String requestCommand);//portal简单指令请求
	
	public ISimpleService findService(long svrid); // 根据KEY值获取一个服务对象
	
	public ISimpleService findService(int portalid,long svrid); // 根据KEY值获取一个服务对象
	
	public boolean isActive(long serviceId);
	
	public T activeService(long svrid, boolean isOrderActive); // 根据KEY值激活一个服务对象
	
	public T getPortalService(); // 获取无状态服务对象

	public void destroyService(long svrid); // 释放一个服务对象

	public boolean isProxy(); // 是否远程代理

}
