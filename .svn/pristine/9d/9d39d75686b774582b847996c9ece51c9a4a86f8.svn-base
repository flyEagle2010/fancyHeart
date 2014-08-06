package com.doteyplay.core.classloader;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.doteyplay.core.bhns.BOServiceConfig;
import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.core.bhns.ISimpleService;
import com.doteyplay.core.bhns.ServiceInfo;
import com.doteyplay.core.bhns.source.IEndpointSource;

/**
 * 可能以后会遇到服务版本太多，需要持有loader的情况。
 * 
 */
public class ServiceJarReloadManager
{
	private static Logger logger = Logger.getLogger(ServiceJarReloadManager.class);
	
	private Map<Integer, ClassLoader> serviceJarLoaderMap = new HashMap<Integer, ClassLoader>();

	private ServiceJarReloadManager()
	{
	}

	private void put_service_loader(int portalId, ClassLoader loader)
	{
		serviceJarLoaderMap.put(portalId, loader);
	}

	@SuppressWarnings("static-access")
	private ClassLoader get_service_loader(int portalId)
	{
		ClassLoader loader = serviceJarLoaderMap.get(portalId);
		if (loader == null)
		{
			loader = ServiceJarReloadManager.class.getClassLoader()
					.getSystemClassLoader();
			serviceJarLoaderMap.put(portalId, loader);
		}
		return loader;
	}

	private boolean notify_replace_service(int portalId,
			Class<? extends ISimpleService> serviceImplClz, ClassLoader loader)
	{
		if(serviceImplClz == null)
			return false;
		this.put_service_loader(portalId, loader);
		ServiceInfo tmpServiceInfo = BOServiceManager.getServiceInfo(portalId);
		if (tmpServiceInfo != null)
		{
			IEndpointSource tmpEndpoint = tmpServiceInfo.source().getLocalEndPoint();
			if (tmpEndpoint != null && tmpEndpoint.isLocalImplemention())
			{
				tmpEndpoint.updateServiceImplClz(serviceImplClz);
				return true;
			}
			return false;
		}
		
		logger.error("replace serviceImpl success , portalId:"+portalId +", serviceImplClz:"+serviceImplClz.getName());
		return true;
	}
	
	private boolean replace_service_jar(int[] portalIds, String[] jarPaths)
	{
		if (portalIds == null || jarPaths == null || jarPaths.length == 0)
			return false;

		for (int portalId : portalIds)
		{
			ServiceInfo tmpServiceInfo = BOServiceManager.getServiceInfo(portalId);
			if (tmpServiceInfo != null)
			{
				ISimpleService service = tmpServiceInfo.getLocalPortalService(portalId);
				if(service == null || service.isProxy())
					return false;
			}
		}
		CustomClassLoader ccl = new CustomClassLoader(jarPaths);
		try
		{
			ccl.load();
			//从读本地配置，匹配当前服务实现类
			BOServiceConfig.reloadLocalService();
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// //////////////////////////////////////////////////////////////////////////////////////
	private static ServiceJarReloadManager _instance = new ServiceJarReloadManager();

	public static boolean replaceServiceJar(int[] portalIds, String[] jarPaths)
	{
		return _instance.replace_service_jar(portalIds, jarPaths);
	}

	public static boolean notifyReplaceServiceImpl(int portalId,
			Class<? extends ISimpleService> serviceImplClz, ClassLoader loader)
	{
		return _instance.notify_replace_service(portalId, serviceImplClz,
				loader);
	}

	public static ClassLoader find(int portalId)
	{
		return _instance.get_service_loader(portalId);
	}
}
