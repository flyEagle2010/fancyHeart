package com.doteplay.editor.common;

import java.util.HashMap;
import java.util.List;

import com.doteplay.editor.datahelper.IRuntimeDataHelper;
import com.doteplay.editor.datahelper.MetaData;
import com.doteplay.editor.datahelper.ParamList;

public class RuntimeDataHelper implements IRuntimeDataHelper
{

	private HashMap<String, MetaData> dataCache;

	public RuntimeDataHelper()
	{
		dataCache = new HashMap<String, MetaData>();
	}

	@Override
	public void resetCache()
	{
		dataCache.clear();
	}

	@Override
	public List<MetaData> getRuntimeData(String datagroup, ParamList params)
	{
		DataGroup dataGroup = DataManager.getDataGroup(datagroup);
		if (dataGroup != null)
		{
			return dataGroup.filterData(params);
		} else
			return null;
	}

	@Override
	public MetaData findRuntimeData(String datagroup, String id)
	{
		DataGroup dataGroup = DataManager.getDataGroup(datagroup);
		if (dataGroup != null)
		{
			BaseData tmpData = dataGroup.getData(id);
			if (tmpData != null)
			{
				MetaData tmpMetaData = dataGroup.createMetaData();
				tmpData.exportMetaData(tmpMetaData);
				return tmpMetaData;
			}
		}
		return null;
	}

	@Override
	public void debugInfoOutput(String info, int infotype)
	{
		System.out.println("type=" + infotype + ":" + info);
	}

	private static IRuntimeDataHelper _instance;

	public synchronized static IRuntimeDataHelper defaultDataHelper()
	{
		if (_instance == null)
		{
			_instance = new RuntimeDataHelper();
		}
		return _instance;
	}

}
