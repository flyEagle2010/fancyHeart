package com.doteplay.editor.common;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinkDataUtil {

	/**
	 * 关联资源
	 * @param linkDataMap
	 *            Key:resourceType List<LinkData> data id list
	 * @param resourceType
	 * @param resourceId
	 * @param resourceLevel
	 */
	public static void associateDataResource(Map<String, LinkData> linkDataMap, int resourceType, int resourceId,
			int resourceLevel) {
		
		if (resourceId == -1) {
			return;
		}

		LinkData link = new LinkData(resourceType, resourceId, resourceLevel);
		
		if (linkDataMap.containsKey(link.getKey())) {
			return;
		}
		
		linkDataMap.put(link.getKey(), link);
	}
	
	/**
	 * 关联资源
	 * @param linkDataMap
	 * @param resourceType
	 * @param resourceId
	 */
	public static void associateDataResource(Map<String, LinkData> linkDataMap, int resourceType, int resourceId) {
		associateDataResource(linkDataMap, resourceType, resourceId, LinkData.LEVEL_0);
	}

	/**
	 * 关联资源
	 * 
	 * @param linkDataMap
	 * @param subLinkDataMap
	 */
	public static void associateDataResource(Map<String, LinkData> linkDataMap, Map<String, LinkData> subLinkDataMap) {
		if (subLinkDataMap == null || subLinkDataMap.isEmpty()) {
			return;
		}
		linkDataMap.putAll(subLinkDataMap);
	}

	/**
	 * 关联资源
	 * 
	 * @param linkDataMap
	 * @param subLinkDataMap
	 * @param level
	 */
	public static void associateDataResource(Map<String, LinkData> linkDataMap, Map<String, LinkData> subLinkDataMap,
			int level) {

		if (subLinkDataMap == null || subLinkDataMap.isEmpty()) {
			return;
		}

		Map<String, LinkData> temp = new HashMap<String, LinkData>();
		for (String key : subLinkDataMap.keySet()) {
			LinkData data = subLinkDataMap.get(key);
			data.level = level;
			temp.put(key, data);
		}
		
		associateDataResource(linkDataMap, temp);
	}

	/**
	 * 清空关联资源
	 * 
	 * @param linkDataMap
	 */
	public static void clearLinkData(Map<Integer, List<Integer>> linkDataMap) {
		linkDataMap.clear();
	}
	
	public static void sortLinkDataList(List<LinkData> linkLevelList) {
		Comparator<LinkData> compar = new Comparator<LinkData>(){

			@Override
			public int compare(LinkData o1, LinkData o2) {
				if(o1.type>o2.type){
					return 1;
				}else if(o1.type<o2.type){
					return -1;
				}
				return 0;
			}
			
		};
		
		Collections.sort(linkLevelList, compar);		
	}
}
