package com.doteyplay.game.domain.tollgate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className:NodeArray.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年6月24日 下午2:32:14
 */
public class NodeArray<K, V> {

	private List<V> nodeList;

	private Map<K, V> nodeMap;

	public NodeArray() {
		initlize();
	}
	/**
	 * 初始化内存空间
	 */
	private void initlize() {
		nodeList = new ArrayList<V>();
		nodeMap = new HashMap<K, V>();
	}

	public void addValue(K k, V v) {
		nodeList.add(v);
		nodeMap.put(k, v);
	}

	public boolean containKey(K k) {
		return nodeMap.containsKey(k);
	}

	public void remove(K k, V v) {
		nodeList.remove(v);
		nodeMap.remove(k);
	}

	public int size() {
		// TODO Auto-generated method stub
		return nodeList.size();
	}
	
	/**
	 * 返回不可以修改的List
	 * @return
	 */
	public List<V> getNodeList(){
		return nodeList;
	}
	
	public V get(K k){
		return nodeMap.get(k);
	}
	
	public void clear(){
		nodeList.clear();
		nodeMap.clear();
	}
}
