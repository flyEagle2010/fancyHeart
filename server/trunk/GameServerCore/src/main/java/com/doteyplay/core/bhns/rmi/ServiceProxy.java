package com.doteyplay.core.bhns.rmi;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.core.bhns.IMethodCollection;
import com.doteyplay.core.bhns.INetSupport;
import com.doteyplay.core.bhns.ISimpleService;
import com.doteyplay.core.bhns.ServiceInfo;
import com.doteyplay.core.bhns.location.ISvrLocationService;
import com.doteyplay.core.bhns.portal.SimpleMethodCollection;
import com.doteyplay.core.bhns.source.IEndpointSource;
import com.doteyplay.core.monitor.Monitor;
import com.doteyplay.game.BOConst;
import com.doteyplay.game.config.GameConfig;
import com.doteyplay.luna.client.SynchronicClientManager;
import com.doteyplay.luna.common.message.DecoderMessage;
import com.doteyplay.luna.common.message.EncoderMessage;

/**
 * 服务代理
 * 
 * @author
 * 
 */
public class ServiceProxy<T extends ISimpleService>
{
	private int portalId;
	private byte endpointId;
	private INetSupport netSupport;
	private Constructor<?> boProxyConstructor;
	private IMethodCollection methodMap;
	// TODO:远程BO代理的缓存，需要增加延时内存回收机制，将长期无人使用的代理对象清除
	private Map<Long, ServiceProxyStatus<T>> serviceProxyCache;
	private boolean isSingleton;
	private ServiceProxyStatus<T> singletonServiceProxy;
	private ServiceProxyStatus<T> portalServiceProxy;

