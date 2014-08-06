package com.doteyplay.core.bhns.portal;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.doteyplay.core.bhns.AbstractSimpleService;
import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.core.bhns.ILocalService;
import com.doteyplay.core.bhns.IMethodCollection;
import com.doteyplay.core.bhns.IPortalCommandHandler;
import com.doteyplay.core.bhns.IServiceAssembly;
import com.doteyplay.core.bhns.IServiceCreator;
import com.doteyplay.core.bhns.IServicePortal;
import com.doteyplay.core.bhns.ISimpleService;
import com.doteyplay.core.bhns.ServiceInfo;
import com.doteyplay.core.bhns.data.ServiceData;
import com.doteyplay.core.bhns.event.IEventListener;
import com.doteyplay.core.bhns.event.IMultipleEventListener;
import com.doteyplay.core.bhns.event.IMultipleServiceEvent;
import com.doteyplay.core.bhns.event.IServiceEvent;
import com.doteyplay.core.bhns.event.LocalMultipleEventListener;
import com.doteyplay.core.bhns.event.LocalSingletonEventListener;
import com.doteyplay.core.bhns.source.IEndpointSource;
import com.doteyplay.core.bhns.source.IServiceOption;
import com.doteyplay.core.util.KvMap;
import com.doteyplay.net.protocol.IServicePipeline;

/**
 * 本地的服务入口
 * 
 * @author
 * 
 */
