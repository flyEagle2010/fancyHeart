package com.doteyplay.core.bhns;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.core.bhns.data.ServiceData;
import com.doteyplay.core.bhns.event.IEventSource;
import com.doteyplay.core.bhns.event.IMultipleServiceEvent;
import com.doteyplay.core.bhns.event.IServiceEvent;
import com.doteyplay.core.bhns.gateway.IGateWayService;
import com.doteyplay.core.bhns.location.ISvrLocationService;
import com.doteyplay.core.messageagent.ITransferAgent;
import com.doteyplay.core.messageagent.TransferAgent;
import com.doteyplay.net.message.AbstractMessage;

/**
 * 本地服务实现的基类
 * 
 * @author
 * 
 */
public abstract class AbstractSimpleService<T extends ISimpleService>
		implements ISimpleService, ILocalService, ITransferAgent, IEventSource
{
	// 服务对应key
	private long _serviceId;
	private byte _endpointId;
	private ServiceData _serviceData;
	private int _version;
	private IServiceAssembly _servicePortal;
	private IServiceCreator _serviceCreator;
	private IMethodCollection _methodMap;
	private long sessionId;

	@Override
	public void init(IServiceAssembly serviceportal,
			IServiceCreator servicecreator, long serviceId, byte endpointId,
			IMethodCollection methodmap, int version)
	{
		this._servicePortal = serviceportal;
		this._serviceCreator = servicecreator;
		this._serviceId = serviceId;
		this._endpointId = endpointId;
		this._version = version;
		this._methodMap = methodmap;
	}

	public boolean isConfiged()
	{
		return (this._servicePortal != null);
	}

	public boolean bindServiceData(ServiceData servicedata)
	{
		if (servicedata == null)
			return false;
		if (this._serviceData != null)
			_serviceCreator.resetServiceData(this, servicedata);
		this._serviceData = servicedata;
		return true;
	}

	@Override
	public long getServiceId()
	{
		return _serviceId;
	}

	@Override
	public byte getEndpointId()
	{
		return _endpointId;
	}

	@Override
	public int getVersion()
	{
		return _version;
	}

	protected void postLog(String logcontent)
	{

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T1 extends ISimpleService> T1 getService(int portalId)
	{
		return (T1) getService(portalId, _serviceId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T1 extends ISimpleService> T1 getService(int portalId, long serviceId)
	{
		if (_servicePortal == null)
			return null;

		return (T1) _servicePortal.getService(portalId, serviceId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T1 extends ISimpleService> T1 getDefaultService(int portalid,
			byte endpointid)
	{
		if (_servicePortal == null)
			return null;

		return (T1) _servicePortal.getDefaultService(portalid, endpointid);
	}

	@Override
	public Object invokeMethod(String methodSignature, Object[] args)
	{
		if (_methodMap != null)
			return _methodMap.invokeMethod(methodSignature, this, args);
		else
			return null;
	}

	@Override
	public boolean isValid()
	{
		return (_servicePortal != null && _methodMap != null);
	}

	@Override
	public boolean isProxy()
	{
		return false;
	}

	@Override
	public boolean isSingleton()
	{
		return false;
	}

	protected ISimpleService applySubService(int portalid)
	{
		return null;
	}

	protected ISimpleService applyPluginService(int portalid)
	{
		return null;
	}

	public ISimpleService extractService(int portalid)
	{
		if (portalid == this.getPortalId())
			return this;
		else
		{
			ISimpleService tmpService = applySubService(portalid);
			if (tmpService != null)
			{
				if (!tmpService.isValid() && this._serviceCreator != null)
				{
					this._serviceCreator.configInstance(
							(ILocalService) tmpService, this.getServiceId());
				}
				return tmpService;
			}
			tmpService = applyPluginService(portalid);
			if (tmpService != null)
			{
				if (!tmpService.isValid() && this._serviceCreator != null)
				{
					((IServicePlugin) tmpService).bindPipe(this);
				}
				return tmpService;
			}
			return tmpService;
		}
	}

	public Collection<ISimpleService> portalActiveServices()
	{
		if (this._servicePortal != null)
			return this._servicePortal.getActiveServices();
		else
			return null;
	}

	public void sendMessage(AbstractMessage message)
	{
		this.sendMessage(getServiceId(), message);
	}

	public void sendMessageBySessionId(long sessionId, AbstractMessage message)
	{
		byte endpointId = (byte) getGatewayEndpointBySessionId(sessionId);
		IGateWayService gateWayService = BOServiceManager.findDefaultService(
				IGateWayService.PORTAL_ID, endpointId);

		IoBuffer buffer = message.encodeIoBuffer();
		int byteLength = buffer.limit();
		byte[] tmpMessageBytes = new byte[byteLength];
		buffer.get(tmpMessageBytes, 0, byteLength);

		gateWayService.sendMessage(tmpMessageBytes, sessionId);
	}

	public static byte getGatewayEndpointBySessionId(long sessionId)
	{
		return (byte) (sessionId >>> 56);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.doteyplay.core.messageagent.ITransferAgent#broadcastMessage(java.util.
	 * List, com.doteyplay.net.message.AbstractMessage)
	 */
	/**
	 * @param roleIdLst
	 * @param message
	 */
	@Override
	public void broadcastMessage(List<Long> roleIdLst,
			AbstractMessage message)
	{
		TransferAgent.getInstance().broadcastMessage(roleIdLst, message);
	}

	@Override
	public void sendMessage(long roleid, AbstractMessage message)
	{
		TransferAgent.getInstance().sendMessage(roleid, message);
	}

	// Data**********************************************
	public Serializable getData(String datatag)
	{
		return (_serviceData != null) ? _serviceData.getData(datatag) : null;
	}

	public void putData(String datatag, Serializable data)
	{
		if (_serviceData != null)
			_serviceData.putData(datatag, data);
	}

	public void removeData(String datatag)
	{
		if (_serviceData != null)
			_serviceData.removeData(datatag);
	}

	public void clearData()
	{
		if (_serviceData != null)
			_serviceData.clearData();
	}

	public Serializable getData(int datagroup, String datakey)
	{
		return (_serviceData != null) ? _serviceData
				.getData(datagroup, datakey) : null;
	}

	public void putData(int datagroup, String datakey, Serializable data)
	{
		if (_serviceData != null)
			_serviceData.putData(datagroup, datakey, data);
	}

	public void removeData(int datagroup, String datakey)
	{
		if (_serviceData != null)
			_serviceData.removeData(datagroup, datakey);
	}

	public void clearData(int datagroup)
	{
		if (_serviceData != null)
			_serviceData.clearData(datagroup);
	}

	public void clearAllData()
	{
		if (_serviceData != null)
			_serviceData.clearAllData();
	}

	public boolean isDataExist(String datatag)
	{
		if (_serviceData != null)
			return _serviceData.isDataExist(datatag);
		else
			return false;
	}

	public boolean isDataExist(int datagroup)
	{
		if (_serviceData != null)
			return _serviceData.isDataExist(datagroup);
		else
			return false;
	}

	public Iterator<Serializable> getDataIterator()
	{
		if (_serviceData != null)
			return _serviceData.getDataIterator();
		else
			return null;
	}

	public Iterator<Serializable> getDataIterator(int datagroup)
	{
		if (_serviceData != null)
			return _serviceData.getDataIterator(datagroup);
		else
			return null;
	}

	/**
	 * 跨服没用这个方法,暂时无用
	 */
	public boolean transfeDataToCluster()
	{
		if (_serviceData == null)
			return false;
		ISimpleService clusterService = BOServiceManager.findService(
				this.getPortalId(), this.getServiceId());
		if (!clusterService.isValid())
			return false;
		return clusterService.bindServiceData(_serviceData);
	}

	public long getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(long sessionId)
	{
		this.sessionId = sessionId;
	}

	// Event*********************************************
	@SuppressWarnings("unchecked")
	public <T1 extends IServiceEvent> T1 createSingletonEvent(int eventid)
	{
		return (T1) BOServiceManager.createSingletonEvent(eventid);
	}

	@SuppressWarnings("unchecked")
	public <T1 extends IMultipleServiceEvent> T1 createMultipleEvent(
			int eventid, Object key)
	{
		return (T1) BOServiceManager.createMultipleEvent(eventid, key);
	}

	public void messageReceived(byte[] messageBytes,long sessionId)
	{
		IoBuffer buffer = IoBuffer.wrap(messageBytes);
		_servicePortal.getPipline().dispatchAction(this, buffer,sessionId);
	}
}