	public ServiceProxy(int portalid, byte endpointId, Class<T> interfaceClass,
			INetSupport netSupport)
	{
		this.netSupport = netSupport;
		this.endpointId = endpointId;
		this.portalId = portalid;
		serviceProxyCache = new ConcurrentHashMap<Long, ServiceProxyStatus<T>>();
		methodMap = new SimpleMethodCollection(interfaceClass);
		Class<?> clazzProxy = Proxy.getProxyClass(
				interfaceClass.getClassLoader(), interfaceClass);
		try
		{
			boProxyConstructor = clazzProxy
					.getConstructor(InvocationHandler.class);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		this.isSingleton = false;
		this.singletonServiceProxy = null;
	}

	private ServiceProxyStatus<T> createProxyStatus(long serviceId)
	{
		ServiceProxyStatus<T> tmpServiceProxyStatus = new ServiceProxyStatus<T>();
		try
		{
			@SuppressWarnings("unchecked")
			T tmpEntrance = (T) boProxyConstructor
					.newInstance(new ServiceProxyEntrance(portalId, this,
							serviceId));
			tmpServiceProxyStatus.bindEntrance(tmpEntrance);
			tmpServiceProxyStatus.setStatus(ServiceProxyStatus.INACTIVE_STATUS);
			return tmpServiceProxyStatus;
		} catch (Exception e)
		{
			tmpServiceProxyStatus
					.setStatus(ServiceProxyStatus.UNAVAILABLE_STATUS);
			e.printStackTrace();
		}
		return null;
	}

	private Object invokePortalMethodProxy(String methodname,
			byte intervalflag, int portalid, byte endpointid, long serviceId,
			Object[] paramters)
	{
		SynchronicClientManager tmpNet = netSupport.getNetChannel();
		if (tmpNet != null)
		{
			ProxyBean tmpPB = new ProxyBean();
			tmpPB.setInternalFlag(intervalflag);
			tmpPB.setMethodSignature(methodname);
			tmpPB.setPortalId(portalid);
			tmpPB.setEndpointId(endpointid);
			tmpPB.setParameter(paramters);

			EncoderMessage buffer = new EncoderMessage((short) 0, true);
			buffer.setRoleId(serviceId);
			tmpPB.encode(buffer.getBuffer());
			Monitor.recordSendCallIoSize(buffer.getBuffRealLength());

			boolean isSyncRequest = false;
			if (methodname.equals("doPortalCommand"))
			{
				if (paramters[1] != null
						&& paramters[1].toString().toLowerCase()
								.indexOf("sync") > -1)
					isSyncRequest = true;
				// 这里做一下对于坊间服务激活的特殊处理
			} else if (methodname.equals("isActive"))
			{
				isSyncRequest = true;
			} else
			{
				if (methodMap.isSyncMethod(methodname))
					isSyncRequest = true;
			}

			if (isSyncRequest)
			{
				DecoderMessage result = (DecoderMessage) tmpNet
						.synInvoke(buffer);
				Monitor.recordGetReturnIoSize(result.getMessageLength());
				Object o = result.getObject();
				return o;
			} else
			{
				tmpNet.asynInvoke(buffer);
				Monitor.recordGetReturnIoSize(0);
				return null;
			}
		} else
			return null;
	}

	public void clearRemoteProxy(long serviceId)
	{
		serviceProxyCache.remove(serviceId);
	}

	public boolean isActive(long serviceId)
	{
		if (serviceId <= 0)
			return false;

		boolean isActiveSuc = false;
		try
		{
			ServiceProxyStatus<T> tmpServiceProxyStatus = isSingleton ? singletonServiceProxy
					: serviceProxyCache.get(serviceId);
			if (tmpServiceProxyStatus == null)
			{
				// 插入一个激活过程
				tmpServiceProxyStatus = createProxyStatus(serviceId);

				Object tmpR = invokePortalMethodProxy("isActive",
						ProxyConst.FLAG_SERVICE_IS_ACTIVE, this.portalId,
						this.endpointId, serviceId, null);
				if (tmpR != null)
					isActiveSuc = (Boolean) tmpR;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return isActiveSuc;
	}

	public ISimpleService getServiceProxy(long serviceId)
	{
		if (serviceId <= 0)
			return null;

		try
		{
			boolean isActiveSuc = false;
			ServiceProxyStatus<T> tmpServiceProxyStatus = isSingleton ? singletonServiceProxy
					: serviceProxyCache.get(serviceId);
			if (tmpServiceProxyStatus == null)
			{
				// 插入一个激活过程
				tmpServiceProxyStatus = createProxyStatus(serviceId);

				Object tmpR = invokePortalMethodProxy("isActive",
						ProxyConst.FLAG_SERVICE_IS_ACTIVE, this.portalId,
						this.endpointId, serviceId, null);
				if (tmpR != null)
					isActiveSuc = (Boolean) tmpR;

				if (isActiveSuc)
				{
					tmpServiceProxyStatus
							.setStatus(ServiceProxyStatus.ACTIVE_STATUS);
					if (tmpServiceProxyStatus.isEntranceSingleton())
					{
						isSingleton = true;
						singletonServiceProxy = tmpServiceProxyStatus;
					} else
						serviceProxyCache.put(serviceId, tmpServiceProxyStatus);
				} else
				{
					serviceProxyCache.put(serviceId, tmpServiceProxyStatus);
				}
			}
			if (tmpServiceProxyStatus != null
					&& tmpServiceProxyStatus.getStatus() == ServiceProxyStatus.ACTIVE_STATUS)
				return tmpServiceProxyStatus.getEntrance();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public String doPortalCommand(int portalId, String requestCommand)
	{
		try
		{
			Object tmpR = invokePortalMethodProxy("doPortalCommand",
					ProxyConst.FLAG_SERVICE_PORTAL_COMMAND, this.portalId,
					this.endpointId, 0,
					new Object[] { portalId, requestCommand });
			return (String) tmpR;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public T activeServiceProxy(long serviceId, boolean isOrderActive)
	{
		if (serviceId <= 0)
			return null;

		try
		{
			boolean isActiveSuc = false;
			ServiceProxyStatus<T> tmpServiceProxyStatus = isSingleton ? singletonServiceProxy
					: serviceProxyCache.get(serviceId);
			if (tmpServiceProxyStatus == null)
			{
				// 插入一个激活过程
				tmpServiceProxyStatus = createProxyStatus(serviceId);

				Object tmpR = invokePortalMethodProxy("activeNofity",
						ProxyConst.FLAG_SERVICE_ACTIVE_NOTIFY, this.portalId,
						this.endpointId, serviceId,
						new Object[] { isOrderActive });
				// if (tmpR != null)
				// isActiveSuc = (Boolean) tmpR;
				isActiveSuc = true;

				if (isActiveSuc)
				{
					tmpServiceProxyStatus
							.setStatus(ServiceProxyStatus.ACTIVE_STATUS);
					if (tmpServiceProxyStatus.isEntranceSingleton())
					{
						isSingleton = true;
						singletonServiceProxy = tmpServiceProxyStatus;
					} else
						serviceProxyCache.put(serviceId, tmpServiceProxyStatus);
				} else
				{
					serviceProxyCache.put(serviceId, tmpServiceProxyStatus);
				}
			}

			if (tmpServiceProxyStatus != null
					&& tmpServiceProxyStatus.getStatus() == ServiceProxyStatus.ACTIVE_STATUS)
			{
				ISvrLocationService locService = BOServiceManager
						.findLocalDefaultService(ISvrLocationService.PORTAL_ID);
				locService.changeLocationOfserviceId(this.portalId, serviceId,
						this.endpointId);
				return tmpServiceProxyStatus.getEntrance();
			} else
				return null;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public void clearServiceProxy(long serviceId)
	{
		if (serviceId <= 0 || isSingleton)
			return;

		try
		{
			ServiceProxyStatus<T> tmpServiceProxyStatus = serviceProxyCache
					.get(serviceId);
			if (tmpServiceProxyStatus != null
					&& tmpServiceProxyStatus.getStatus() == ServiceProxyStatus.ACTIVE_STATUS)
			{
				invokePortalMethodProxy("clearService",
						ProxyConst.FLAG_SERVICE_DESTROY, this.portalId,
						this.endpointId, serviceId, null);
				tmpServiceProxyStatus.bindEntrance(null);
				serviceProxyCache.remove(serviceId);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public T getPortalServiceProxy()
	{
		try
		{
			// PortalServiceProxy自动激活
			if (portalServiceProxy == null)
			{
				portalServiceProxy = this.createProxyStatus(0);
				portalServiceProxy.setStatus(ServiceProxyStatus.ACTIVE_STATUS);
			}
			return portalServiceProxy.getEntrance();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;

	}

	public Object doService(int portalid, byte internalFlag, long serviceId,
			Method method, Object[] args)
	{
		if (this.netSupport != null && method != null)
			return invokePortalMethodProxy(
					this.methodMap.getMethodSignature(method), internalFlag,
					portalid, this.endpointId, serviceId, args);
		else
			return null;
	}

	public int getPortalId()
	{
		return portalId;
	}

}
