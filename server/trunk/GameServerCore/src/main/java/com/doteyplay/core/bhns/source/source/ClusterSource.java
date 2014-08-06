package com.doteyplay.core.bhns.source.source;

import org.apache.log4j.Logger;

import com.doteyplay.core.bhns.AbstractSimpleService;
import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.core.bhns.ISimpleService;
import com.doteyplay.core.bhns.data.ServiceData;
import com.doteyplay.core.bhns.location.ISvrLocationService;
import com.doteyplay.core.bhns.rmi.ProxyBean;
import com.doteyplay.core.bhns.source.IEndpointSource;
import com.doteyplay.core.bhns.source.IServiceSource;
import com.doteyplay.core.bhns.source.options.ClusterServiceOption;
import com.doteyplay.core.bhns.source.options.ServiceEndpointInfo;
import com.doteyplay.core.bhns.source.source.cluster.EndpointSource;
import com.doteyplay.core.bhns.source.source.cluster.RemoteEndpointSource;
import com.doteyplay.luna.client.SynchronicClientManager;
import com.doteyplay.luna.common.message.EncoderMessage;

public class ClusterSource implements IServiceSource
{
	private static final Logger logger = Logger.getLogger(ClusterSource.class);

	private ClusterServiceOption serviceOption;
	private IEndpointSource[] endpointList;
	private byte[] endpointIdList;
	private boolean avialable;

	// private Map<Integer, Byte> elementLocationMap;

	public ClusterSource(ClusterServiceOption serviceOption)
	{
		this.serviceOption = serviceOption;
		this.endpointList = new IEndpointSource[serviceOption
				.getMaxEndpointId() + 1];
		this.endpointIdList = null;
		// this.elementLocationMap = new ConcurrentHashMap<Integer, Byte>();
	}

