package com.doteyplay.core.bhns.active;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.core.util.dependence.DepandenceSorter;
import com.doteyplay.core.util.dependence.DependenceScaner;
import com.doteyplay.core.util.dependence.IDependence;
import com.doteyplay.core.util.dependence.IDependenceAssembly;
import com.doteyplay.core.util.xml.IParamterSupport;
import com.doteyplay.core.util.xml.ISimpleParamters;
import com.doteyplay.core.util.xml.XmlFileSupport;

/**
 * 服务的激活管理
 * 
 * @author
 * 
 */
public class ActiveServiceHolder implements IParamterSupport,
		IDependenceAssembly
{
	private static final Logger logger = Logger
			.getLogger(ActiveServiceHolder.class);
	/**
	 * 激活service的配置表.用于读取所有的配置.
	 */
	public static String ACTIVESERVICE_CONFIG_FILE = "/active_service.xml";
	/**
	 * 存贮激活后的Service.<Key是Name,Value是ActiveServiceInfo.信息的简单封装>
	 */
	private Map<String, ActiveServiceInfo> activeInfoMap = new HashMap<String, ActiveServiceInfo>();
	/**
	 * 存贮service.<Key是服务器的Id,Value是服务的简单信息封装>
	 */
	private Map<Integer, ActiveServiceInfo> activeIdInfoMap = new HashMap<Integer, ActiveServiceInfo>();
	/**
	 * 排序后的List
	 */
	private List<String> serivceSortedList = new ArrayList<String>();

	public void load_config(String configfile)
	{
		if (configfile != null && configfile.length() > 0)
			ACTIVESERVICE_CONFIG_FILE = configfile;
		// 读取配置文件.并调用本对象作为解析器,解析xml的每一个元素.直接调用this.putParamter()方法.读取数据.
		XmlFileSupport.parseXmlFromResource(ACTIVESERVICE_CONFIG_FILE, this);

		boolean check = this.checkDependRelation();
		if (!check)
			throw new RuntimeException("activeService depaendRelation error");

		DepandenceSorter sorter = new DepandenceSorter();
		for (ActiveServiceInfo info : activeInfoMap.values())
			sorter.addElement(String.valueOf(info.getPortalId()),
					info.getDependence());

		serivceSortedList = sorter.sort();
	}

	public void active_all_service(long serviceId)
	{
		if (serviceId <= 0)
			return;

		if (serivceSortedList != null && serivceSortedList.size() > 0)
		{
			int portalId = Integer.parseInt(serivceSortedList.get(0));
			ActiveServiceInfo info = activeIdInfoMap.get(portalId);
			BOServiceManager.activeService(info.getPortalId(), serviceId,
					info.getRule(), true);
		}
	}

	public void active_next_service(int portalId, long serviceId)
	{
		int curIndex = serivceSortedList.indexOf(String.valueOf(portalId));
		int nextIndex = 0;
		if (curIndex < serivceSortedList.size() - 1)
		{
			nextIndex = curIndex + 1;

			int nextPortalId = Integer.parseInt(serivceSortedList
					.get(nextIndex));
			ActiveServiceInfo info = activeIdInfoMap.get(nextPortalId);
			BOServiceManager.activeService(info.getPortalId(), serviceId,
					info.getRule(), true);
		} else
			return;
	}

	public void active_service(int portalId, long serviceId)
	{
		ActiveServiceInfo info = activeIdInfoMap.get(portalId);
		BOServiceManager.activeService(info.getPortalId(), serviceId,
				info.getRule(), false);
	}

	/**
	 * 读取active_service.xml的内容. 并装信息,封装进入Map中去.
	 */
	@Override
	public void putParamter(ISimpleParamters paramter)
	{
		if ("SERVICE".equals(paramter.getDataName()))
		{ // 读取Name
			String name = paramter.getValue("NAME");
			// portalId 入口Id.一个服务,称为一个入口
			String portalIdStr = paramter.getValue("PORTALID");
			// 依赖的其它服务的Id.或入口Id.可以入逗号分隔,写入多个.
			String dependencesStr = paramter.getValue("DEPENDENCES");
			/**
			 * 规则.
			 */
			String ruleStr = paramter.getValue("RULE");
			if (name == null || name.length() <= 0 || portalIdStr == null
					|| portalIdStr.length() <= 0)
			{
				logger.error("Invalid active setting,name=" + name
						+ ",portalId=" + portalIdStr);
				return;
			}
			String[] tmpDependenceList = null;
			if (dependencesStr != null)
				dependencesStr = dependencesStr.trim();

			if (dependencesStr != null && dependencesStr.length() > 0)
				tmpDependenceList = dependencesStr.trim().toUpperCase()
						.split(",");
			else
				tmpDependenceList = null;

			int portalId = Integer.parseInt(portalIdStr);
			// 构造入口的信息存贮类.
			ActiveServiceInfo activeInfo = new ActiveServiceInfo(name,
					portalId, tmpDependenceList, ruleStr);
			this.activeInfoMap.put(name.toUpperCase(), activeInfo);
			this.activeIdInfoMap.put(portalId, activeInfo);
		}
	}

	@Override
	public void onComplete()
	{
	}

	/**
	 * 读完所有的xml信息后,进行检查依赖关系.
	 * 
	 * @return false 为有依赖关系不合法. true 依赖正常.
	 */
	public boolean checkDependRelation()
	{
		Collection<ActiveServiceInfo> tmpBlockInfos = activeInfoMap.values();
		if (tmpBlockInfos != null)
		{
			for (ActiveServiceInfo tmpBlockInfo : tmpBlockInfos)
			{
				if (!DependenceScaner.checkValidation(String.valueOf(tmpBlockInfo.getPortalId()),
						this))
				{
					logger.error("dependence of " + tmpBlockInfo.getName()
							+ " is invalid");
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public IDependence getItem(String name)
	{
		if (name != null)
			return activeIdInfoMap.get(Integer.parseInt(name));
		else
			return null;
	}

	// **********************************************************************
	private static ActiveServiceHolder instance;

	private synchronized static ActiveServiceHolder getInstance()
	{
		if (instance == null)
		{
			instance = new ActiveServiceHolder();
		}
		return instance;

	}

	public static void initialize()
	{
		ActiveServiceHolder tmpDataStore = getInstance();
		if (tmpDataStore != null)
		{
			tmpDataStore.load_config(ACTIVESERVICE_CONFIG_FILE);
		}
	}

	public static void activeAllService(long serviceId)
	{
		ActiveServiceHolder tmpDataStore = getInstance();
		if (tmpDataStore != null)
		{
			tmpDataStore.active_all_service(serviceId);
		}
	}

	public static void activeNextService(int curPortalId, long serviceId)
	{
		ActiveServiceHolder tmpDataStore = getInstance();
		if (tmpDataStore != null)
		{
			tmpDataStore.active_next_service(curPortalId, serviceId);
		}
	}
}
