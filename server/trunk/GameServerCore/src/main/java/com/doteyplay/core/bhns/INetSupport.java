package com.doteyplay.core.bhns;

import com.doteyplay.luna.client.SynchronicClientManager;
/**
 * 网络通道获取
 * @author 
 *
 */
//TODO：需要优化mina
public interface INetSupport
{
	public SynchronicClientManager getNetChannel();
	
	public SynchronicClientManager relocateNetChannel();

}
