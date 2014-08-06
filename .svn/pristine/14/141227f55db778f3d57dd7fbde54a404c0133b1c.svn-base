package com.doteyplay.core.datastore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.doteyplay.core.util.dependence.DependenceScaner;
import com.doteyplay.core.util.dependence.IDependence;
import com.doteyplay.core.util.dependence.IDependenceAssembly;
import com.doteyplay.core.util.xml.IParamterSupport;
import com.doteyplay.core.util.xml.ISimpleParamters;
import com.doteyplay.core.util.xml.XmlFileSupport;

/**
 * 数据块管理器,提供数据块直接的相互依赖处理、顺序初始化和调用接口获取
 * 
 */
public class DataStore implements IParamterSupport, IDependenceAssembly
{
	private static final Logger logger = Logger.getLogger(DataStore.class);
	
	public String DATASERVICE_CONFIG_FILE = "/data_service.xml";
	private Map<String, DataBlockInfo> blockInfoMap = new HashMap<String, DataBlockInfo>();
	private Map<String, IDataBlock> blockImplMap = new HashMap<String, IDataBlock>();

	public void load_config(String configfile)
	{
		if (configfile != null && configfile.length() > 0)
			DATASERVICE_CONFIG_FILE = configfile;
		
		XmlFileSupport.parseXmlFromResource(DATASERVICE_CONFIG_FILE, this);
	}
	@Override
	public IDependence getItem(String name)
	{
		if (name != null)
			return blockInfoMap.get(name);
		else
			return null;
	}

	@Override
	public void putParamter(ISimpleParamters paramter)
	{
		if ("DATABLOCK".equals(paramter.getDataName()))
		{
			String tmpName = paramter.getValue("NAME");
			String tmpInterfaceName = paramter.getValue("SOURCE");
			String tmpImplementName = paramter.getValue("IMPLEMENTION");
			if (tmpName == null || tmpName.length() <= 0 || "*".equals(tmpName) || tmpInterfaceName == null
					|| tmpInterfaceName.length() <= 0 || tmpImplementName == null
					|| tmpImplementName.length() <= 0)
			{
				logger.error("Invalid datablock setting,name=" + tmpName + ",source=" + tmpInterfaceName);
				return;
			}
			String tmpDependences = paramter.getValue("DEPENDENCES");
			String[] tmpDependenceList = null;
			if (tmpDependences != null)
				tmpDependences = tmpDependences.trim();
			
			if (tmpDependences != null && tmpDependences.length() > 0)
				tmpDependenceList = tmpDependences.trim().toUpperCase().split(",");
			else
				tmpDependenceList = null;

			DataBlockInfo tmpBlockInfo = new DataBlockInfo(tmpName, tmpInterfaceName, tmpImplementName,
					tmpDependenceList);
			this.blockInfoMap.put(tmpName.toUpperCase(), tmpBlockInfo);
		}
	}

	@Override
	public void onComplete()
	{
	}

	public boolean checkDependRelation()
	{
		Collection<DataBlockInfo> tmpBlockInfos = blockInfoMap.values();
		if (tmpBlockInfos != null)
		{
			for (DataBlockInfo tmpBlockInfo : tmpBlockInfos)
			{
				if (!DependenceScaner.checkValidation(tmpBlockInfo.getName(), this))
				{
					logger.error("dependence of " + tmpBlockInfo.getName() + " is invalid");
					return false;
				}
			}
		}
		return true;
	}

	public void loadDataBlock(String blockname)
	{
		if (blockname == null)
			return;

		if ("*".equals(blockname))
		{
			Collection<DataBlockInfo> tmpBlockInfos = blockInfoMap.values();
			for (DataBlockInfo tmpBlockInfo : tmpBlockInfos)
			{
				try
				{
					loadDataBlock(tmpBlockInfo.getName());
				}catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			DataBlockInfo tmpBlockInfo = blockInfoMap.get(blockname.toUpperCase());
			if (tmpBlockInfo == null)
			{
				logger.error("datablock " + blockname + " not registered");
				return;
			}

			if ("*".equals(tmpBlockInfo.getName()))
			{
				logger.error("invalid " + tmpBlockInfo.getName() + " name");
				return;
			}

			if (tmpBlockInfo.isLoaded())
			{
				return;
			}

			if (!DependenceScaner.checkValidation(tmpBlockInfo.getName(), this))
			{
				logger.error("dependence of " + blockname + " is invalid");
				return;
			}

			String[] tmpDependenceList = tmpBlockInfo.getDependence();
			if (tmpDependenceList != null)
			{
				for (int i = 0; i < tmpDependenceList.length; i++)
				{
					loadDataBlock(tmpDependenceList[i]);
				}
			}

			IDataBlock tmpDataBlock = tmpBlockInfo.createImplementInstance();
			if (tmpDataBlock == null)
			{
				logger.error("datablock " + blockname + " can't create instance");
				return;
			}
			//如果能创建则扔到池中
			blockImplMap.put(tmpBlockInfo.getInterfaceFlagName(), tmpDataBlock);
			
			if(!tmpDataBlock.initialize()){
				logger.error("datablock " + blockname + " can't initialize'");			
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends IDataBlock> T getDataBlock(Class<T> datablockclass)
	{
		if (datablockclass != null)
			return (T) blockImplMap.get(datablockclass.getSimpleName());
		else
			return null;
	}

	private static DataStore instance;

	private synchronized static DataStore getInstance()
	{
		if (instance == null)
		{
			instance = new DataStore();
		}
		return instance;

	}

	public static void initialize(String configfile)
	{
		DataStore tmpDataStore = getInstance();
		if (tmpDataStore != null)
		{
			tmpDataStore.load_config(configfile);
		}
	}

	public static void initDataBlock(String datablockname)
	{
		if (datablockname != null && datablockname.length() > 0)
		{
			DataStore tmpDataStore = getInstance();
			if (tmpDataStore != null)
			{
				String[] tmpBlockNames = datablockname.split(",");
				for (int i = 0; i < tmpBlockNames.length; i++)
				{
					tmpDataStore.loadDataBlock(tmpBlockNames[i]);
				}
			}
		}
	}

	public static void initDataBlock(String[] datablocknames)
	{
		if (datablocknames != null && datablocknames.length > 0)
		{
			DataStore tmpDataStore = getInstance();
			if (tmpDataStore != null)
			{
				for (int i = 0; i < datablocknames.length; i++)
					tmpDataStore.loadDataBlock(datablocknames[i]);
			}
		}
	}

	public static <T extends IDataBlock> T findDataBlock(Class<T> datablockinterface)
	{
		DataStore tmpDataStore = getInstance();
		if (tmpDataStore != null)
		{
			return (T)tmpDataStore.getDataBlock(datablockinterface);
		}
		return null;
	}
}
