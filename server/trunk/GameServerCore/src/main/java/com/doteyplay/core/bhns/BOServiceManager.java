package com.doteyplay.core.bhns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.doteyplay.core.bhns.event.IMultipleServiceEvent;
import com.doteyplay.core.bhns.event.IServiceEvent;
import com.doteyplay.core.bhns.event.LocalEventSource;
import com.doteyplay.core.bhns.gateway.IGateWayService;
import com.doteyplay.core.bhns.location.ISvrLocationService;
import com.doteyplay.core.bhns.portal.AbstractLocalPortal;
import com.doteyplay.core.bhns.source.IEndPointRule;
import com.doteyplay.core.bhns.source.IEndpointSource;
import com.doteyplay.core.bhns.source.options.ServiceEndpointInfo;

/**
 * 分布式服务管理器
 * 
 * @author
 * 
 */
public class BOServiceManager implements IServiceInfoAssembly
{
	private static final Logger logger = Logger
			.getLogger(BOServiceManager.class);
	/**
	 * 存贮服务信息的Map
	 */
	private Map<Integer, ServiceInfo> serviceMap = new HashMap<Integer, ServiceInfo>();
	private Map<Integer, List<IEndpointSource>> localServiceSourceMap;

	public void register_service_info(ServiceInfo svrinfo)
	{
		if (svrinfo != null)
			serviceMap.put(svrinfo.getPortalId(), svrinfo);
	}

	public void broadcast_service_location(int portalId, long svrid,
			byte endpointId)
	{
		for (ServiceInfo serviceInfo : serviceMap.values())
		{
		}
	}

	public void sync_service_location(int portalId, long svrid, byte endpointId)
	{
		ServiceInfo serviceInfo = this.get_service_info(portalId);
		if (serviceInfo != null)
		{
			serviceInfo.syncServiceLocation(svrid, endpointId);
		}
	}

	public void syncServiceLocation(int portalId, long svrid, byte endpointId)
	{

	}
	
	public boolean relocate_service_info(int portalId, byte endpointId,
			String ip, int port)
	{
		ServiceInfo info = this.get_service_info(portalId);
		if (info == null)
		{
			logger.error("relocate_service_info error,serviceInfo is null,portalId = "
					+ portalId);
			return false;
		} else
		{
			ServiceEndpointInfo pointInfo = info.option().getEndpointInfo(
					endpointId);
			if (pointInfo == null)
			{
				logger.error("relocate_service_info error,pointInfo is null,portalId = "
						+ portalId + ",endpointId = " + endpointId);
				return false;
			}

			IEndpointSource source = info.source().getEndpoint(endpointId);
			if (source instanceof INetSupport)
			{
				// 获取已接获的serviceId列表
				String serviceIdsStr = source.postPortalCommand(portalId,
						AbstractLocalPortal.GET_ACTIVED_SERVICES_CMD);

				if (pointInfo.getEndpointIP().equals(ip)
						&& pointInfo.getEndpointPort() == port)
				{
					// logger.error("relocate_service_info error,reloacte ip and port have no change,portalId = "+portalId+",endpointId = "+endpointId);
					return false;
				}

				pointInfo.setEndpointIP(ip);
				pointInfo.setEndpointPort(port);

				INetSupport reSource = (INetSupport) source;
				reSource.relocateNetChannel();

				// 发送已接获的serviceId列表
				source.postPortalCommand(portalId,
						AbstractLocalPortal.ACTIVED_SERVICES_CMD
								+ serviceIdsStr);
				logger.error("relocate_service_info success,portalId = "
						+ portalId + ",endpointId = " + endpointId + ",ip = "
						+ ip + ",port = " + port);
				return true;
			} else
			{
				logger.error("relocate_service_info error,unsupport reloacte operation,portalId = "
						+ portalId + ",endpointId = " + endpointId);
				return false;
			}
		}
	}

	public ServiceInfo get_service_info(int portalId)
	{
		if (portalId >= 0)
			return serviceMap.get(portalId);
		else
		{
			logger.error("portalid is invalid");
			return null;
		}
	}