public abstract class AbstractLocalPortal<T extends ISimpleService> implements
		IServicePortal<T>, IServiceAssembly, IServiceCreator, IEventListener,
		IMultipleEventListener
{

	private static boolean DEBUG = false;

	private static final Logger logger = Logger
			.getLogger(AbstractLocalPortal.class);

	public static final String GET_ACTIVED_SERVICES_CMD = "GET_ACTIVED_SERVICES_CMD";

	public static final String ACTIVED_SERVICES_CMD = "ACTIVE_SERVICES_CMD";

	protected IServiceOption option;
	protected IEndpointSource source;
	protected byte endpointId;

	private LocalSingletonEventListener localSingletonEventListener;
	private LocalMultipleEventListener localMultipleEventListener;

	private AbstractSimpleService<T> portalService;
	private ConcurrentHashMap<Long, AbstractSimpleService<T>> serviceImplMap = new ConcurrentHashMap<Long, AbstractSimpleService<T>>();
	private ConcurrentHashMap<Long, ServiceData> dataMap = new ConcurrentHashMap<Long, ServiceData>();
	private PortalCommandBridge portalCommandBridge;
	private IServicePipeline servicePipeline;

	public AbstractLocalPortal(IServiceOption option, IEndpointSource source)
	{
		this.option = option;
		this.source = source;
		this.endpointId = source.getEndpointId();
		localSingletonEventListener = new LocalSingletonEventListener(this);
		localMultipleEventListener = new LocalMultipleEventListener();
		this.portalCommandBridge = null;
	}

	protected void bindPortalService(AbstractSimpleService<T> portalservice)
	{
		this.portalService = portalservice;
		this.portalService.init(this, this, 0, source.getEndpointId(),
				option.getMethodMap(), 0);
	}

	public void bindPortoalCommandHandler(IPortalCommandHandler handler)
	{
		if (portalCommandBridge == null)
		{
			portalCommandBridge = new PortalCommandBridge(this);
		}
		portalCommandBridge.bindHandler(handler);
	}

	public void bindPipline(IServicePipeline pipeline)
	{
		this.servicePipeline = pipeline;
	}

	/**
	 * 获取消息分发
	 * 
	 * @return
	 */
	public IServicePipeline getPipline()
	{
		return servicePipeline;
	}

	// IBOService******************
	@Override
	public int getPortalId()
	{
		return option.portalId();
	}

	@Override
	public byte getEndpointId()
	{
		return this.endpointId;
	}

	@Override
	public ISimpleService findService(long serviceId)
	{
		return findService(this.getPortalId(), serviceId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ISimpleService findService(int portalId, long serviceId)
	{
		AbstractSimpleService<T> tmpImpl = serviceImplMap.get(serviceId);
		// 检查版本匹配
		if (tmpImpl != null && portalId == this.getPortalId()
				&& tmpImpl.getVersion() < this.source.getVersion())
		{
			// 版本过低重新创建
			this.releaseService((T) tmpImpl);
			tmpImpl = null;
			tmpImpl = safeNewInstance(serviceId);
			if (tmpImpl != null)
				serviceImplMap.put(serviceId, tmpImpl);
		}
		ISimpleService r = null;
		if (tmpImpl != null)
		{
			r = tmpImpl.extractService(portalId);
		}
		if (r == null)
		{
			ServiceInfo tmpServiceInfo = BOServiceManager
					.getServiceInfo(portalId);
			if (tmpServiceInfo != null)
			{
				if (DEBUG)
				{
					r = null;
				} else
				{
					logger.error("未找到当前服务，给一个dummy服务.位置:\r\n"
							+ createCurrentTread());
					r = tmpServiceInfo.option().getDummyService();
				}
			}
		}
		return r;
	}

	private String createCurrentTread()
	{
		StackTraceElement[] es = Thread.currentThread().getStackTrace();
		StringBuffer buffer = new StringBuffer();
		int i = 0;
		for (StackTraceElement e : es)
		{
			i++;
			if (i > 2)
				buffer.append(e).append("\r\n");
			if (i >= 11) // 就输出8行
				break;
		}
		buffer.append("...\r\n");
		return buffer.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T activeService(long serviceId, boolean isOrderActive)
	{
		AbstractSimpleService<T> tmpImpl = serviceImplMap.get(serviceId);

		// 如果为空重新创建
		if (tmpImpl == null)
		{
			tmpImpl = safeNewInstance(serviceId);
			if (tmpImpl != null)
			{
				serviceImplMap.put(serviceId, tmpImpl);
			}
		}
		return (T) tmpImpl;
	}

	@Override
	public boolean isActive(long serviceId)
	{
		return (serviceImplMap.containsKey(serviceId));
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getPortalService()
	{
		if (portalService != null)
			return (T) this.portalService;
		else
			return (T) this.option.getDummyService();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void destroyService(long serviceId)
	{
		AbstractSimpleService<T> tmpImpl = serviceImplMap.get(serviceId);
		if (tmpImpl != null)
		{
			this.releaseService((T) tmpImpl);
			serviceImplMap.remove(serviceId);
			tmpImpl = null;
		}
	}

	@Override
	public ISimpleService getService(int portalId, long serviceId)
	{
		if (portalId == option.portalId())
		{
			return findService(portalId, serviceId);
		} else
		{
			ServiceInfo tmpServiceInfo = BOServiceManager
					.getServiceInfo(portalId);
			if (tmpServiceInfo != null)
			{
				return tmpServiceInfo.findService(portalId, serviceId);
			}
			return null;
		}
	}

	@Override
	public ISimpleService activeService(int portalId, long serviceId,
			byte endpointId, boolean isOrderActive)
	{
		if (portalId == option.portalId())
			return activeService(serviceId, isOrderActive);
		else
		{
			ServiceInfo tmpBHNS = BOServiceManager.getServiceInfo(portalId);
			if (tmpBHNS != null)
			{
				return tmpBHNS.activeService(serviceId, endpointId,isOrderActive);
			}
			return null;
		}
	}

	@Override
	public ISimpleService getDefaultService(int portalid, byte endpointId)
	{
		if (portalid == option.portalId())
			return getPortalService();
		else
		{
			ServiceInfo tmpBHNS = BOServiceManager.getServiceInfo(portalid);
			if (tmpBHNS != null)
			{
				return tmpBHNS.getPortalService(portalid, endpointId);
			}
			return null;
		}
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	@Override
	public Collection<ISimpleService> getActiveServices()
	{
		return (Collection) this.serviceImplMap.values();
	}

	protected AbstractSimpleService<?> createServiceInstance()
	{
		return this.source.createServiceImpInstance();
	}

	@SuppressWarnings("rawtypes")
	private AbstractSimpleService safeNewInstance(long serviceId)
	{
		AbstractSimpleService c = this.source.createServiceImpInstance();
		configInstance(c, serviceId);
		return c;
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public void configInstance(ILocalService c, long serviceId)
	{
		if (c != null)
		{
			IMethodCollection tmpIMethodCollection = null;
			if (c.getPortalId() == this.getPortalId())
				tmpIMethodCollection = option.getMethodMap();
			else
			{
				ServiceInfo tmpServiceInfo = BOServiceManager.getServiceInfo(c
						.getPortalId());
				if (tmpServiceInfo != null)
					tmpIMethodCollection = tmpServiceInfo.option()
							.getMethodMap();
			}

			if (tmpIMethodCollection != null)
			{
				// 子服务也使用主服务的endpointId和version
				c.init(this, this, serviceId, source.getEndpointId(),
						tmpIMethodCollection, source.getVersion());
				ServiceData tmpServiceData = dataMap.get(serviceId);
				if (tmpServiceData == null)
				{
					tmpServiceData = new ServiceData();
					dataMap.put(serviceId, tmpServiceData);
				}
				((AbstractSimpleService) c).bindServiceData(tmpServiceData);
				initializeService(((AbstractSimpleService) c));
			}
		}
	}

	public void resetServiceData(ILocalService c, ServiceData newServiceData)
	{
		if (c == null || newServiceData == null)
			return;
		long serviceId = c.getServiceId();
		dataMap.put(serviceId, newServiceData);
	}

	public ServiceData getServiceData(long serviceId)
	{
		AbstractSimpleService<?> service = this.serviceImplMap.get(serviceId);
		ServiceData serviceData = this.dataMap.get(serviceId);
		if (service != null && serviceData != null)
			return serviceData;
		return null;
	}

	@Override
	public void destroyService(int portalid, long serviceId)
	{
		if (portalid == option.portalId())
			this.destroyService(serviceId);
	}

	@Override
	public boolean isProxy()
	{
		return false;
	}

	@Override
	public void triggerEvent(int eventid, String methodSignature, Object[] args)
	{
		localSingletonEventListener
				.triggerEvent(eventid, methodSignature, args);
	}

	@Override
	public void triggerMultipleEvent(int eventid, String methodSignature,
			Object key, Object[] args)
	{
		localMultipleEventListener.triggerMultipleEvent(eventid,
				methodSignature, key, args);
	}

	public void rmMultipleTrigger(int eventid, Object key)
	{
		localMultipleEventListener.rmMultipleTrigger(eventid, key);
	}

	protected void regSingletonTrigger(IServiceEvent eventImpl)
	{
		if (eventImpl != null)
		{
			localSingletonEventListener.regTrigger(eventImpl);
		}
	}

	public void regMultipleTrigger(IMultipleServiceEvent eventImpl)
	{
		if (eventImpl != null)
		{
			localMultipleEventListener.regTrigger(eventImpl);
		}
	}

	@Override
	public String doPortalCommand(int portalId, String requestCommand)
	{
		if (GET_ACTIVED_SERVICES_CMD.equals(requestCommand))
		{
			StringBuffer buf = new StringBuffer();
			for (AbstractSimpleService service : serviceImplMap.values())
			{
				buf.append(service.getServiceId()).append(",");
			}
			buf.deleteCharAt(buf.length() - 1);
			return buf.toString();
		} else if (requestCommand != null
				&& requestCommand.startsWith(ACTIVED_SERVICES_CMD))
		{
			String[] serviceIdStrArray = requestCommand.substring(
					ACTIVED_SERVICES_CMD.length()).split(",");

			// FIXME:切服务器的时候需要补上将消息代理服务器信息发过去
			for (String serviceIdStr : serviceIdStrArray)
			{
				activeService(Integer.parseInt(serviceIdStr), false);
			}
			return null;
		}

		if (portalId == this.getPortalId())
		{
			if (this.portalCommandBridge == null)
				return null;

			KvMap tmpRequest = KvMap.createFromData(requestCommand);
			if (tmpRequest == null)
				return null;

			KvMap tmpResponse = KvMap.create(tmpRequest.getDataName());
			portalCommandBridge.invokeMethod(tmpRequest.getDataName(),
					tmpRequest, tmpResponse);
			return tmpResponse.toString();
		} else
			return doSubPortalCommand(portalId, requestCommand);
	}

	public boolean isSyncPortalCommand(int portalId, String requestCommand)
	{
		if (GET_ACTIVED_SERVICES_CMD.equals(requestCommand))
		{
			return true; //FIXME:激活是同步请求
		} else if (requestCommand != null
				&& requestCommand.startsWith(ACTIVED_SERVICES_CMD))
		{
			return false;
		}

		if (portalId == this.getPortalId())
		{
			if (this.portalCommandBridge == null)
				return false;

			KvMap tmpRequest = KvMap.createFromData(requestCommand);
			if (tmpRequest == null)
				return false;

			return this.portalCommandBridge.isSyncMethod(tmpRequest
					.getDataName());
		} else
			return false;
	}

	public String doSubPortalCommand(int portalId, String requestCommand)
	{
		return null;
	}

	protected abstract void initializeService(AbstractSimpleService<T> service);

	public abstract void initializePortal();// 初始化

	public abstract void initEventListerner();// 事件监听初始化

	public abstract void releaseService(T svr); //

}
