package com.doteyplay.core.bhns.source;

import com.doteyplay.core.bhns.AbstractSimpleService;
import com.doteyplay.core.bhns.IServicePortal;
import com.doteyplay.core.bhns.ISimpleService;
import com.doteyplay.core.bhns.data.ServiceData;
import com.doteyplay.core.bhns.event.IEventListener;
import com.doteyplay.core.bhns.event.IMultipleEventListener;
import com.doteyplay.core.bhns.event.IMultipleServiceEvent;

/**
 * 
* @className:IEndpointSource.java
* @classDescription: 节点相关的接口
 */
public interface IEndpointSource extends IEventListener,IMultipleEventListener
{
	/**
	 * 初使化
	 */
	public void initialize();
	/**
	 * 是否为本地节点.
	 * @return true or false
	 */
	public boolean isLocalImplemention();
	/**
	 * 是否支持Cluster(群,簇)
	 * @return
	 */
	public boolean supportCluster();
	/**
	 * 返回节点Id
	 * @return
	 */
	public byte getEndpointId();
	/**
	 * 返回PortalId
	 * @return
	 */
	public int getPortalId();
	/**
	 * 返回版本
	 * @return
	 */
	public int getVersion();
	
	/**
	 * 更新服务的实现类.
	 * @param clz
	 * @return
	 */
	public boolean updateServiceImplClz(Class<? extends ISimpleService> clz);
	/**
	 * 获得服务的实现类
	 * @return
	 */
	public Class<? extends ISimpleService> getServiceImplClz();
	
	/**
	 * 获得配置文件
	 * @return
	 */
	public String getConfigFile();
	/**
	 * 返回数据模块
	 * @return
	 */
	public String getDataBlocks();
	/**
	 * 是否可用
	 * @return
	 */
	public boolean isAvailable();
	/**
	 * 返回服务的Portal实现
	 * @return
	 */
	public IServicePortal<? extends ISimpleService> getServicePortalImpl();
	/**
	 * 创造serviceImp
	 * @return
	 */
	public AbstractSimpleService<?> createServiceImpInstance();
	/**
	 * 释放服务
	 * @param svrid
	 */
	public void destroyService(long svrid);
	
	/**
	 * 传递Potalcommand 
	 * @param portalId
	 * @param requestCommand
	 * @return
	 */
	public String postPortalCommand(int portalId,String requestCommand);

	/**
	 * 查找服务
	 * @param portalId 
	 * @param svrid 服务Id.一般roleId
	 * @return
	 */
	public <T extends ISimpleService> T findService(int portalId, long svrid);
	
	/**
	 * 查找
	 * @param svrid 服务Id.一般roleId
	 * @return
	 */
	public <T extends ISimpleService> T findService(long svrid);
	/**
	 * 查找入口
	 * @param portalId
	 * @return
	 */
	public <T extends ISimpleService> T getPortalService(int portalId);
	/**
	 * 激活服务,是否要按顺序来.
	 * @param svrid
	 * @param isOrderActive
	 * @return
	 */
	public <T extends ISimpleService> T activeService(long svrid, boolean isOrderActive);
	/**
	 * 服务是否已经激活
	 * @param svrid
	 * @return
	 */
	public boolean isActive(long svrid);
	/**
	 * 获取serviceId
	 * @param serviceId
	 * @return
	 */
	public ServiceData getServiceData(long serviceId);
	
	public boolean regMultipleEventListener(IMultipleServiceEvent event);
	
	public void rmMultipleEventListener(int eventid,Object key);
	
	public boolean isSyncPortalCommand(int portalId, String requestCommand);
}	
