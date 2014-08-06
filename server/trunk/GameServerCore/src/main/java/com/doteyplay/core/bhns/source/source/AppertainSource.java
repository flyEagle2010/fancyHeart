package com.doteyplay.core.bhns.source.source;

import org.apache.log4j.Logger;

import com.doteyplay.core.bhns.AbstractSimpleService;
import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.core.bhns.ISimpleService;
import com.doteyplay.core.bhns.ServiceInfo;
import com.doteyplay.core.bhns.source.IEndpointSource;
import com.doteyplay.core.bhns.source.IServiceOption;
import com.doteyplay.core.bhns.source.IServiceSource;
import com.doteyplay.core.bhns.source.options.AppertainServiceOption;
import com.doteyplay.core.bhns.source.options.ClusterServiceOption;
import com.doteyplay.core.bhns.source.options.ServiceEndpointInfo;
import com.doteyplay.core.bhns.source.source.cluster.AppertainEndpointSource;
import com.doteyplay.core.bhns.source.source.cluster.RemoteEndpointSource;

public class AppertainSource implements IServiceSource
{
	private static final Logger logger = Logger.getLogger(AppertainSource.class);

	private AppertainServiceOption serviceOption;
	private IServiceSource masterSource;
	private boolean avialable;

	private IEndpointSource[] endpointList;
	private byte[] endpointIdList;

	public AppertainSource(AppertainServiceOption serviceOption)
	{
		this.serviceOption = serviceOption;
		this.endpointList = null;
		this.endpointIdList = null;
		this.masterSource = null;
		this.avialable = false;
	}

	@Override
	public void initialize()
	{
		if(this.avialable)
			return;

		ServiceInfo masterInfo = BOServiceManager.getServiceInfo(serviceOption.getMasterPortalId());
		if (masterInfo.option().configType() == IServiceOption.CONFIG_TYPE_APPERTAIN)
		{
			this.avialable = false;
			logger
					.error("Appertain service[portalId=" + serviceOption.portalId()
							+ "] can't appoint parent service of appertain[masterPortalId="
							+ serviceOption.getMasterPortalId());
		}
		else
		{
			this.avialable = true;
			masterSource = masterInfo.source();
			if(!masterSource.isAvailable())
			{
				masterSource.initialize();
			}
			endpointList = new IEndpointSource[((ClusterServiceOption) masterInfo.option()).getMaxEndpointId() + 1];

			byte[] tmpIdList = new byte[endpointList.length];
			int cursor = 0;
			IEndpointSource tmpEndpointSource;
			RemoteEndpointSource tmpRemoteEndpointSource = null;
			ServiceEndpointInfo tmpEndpointInfo;
			for (byte i = 0; i < endpointList.length; i++)
			{
				tmpEndpointSource = masterSource.getEndpoint(i);
				if (tmpEndpointSource != null)
				{
					tmpIdList[cursor++] = i;
					if (tmpEndpointSource.isLocalImplemention())
						endpointList[i] = new AppertainEndpointSource(serviceOption, tmpEndpointSource);
					else
						endpointList[i] = new RemoteEndpointSource(serviceOption, i);
					endpointList[i].initialize();
				}
			}
			endpointIdList = new byte[cursor];
			if (cursor > 0)
				System.arraycopy(tmpIdList, 0, endpointIdList, 0, cursor);
		}
	}

	@Override
	public IEndpointSource getEndpoint(byte endpointId)
	{
		if (endpointId >= 0 && endpointId < this.endpointList.length)
			return this.endpointList[endpointId];
		else
			return null;
	}

	@Override
	public byte endpointIdOfserviceId(long elementId)
	{
		return masterSource.endpointIdOfserviceId(elementId);
	}

	@Override
	public boolean isLocalImplemention(byte endpointId)
	{
		return masterSource.isLocalImplemention(endpointId);
	}

	@Override
	public int getVersion(byte endpointId)
	{
		return 0;
	}

	@Override
	public String getConfigFile(byte endpointId)
	{
		return null;
	}

	@Override
	public String getDataBlocks(byte endpointId)
	{
		return null;
	}

	@Override
	public boolean isAvailable()
	{
		return this.avialable;
	}

	@Override
	public AbstractSimpleService<?> createServiceImpInstance(byte endpointId)
	{
		return null;
	}
	
	@Override
	public String postPortalCommand(int portalId,String requestCommand,byte endpointId)
	{
		if (endpointId >= 0 && endpointId < this.endpointList.length)
			return this.endpointList[endpointId].postPortalCommand(portalId,requestCommand);
		else
			return "response status=\"-1\" msg=\"endpoint is invalid\"";
	}

	@Override
	public <T extends ISimpleService> T activeService(long svrid, byte endpointId, boolean isOrderActive)
	{
		return null;
	}

	@Override
	public boolean isActive(long svrid, byte endpointId)
	{
		return masterSource.isActive(svrid, endpointId);
	}

	@Override
	public void destroyService(long svrid)
	{

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ISimpleService> T findService(int portalId, long svrid)
	{
//		T r = null;
		byte tmpLocation = masterSource.endpointIdOfserviceId(svrid);
		if (tmpLocation < 0 || !masterSource.isActive(svrid, tmpLocation))
		{
			return null;
//			tmpLocation = this.masterSource.refreshSvrLocation(svrid, (byte) -1);
		}
		
		if (tmpLocation >= 0 && tmpLocation < this.endpointList.length && this.endpointList[tmpLocation] != null)
			return (T)this.endpointList[tmpLocation].findService(portalId, svrid);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ISimpleService> T getPortalService(int portalId, byte endpointId)
	{
		return (T) this.masterSource.getPortalService(portalId, endpointId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ISimpleService> T getLocalPortalService(int portalId)
	{
		return (T) this.masterSource.getLocalPortalService(portalId);
	}

	@Override
	public void triggerEvent(int eventid, String methodSignature, Object[] args)
	{

	}

	@Override
	public byte[] endpointIdList()
	{
		return endpointIdList;
	}

	/**
	 * 本地插件服务应该都是作为普通服务
	 */
	public IEndpointSource getLocalEndPoint()
	{
		return masterSource.getLocalEndPoint();
	}
	
	@Override
	public int getEndPointSize()
	{
		int size= 0;
		for(IEndpointSource source :endpointList)
			if(source != null)
				size++;
		return size;
	}
	
	public boolean isSyncPortalCommand(int portalId, String requestCommand)
	{
		return masterSource.isSyncPortalCommand(portalId, requestCommand);
	}
}
