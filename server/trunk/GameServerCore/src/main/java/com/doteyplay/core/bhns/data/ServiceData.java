package com.doteyplay.core.bhns.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 服务数据，与代码实现分离
 * 
 * 存贮数据的容器.
 */
public class ServiceData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 16个Zone
	 */
	public final static int MAX_DATA_ZONE_COUNT = 16;

	/**
	 * 一个
	 */
	private HashMap<String, Serializable> discreteDataMap;
	/**
	 * 16长度的数组.
	 */
	private HashMap<String, Serializable>[] sequenceDataMaps;
	/**
	 * 数据遍历器
	 */
	private ServiceDataIterator emptyIterator;

	@SuppressWarnings("unchecked")
	public ServiceData() {
		discreteDataMap = null;
		sequenceDataMaps = new HashMap[MAX_DATA_ZONE_COUNT];
		emptyIterator = new ServiceDataIterator();
	}
	/*
	 * 取数据
	 */
	public synchronized Serializable getData(String datatag) {
		if (discreteDataMap != null)
			return discreteDataMap.get(datatag);
		else
			return null;
	}
	/**
	 * 按入数据
	 * @param datatag
	 * @param data
	 * @return
	 */
	public synchronized boolean putData(String datatag, Serializable data) {
		if (discreteDataMap == null) {
			discreteDataMap = new HashMap<String, Serializable>(2);
		}
		discreteDataMap.put(datatag, data);
		return true;
	}
	/**
	 * 清理某一个
	 * @param datatag
	 */
	public synchronized void removeData(String datatag) {
		if (discreteDataMap != null)
			discreteDataMap.remove(datatag);
	}
	/**
	 * 清理所有
	 */
	public synchronized void clearData() {
		if (discreteDataMap != null)
			discreteDataMap.clear();
	}
	/**
	 * 检查是否存在
	 * @param datatag
	 * @return
	 */
	public synchronized boolean isDataExist(String datatag) {
		if (discreteDataMap != null)
			return discreteDataMap.containsKey(datatag);
		else
			return false;
	}
	/**
	 * 返回数据的遍历器
	 * @return
	 */
	public synchronized Iterator<Serializable> getDataIterator() {
		if (discreteDataMap != null)
			return discreteDataMap.values().iterator();
		else
			return emptyIterator;
	}

	public synchronized boolean isDataExist(int datagroup) {
		if (datagroup >= 0 && datagroup < MAX_DATA_ZONE_COUNT) {
			return (sequenceDataMaps[datagroup] != null) ? !sequenceDataMaps[datagroup]
					.isEmpty() : false;
		} else
			return false;
	}

	public synchronized Serializable getData(int datagroup, String datakey) {
		if (datagroup >= 0 && datagroup < MAX_DATA_ZONE_COUNT
				&& sequenceDataMaps[datagroup] != null)
			return sequenceDataMaps[datagroup].get(datakey);
		else
			return null;
	}

	public synchronized Iterator<Serializable> getDataIterator(int datagroup) {
		if (datagroup >= 0 && datagroup < MAX_DATA_ZONE_COUNT
				&& sequenceDataMaps[datagroup] != null)
			return sequenceDataMaps[datagroup].values().iterator();
		else
			return emptyIterator;
	}
	/**
	 * 放入数据.
	 * @param datagroup 组Id.
	 * @param datakey key
	 * @param data  value
	 * @return
	 */
	public synchronized  boolean putData(int datagroup, String datakey,
			Serializable data) {
		if (datagroup >= 0 && datagroup < MAX_DATA_ZONE_COUNT) {
			if (sequenceDataMaps[datagroup] == null) {
				sequenceDataMaps[datagroup] = new HashMap<String, Serializable>();
			}
			sequenceDataMaps[datagroup].put(datakey, data);
			return true;
		} else
			return false;
	}
	/**
	 * 删除数据.
	 * @param datagroup 组id
	 * @param datakey key
	 */
	public synchronized void removeData(int datagroup, String datakey) {
		if (datagroup >= 0 && datagroup < MAX_DATA_ZONE_COUNT
				&& sequenceDataMaps[datagroup] != null) {
			sequenceDataMaps[datagroup].remove(datakey);
		}
	}
	/**
	 * 清理组
	 * @param datagroup
	 */
	public synchronized void clearData(int datagroup) {
		if (datagroup >= 0 && datagroup < MAX_DATA_ZONE_COUNT
				&& sequenceDataMaps[datagroup] != null) {
			sequenceDataMaps[datagroup].clear();
		}
	}
	/**
	 * 清理所有的.包括,单个的,和16个数组的
	 */
	public synchronized void clearAllData() {
	if (discreteDataMap != null) {
			discreteDataMap.clear();
			discreteDataMap = null;
		}
		for (int i = 0; i < sequenceDataMaps.length; i++) {
			if (sequenceDataMaps[i] != null) {
				sequenceDataMaps[i].clear();
				sequenceDataMaps[i] = null;
			}
		}
	}
}