	public Map<Integer, List<IEndpointSource>> get_local_service_sources()
	{
		if (this.localServiceSourceMap == null)
		{
			localServiceSourceMap = new HashMap<Integer, List<IEndpointSource>>();
			for (ServiceInfo svrinfo : serviceMap.values())
			{
				for (byte endpointId : svrinfo.source().endpointIdList())
				{
					IEndpointSource source = svrinfo.source().getEndpoint(
							endpointId);
					if (source != null && source.isLocalImplemention())
					{
						List<IEndpointSource> tmpLst = localServiceSourceMap
								.get(svrinfo.getPortalId());
						if (tmpLst == null)
						{
							tmpLst = new ArrayList<IEndpointSource>();
							localServiceSourceMap.put(svrinfo.getPortalId(),
									tmpLst);
						}
						if (!tmpLst.contains(source))
						{
							tmpLst.add(source);
						}
					}
				}
			}
		}
		return this.localServiceSourceMap;
	}

	public boolean reg_multiple_event_listener(int portalId,
			IMultipleServiceEvent listener)
	{
		boolean result = false;

		List<IEndpointSource> sources = this.get_local_service_sources().get(
				portalId);
		if (sources != null)
		{
			for (IEndpointSource source : sources)
			{
				result = source.regMultipleEventListener(listener);
			}
		}
		return result;
	}

	public void rm_multiple_event_listener(int portalId, int eventid, Object key)
	{
		boolean result = false;

		List<IEndpointSource> sources = this.get_local_service_sources().get(
				portalId);
		if (sources != null)
		{
			for (IEndpointSource source : sources)
			{
				source.rmMultipleEventListener(eventid, key);
			}
		}
	}

	public String post_portol_command(int portalId, String requestCommand,
			byte endpointId)
	{
		ServiceInfo tmpBHNSInfo = serviceMap.get(portalId);
		if (tmpBHNSInfo != null && tmpBHNSInfo.isAvailable())
			return tmpBHNSInfo.postPortalCommand(portalId, requestCommand,
					endpointId);
		else
		{
			logger.error("service info is invalid,portalid=:" + portalId);
			return "response status=\"-1\" msg=\"portalId is invalid\"";
		}
	}

	public List<String> post_portol_command_all(int portalId,
			String requestCommand)
	{
		ServiceInfo tmpBHNSInfo = serviceMap.get(portalId);
		List<String> tmpLst = new ArrayList<String>();
		if (tmpBHNSInfo != null && tmpBHNSInfo.isAvailable())
		{
			byte[] endps = tmpBHNSInfo.source().endpointIdList();
			if (endps == null || endps.length == 0) // 至少有一个节点
			{
				endps = new byte[1];
				endps[0] = 0;
			}
			for (byte endpointId : endps)
			{
				String tmpres = tmpBHNSInfo.postPortalCommand(portalId,
						requestCommand, endpointId);
				tmpLst.add(tmpres);
			}
		} else
		{
			logger.error("service info is invalid,portalid=:" + portalId);
			tmpLst.add("response status=\"-1\" msg=\"portalId is invalid\"");
		}
		return tmpLst;
	}

