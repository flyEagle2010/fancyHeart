package com.doteyplay.core.dbcs.executor;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.doteyplay.core.dbcs.DBCSConst;
import com.doteyplay.core.dbcs.config.PackageItemInfo;
import com.doteyplay.core.dbcs.config.SqlMapXmlReader;
import com.doteyplay.core.dbcs.daoproxy.IDaoExecutor;
import com.doteyplay.core.dbcs.dbspace.DBSpace;
import com.doteyplay.core.dbcs.dbspace.DBZone;

/**
 * DAO接口函数执行代理管理器
 * 
 * @author
 * 
 */
public class ExecutorManager
{
	private static Map<String, ExecutorProxy> executorMap = new ConcurrentHashMap<String, ExecutorProxy>();

	public static void initialize()
	{
		Collection<DBZone> tmpDBZones = DBSpace.getDBZones();
		if (tmpDBZones != null)
		{
			String tmpBeanName;
			String tmpPkgName;
			for (DBZone tmpDBZone : tmpDBZones)
			{
				Collection<PackageItemInfo> tmpPkgItemInfos = tmpDBZone
						.getMapperItems();
				if (tmpPkgItemInfos != null)
				{
					for (PackageItemInfo tmpPkgInfo : tmpPkgItemInfos)
					{
						tmpBeanName = tmpPkgInfo.itemName;
						tmpBeanName = tmpBeanName.substring(
								0,
								tmpBeanName.length()
										- SqlMapXmlReader.SUFFIX.length());
						tmpPkgName = tmpPkgInfo.packageName.replace('/', '.');

						regMapperExecutor(tmpBeanName, tmpPkgName, tmpDBZone,
								false);
					}
				}
			}
		}

		DBZone gameDbZone = DBSpace.getGameDataDBZone();
		if (gameDbZone != null)
		{
			String tmpBeanName;
			String tmpPkgName;
			Collection<PackageItemInfo> tmpPkgItemInfos = gameDbZone
					.getMapperItems();
			if (tmpPkgItemInfos != null)
			{
				for (PackageItemInfo tmpPkgInfo : tmpPkgItemInfos)
				{
					tmpBeanName = tmpPkgInfo.itemName;
					tmpBeanName = tmpBeanName.substring(0, tmpBeanName.length()
							- SqlMapXmlReader.SUFFIX.length());
					tmpPkgName = tmpPkgInfo.packageName.replace('/', '.');

					// 为了加载data库 临时这么做
					regMapperExecutor(tmpBeanName, tmpPkgName, gameDbZone, true);
				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	public static void regMapperExecutor(String beanname, String namespace,
			DBZone dbzone, boolean isGameDataZone)
	{
		String tmpExecutorClassName = namespace + "."
				+ DBCSConst.EXECUTOR_INTERFACE_PREFIX + beanname
				+ DBCSConst.EXECUTOR_INTERFACE_SUFFIX;
		if (executorMap.containsKey(tmpExecutorClassName))
			return;

		Class<? extends IDaoExecutor> tmpExecutorClass = null;
		try
		{
			tmpExecutorClass = (Class<? extends IDaoExecutor>) Class
					.forName(tmpExecutorClassName);
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		if (tmpExecutorClass == null)
		{
			// XXX: 待处理，暂时返回NULL
			return;
		}

		if (!tmpExecutorClass.isInterface())
		{
			return;
		}

		ExecutorProxy tmpMapperProxy = new ExecutorProxy(beanname);
		tmpMapperProxy.initialize(tmpExecutorClass, dbzone, isGameDataZone);
		executorMap.put(tmpMapperProxy.getExcutorInterfaceName(),
				tmpMapperProxy);
	}

	@SuppressWarnings("unchecked")
	public static <T extends IDaoExecutor> T getExector(Class<T> interfaceClass)
	{
		ExecutorProxy tmpMapperProxy = executorMap.get(interfaceClass
				.getSimpleName());
		return (tmpMapperProxy != null && tmpMapperProxy.isAvailable()) ? (T) tmpMapperProxy
				.getExecutor() : null;
	}

}