	public void regEndpoint(byte endpointId, String portalImplClassName,
			String serviceImplClassName, String configfile, String dataBlocks)
	{
		if (endpointId >= 0 && endpointId < this.endpointList.length
				&& portalImplClassName != null
				&& portalImplClassName.length() > 0
				&& serviceImplClassName != null
				&& serviceImplClassName.length() > 0)
		{
			EndpointSource tmpClusterEndpoint = new EndpointSource(
					serviceOption, endpointId, portalImplClassName,
					serviceImplClassName, configfile, dataBlocks);
			if (this.getLocalEndPoint() != null)
			{
				logger.error("error reg two local endpoint in clusterSource",
						new UnsupportedOperationException("two local endpoint"));
				return;
			}
			endpointList[endpointId] = tmpClusterEndpoint;
		} else
		{
			logger.error("register endpoint error:unitId=" + endpointId
					+ ",portalImplClassName=" + portalImplClassName
					+ ",serviceImplClassName=" + serviceImplClassName
					+ ",configfile=" + configfile);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.doteyplay.core.bhns.source.IServiceSource#endpointIdOfserviceId(int)
	 */
	@Override
	public byte endpointIdOfserviceId(long elementId)
	{
		// 临时方案，如果是0的话，取本地
		if (elementId == 0)
		{
			for (IEndpointSource endpoint : endpointList)
			{
				if (endpoint != null && endpoint.isLocalImplemention())
				{
					return endpoint.getEndpointId();
				}
			}
			return -1;
		} else
		{
			ISvrLocationService tmpLocationService = BOServiceManager
					.findLocalDefaultService(ISvrLocationService.PORTAL_ID);
			if (tmpLocationService == null)
				throw new RuntimeException(
						"local tmpLocationService is null!!!!");

			Byte tmpCurLocation = tmpLocationService.getEndpointIdOfserviceId(
					serviceOption.portalId(), elementId);
			if (tmpCurLocation != null)
				return tmpCurLocation.byteValue();
			return -1;
		}
	}

	@Override
	public void initialize()
	{
		if (avialable)
			return;
		byte[] tmpIdList = new byte[endpointList.length];
		int cursor = 0;
		RemoteEndpointSource tmpRemoteEndpointSource = null;
		ServiceEndpointInfo tmpEndpointInfo;
		for (int i = 0; i < endpointList.length; i++)
		{
			tmpEndpointInfo = serviceOption.getEndpointInfo(i);
			if (tmpEndpointInfo != null)
			{
				tmpIdList[cursor++] = (byte)i;
				if (endpointList[i] == null)
				{
					endpointList[i] = new RemoteEndpointSource(serviceOption,
							tmpEndpointInfo.getEndpointId());
				}
				endpointList[i].initialize();
			}
		}
		endpointIdList = new byte[cursor];
		if (cursor > 0)
			System.arraycopy(tmpIdList, 0, endpointIdList, 0, cursor);
		avialable = true;
	}

	@Override
	public boolean isLocalImplemention(byte endpointId)
	{
		if (endpointId >= 0 && endpointId < this.endpointList.length
				&& this.endpointList[endpointId] != null)
			return this.endpointList[endpointId].isLocalImplemention();
		else
			return false;
	}

	public IEndpointSource getLocalEndPoint()
	{
		// 本地应该最多只有一个endSource
		for (IEndpointSource es : endpointList)
			if (es != null && es.isLocalImplemention())
				return es;
		return null;
	}

	@Override
	public int getVersion(byte endpointId)
	{
		if (endpointId >= 0 && endpointId < this.endpointList.length
				&& this.endpointList[endpointId] != null)
			return this.endpointList[endpointId].getVersion();
		else
			return 0;
	}

	@Override
	public String getConfigFile(byte endpointId)
	{
		if (endpointId >= 0 && endpointId < this.endpointList.length
				&& this.endpointList[endpointId] != null)
			return this.endpointList[endpointId].getConfigFile();
		else
			return null;
	}

	@Override
	public String getDataBlocks(byte endpointId)
	{
		if (endpointId >= 0 && endpointId < this.endpointList.length
				&& this.endpointList[endpointId] != null)
			return this.endpointList[endpointId].getDataBlocks();
		else
			return null;
	}

	@Override
	public boolean isAvailable()
	{
		return avialable;
	}

	@Override
	public AbstractSimpleService<?> createServiceImpInstance(byte endpointId)
	{
		if (endpointId >= 0 && endpointId < this.endpointList.length
				&& this.endpointList[endpointId] != null)
			return this.endpointList[endpointId].createServiceImpInstance();
		else
			return null;
	}

	@Override
	public String postPortalCommand(int portalId, String requestCommand,
			byte endpointId)
	{
		if (endpointId >= 0 && endpointId < this.endpointList.length
				&& this.endpointList[endpointId] != null)
			return this.endpointList[endpointId].postPortalCommand(portalId,
					requestCommand);
		else
			return "response status=\"-1\" msg=\"endpoint is invalid\"";
	}

	public boolean isSyncPortalCommand(int portalId, String requestCommand)
	{
		throw new UnsupportedOperationException(
				"isSyncPortalCommand unsupport cluster");
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ISimpleService> T activeService(long svrid,
			byte endpointId, boolean isOrderActive)
	{
		T t = null;

		ISvrLocationService tmpLocationService = BOServiceManager
				.findLocalDefaultService(ISvrLocationService.PORTAL_ID);
		if (tmpLocationService == null)
			throw new RuntimeException("local tmpLocationService is null!!!!");

		Byte tmpLocation = tmpLocationService.getEndpointIdOfserviceId(
				this.serviceOption.portalId(), svrid);
		byte tmpOldLocation = (tmpLocation != null) ? tmpLocation.byteValue()
				: (byte) -1;

		if (endpointId == tmpOldLocation && endpointId != -1)
		{
			t = (T) this.endpointList[endpointId].findService(svrid);
			if (t.isValid())
				return t;
		}

		ServiceData data = null;
		if (tmpOldLocation >= 0 && tmpOldLocation < this.endpointList.length
				&& this.endpointList[tmpOldLocation] != null)
		{
			data = this.endpointList[tmpOldLocation].getServiceData(svrid);
			this.endpointList[tmpOldLocation].destroyService(svrid);
		}

		if (endpointId >= 0 && endpointId < this.endpointList.length
				&& this.endpointList[endpointId] != null)
		{
			if (this.endpointList[endpointId].supportCluster()
					&& !this.endpointList[endpointId].isLocalImplemention())
			{
				RemoteEndpointSource source = (RemoteEndpointSource) this.endpointList[endpointId];
				source.clearRemoteProxy(svrid);
			}
			t = (T) this.endpointList[endpointId].activeService(svrid,
					isOrderActive);
		}

		if (data != null && t != null)
			t.bindServiceData(data);
		return t;
	}

	@Override
	public boolean isActive(long svrid, byte endpointId)
	{
		if (endpointId >= 0 && endpointId < this.endpointList.length
				&& this.endpointList[endpointId] != null)
			return this.endpointList[endpointId].isActive(svrid);
		else
			return false;
	}

	@Override
	public void destroyService(long svrid)
	{
		for (IEndpointSource endpoint : endpointList)
		{
			if (endpoint != null && endpoint.isLocalImplemention())
				endpoint.destroyService(svrid);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ISimpleService> T findService(int portalId, long svrid)
	{
		T r = null;
		byte tmpLocation = -1;
		byte tmpFixLocation = -1;
		ISvrLocationService tmpLocationService = BOServiceManager
				.findLocalDefaultService(ISvrLocationService.PORTAL_ID);
		if (tmpLocationService == null)
			throw new RuntimeException("local tmpLocationService is null!!!!");

		Byte tmpCurLocation = tmpLocationService.getEndpointIdOfserviceId(
				portalId, svrid);
		if (tmpCurLocation != null)
		{
			tmpLocation = tmpCurLocation.byteValue();
			if (tmpLocation >= 0 && tmpLocation < this.endpointList.length
					&& this.endpointList[tmpLocation] != null)
			{
				r = (T) this.endpointList[tmpLocation].findService(portalId,
						svrid);
			} else
			{
				logger.error("注意：可能是异步未激活完成就直接发生调用关系，必须保证当前线程激活后使用的代理类为激活服务返回的值处理，或者在激活过程init方法中进行处理");
			}
		}
		return r;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ISimpleService> T getPortalService(int portalId,
			byte endpointId)
	{
		if (endpointId >= 0 && endpointId < this.endpointList.length
				&& this.endpointList[endpointId] != null)
		{
			return (T) this.endpointList[endpointId].getPortalService(portalId);
		} else
			return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ISimpleService> T getLocalPortalService(int portalId)
	{
		for (IEndpointSource tmpEndpointSource : this.endpointList)
		{
			if (tmpEndpointSource != null
					&& tmpEndpointSource.isLocalImplemention())
				return (T) tmpEndpointSource.getPortalService(portalId);
		}
		return null;
	}

	@Override
	public void triggerEvent(int eventid, String methodSignature, Object[] args)
	{
		for (IEndpointSource endpoint : endpointList)
		{
			if (endpoint != null)
			{
				if (endpoint.isLocalImplemention())
					endpoint.triggerEvent(eventid, methodSignature, args);
				else
				{
					RemoteEndpointSource tmpRemoteEndpointSource = (RemoteEndpointSource) endpoint;
					SynchronicClientManager tmpNet = tmpRemoteEndpointSource
							.getNetChannel();
					if (tmpNet != null)
					{
						ProxyBean pb = new ProxyBean();
						pb.setMethodSignature(methodSignature);
						pb.setParameter(args);
						pb.setPortalId(serviceOption.portalId());
						EncoderMessage buffer = new EncoderMessage((short) 0,
								true);
						buffer.setRoleId(eventid);
						pb.encode(buffer.getBuffer());
						tmpNet.asynInvoke(buffer);
					}
				}
			}
		}
	}

	@Override
	public byte[] endpointIdList()
	{
		return endpointIdList;
	}

	@Override
	public int getEndPointSize()
	{
		int size = 0;
		for (IEndpointSource source : endpointList)
			if (source != null)
				size++;
		return size;
	}

}