	@SuppressWarnings("unchecked")
	public <T extends ISimpleService> T active_service(int portalId,
			long serviceId, byte endpointId, boolean isOrderActive)
	{	//serviceId为roleId，不通为负数。
		if (serviceId <= 0)
			return null;
		//serviceMap中存在portalId 即入口Id
		ServiceInfo tmpBHNSInfo = serviceMap.get(portalId);
		if (tmpBHNSInfo != null && tmpBHNSInfo.isAvailable())
			return (T) tmpBHNSInfo.activeService(serviceId, endpointId,
					isOrderActive);
		else
		{
			logger.error("service info is invalid,portalid=:" + portalId);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends ISimpleService> T find_service(int portalId, long serviceId)
	{
		ServiceInfo tmpBHNSInfo = serviceMap.get(portalId);
		if (tmpBHNSInfo != null && tmpBHNSInfo.isAvailable())
			return (T) tmpBHNSInfo.findService(portalId, serviceId);
		else
		{
			logger.error("service info is invalid,portalid=:" + portalId);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends ISimpleService> T find_default_service(int portalId,
			byte endpointId)
	{
		ServiceInfo tmpBHNSInfo = serviceMap.get(portalId);
		if (tmpBHNSInfo != null && tmpBHNSInfo.isAvailable())
			return (T) tmpBHNSInfo.getPortalService(portalId, endpointId);
		else
		{
			logger.error("service info is invalid,portalid=:" + portalId);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends ISimpleService> T find_local_default_service(int portalId)
	{
		ServiceInfo tmpBHNSInfo = serviceMap.get(portalId);
		if (tmpBHNSInfo != null && tmpBHNSInfo.isAvailable())
			return (T) tmpBHNSInfo.getLocalPortalService(portalId);
		else
		{
			logger.error("service info is invalid,portalid=:" + portalId);
			return null;
		}
	}

	public boolean is_local_implement(int portalId, byte endpointId)
	{
		ServiceInfo tmpBHNSInfo = serviceMap.get(portalId);
		if (tmpBHNSInfo != null && tmpBHNSInfo.isAvailable())
			return tmpBHNSInfo.source().isLocalImplemention(endpointId);
		return false;
	}

	public byte find_local_endpointId(int portalId)
	{
		ServiceInfo tmpBHNSInfo = serviceMap.get(portalId);
		if (tmpBHNSInfo != null && tmpBHNSInfo.isAvailable())
		{
			IEndpointSource endpointSource = tmpBHNSInfo.source()
					.getLocalEndPoint();
			if (endpointSource != null)
				return endpointSource.getEndpointId();
		}
		return -1;
	}

	public <T extends ISimpleService> T find_local_service(int portalId,
			long serviceId)
	{
		ServiceInfo tmpBHNSInfo = serviceMap.get(portalId);
		if (tmpBHNSInfo != null && tmpBHNSInfo.isAvailable())
			return tmpBHNSInfo.source().getLocalEndPoint()
					.findService(serviceId);
		return null;
	}

	public void destroy_service(int portalId, long serviceId)
	{
		if (portalId <= 0)
			return;

		ServiceInfo tmpBHNSInfo = serviceMap.get(portalId);
		if (tmpBHNSInfo != null && tmpBHNSInfo.isAvailable())
		{
			tmpBHNSInfo.destoryAllService(serviceId);
		} else
			logger.error("service info is invalid,portalid=:" + portalId);
	}

	public boolean is_active(int portalId, long serviceId)
	{
		if (portalId <= 0)
			return false;
		ServiceInfo tmpBHNSInfo = serviceMap.get(portalId);
		if (tmpBHNSInfo != null && tmpBHNSInfo.isAvailable())
		{
			ISvrLocationService service = this
					.find_local_default_service(ISvrLocationService.PORTAL_ID);
			if (service.getEndpointIdOfserviceId(portalId, serviceId) > -1)
				return true;
		}
		return false;
	}

	public String[] get_local_data_blocks()
	{
		Set<String> tmpBlockNameSet = new HashSet<String>();
		String tmpNamesInfo;
		String[] tmpNames;
		byte[] tmpEndpointIdList;
		Collection<ServiceInfo> tmpServices = serviceMap.values();
		for (ServiceInfo tmpServiceInfo : tmpServices)
		{
			tmpEndpointIdList = tmpServiceInfo.source().endpointIdList();
			if (tmpEndpointIdList != null && tmpEndpointIdList.length > 0)
			{
				for (byte endpointId : tmpEndpointIdList)
				{
					tmpNamesInfo = tmpServiceInfo.source().getDataBlocks(
							endpointId);
					if (tmpNamesInfo != null && tmpNamesInfo.length() > 0)
					{
						tmpNames = tmpNamesInfo.split(",");
						for (String tmpName : tmpNames)
						{
							if (tmpName != null && tmpName.length() > 0
									&& !tmpBlockNameSet.contains(tmpName))
							{
								tmpBlockNameSet.add(tmpName);
							}
						}
					}
				}
			}
		}
		String[] r = new String[tmpBlockNameSet.size()];
		tmpBlockNameSet.toArray(r);
		tmpBlockNameSet.clear();
		tmpBlockNameSet = null;
		return r;
	}

	public void initial_service()
	{
		for (ServiceInfo bhns : serviceMap.values())
		{
			bhns.initialSource();
		}
	}

	// *************************************************
	private final static BOServiceManager _instance = new BOServiceManager();

	public static void registerServiceInfo(ServiceInfo svrinfo)
	{
		_instance.register_service_info(svrinfo);
	}

	public static boolean relocateServiceInfo(int portalId, byte endpointId,
			String ip, int port)
	{
		return _instance.relocate_service_info(portalId, endpointId, ip, port);
	}

	public static void relocateServiceInfo(int portalId, String ip, int port)
	{
		relocateServiceInfo(portalId, (byte) 0, ip, port);
	}

	public static ServiceInfo getServiceInfo(int portalId)
	{
		return _instance.get_service_info(portalId);
	}

	public static Map<Integer, List<IEndpointSource>> getLocalServiceSources()
	{
		return _instance.get_local_service_sources();
	}

	public static String postPortolCommand(int portalId, String requestCommand)
	{
		return postPortolCommand(portalId, requestCommand, (byte) 0);
	}

	public static String postPortolCommand(int portalId, String requestCommand,
			byte endpointId)
	{
		return _instance.post_portol_command(portalId, requestCommand,
				endpointId);
	}

	public static List<String> postPortolCommandAll(int portalId,
			String requestCommand)
	{
		return _instance.post_portol_command_all(portalId, requestCommand);
	}

	@SuppressWarnings("unchecked")
	public static <T extends ISimpleService> T activeService(int portalId,
			long serviceId, IEndPointRule rule, boolean isOrderActive)
	{
		if (rule == null)
			return (T) activeService(portalId, serviceId, (byte) 0,
					isOrderActive);
		else
			return (T) activeService(portalId, serviceId,
					rule.getEndPoint(serviceId), isOrderActive);
	}

	@SuppressWarnings("unchecked")
	public static <T extends ISimpleService> T activeService(int portalId,
			long serviceId, byte endpointId, boolean isOrderActive)
	{
		return (T) _instance.active_service(portalId, serviceId, endpointId,
				isOrderActive);
	}

	@SuppressWarnings("unchecked")
	public static <T extends ISimpleService> T findService(int portalId,
			long serviceId)
	{
		return (T) _instance.find_service(portalId, serviceId);
	}

	@SuppressWarnings("unchecked")
	public static <T extends ISimpleService> T findDefaultService(int portalId)
	{
		return (T) findDefaultService(portalId, (byte) 0);
	}

	@SuppressWarnings("unchecked")
	public static <T extends ISimpleService> T findDefaultService(int portalId,
			byte endpointId)
	{
		return (T) _instance.find_default_service(portalId, endpointId);
	}

	@SuppressWarnings("unchecked")
	public static <T extends ISimpleService> T findLocalDefaultService(
			int portalId)
	{
		return (T) _instance.find_local_default_service(portalId);
	}

	public static void destroyService(int portalId, long serviceId)
	{
		_instance.destroy_service(portalId, serviceId);
	}

	public static String[] getLocalDataBlocks()
	{
		return _instance.get_local_data_blocks();
	}

	public static void initialService()
	{
		_instance.initial_service();
	}

	public static boolean isLocalImplement(int portalId, byte endpointId)
	{
		return _instance.is_local_implement(portalId, endpointId);
	}

	public static byte findLocalEndpointId(int portalId)
	{
		return _instance.find_local_endpointId(portalId);
	}

	@SuppressWarnings("unchecked")
	public static <T extends IServiceEvent> T createSingletonEvent(int eventid)
	{
		return (T) LocalEventSource.createEvent(eventid, true, null);
	}

	@SuppressWarnings("unchecked")
	public static <T extends IServiceEvent> T createMultipleEvent(int eventid,
			Object key)
	{
		if (key == null)
		{
			logger.error("key param must not null!!!!!");
		}
		return (T) LocalEventSource.createEvent(eventid, false, key);
	}

	public static boolean regLocalMultipleEventListener(int portalId,
			IMultipleServiceEvent listener)
	{
		return _instance.reg_multiple_event_listener(portalId, listener);
	}

	public static void rmMultipleEventListener(int portalId, int eventid,
			Object key)
	{
		_instance.rm_multiple_event_listener(portalId, eventid, key);
	}

	public static <T extends ISimpleService> T findLocalService(int portalId,
			long serviceId)
	{
		return _instance.find_local_service(portalId, serviceId);
	}

	public static boolean isActive(int portalId, long serviceId)
	{
		return _instance.is_active(portalId, serviceId);
	}
	
	public static boolean isserviceIdOnline(long serviceId)
	{
		return isActive(IGateWayService.PORTAL_ID, serviceId);
	}
}
