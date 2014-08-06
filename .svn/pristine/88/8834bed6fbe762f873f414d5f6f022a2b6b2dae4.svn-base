package com.doteyplay.game.domain.tollgate;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.doteyplay.game.message.tollgate.ShowTollgateDetailMessage;

/**
 * @className:RoleTollgateManager.java
 * @classDescription:数据管理者
 * @author:Tom.Zheng
 * @createTime:2014年6月23日 下午5:58:13
 */
public class RoleTollgateManager {

	private long roleId;
	private Map<Integer, TollgateInfo> map;

	public RoleTollgateManager(long roleId) {
		super();
		this.roleId = roleId;
		initlize();
	}

	private void initlize() {
		if (map == null) {
			map = new HashMap<Integer, TollgateInfo>(1);
		}
	}


	
	
	/**
	 * 开启某一个关卡,默认开启第一个结点.
	 * @param tollgateId
	 */
	public boolean openTollgateAndNode(int tollgateId,int nodeId) {
		
		
		TollgateInfo bean =null;
		if(map.containsKey(tollgateId)){
			bean = map.get(tollgateId);
		}else{
			bean = new TollgateInfo(roleId, tollgateId);
			bean.setIsOpen((byte) 1);
			bean.setOtherInfo("");
			map.put(tollgateId, bean);
		}
		
		if(bean.isOpenNode(nodeId)){
			return false;
		}else{
			bean.addNode(nodeId);
			return true;
		}
		
	}
	
	/**
	 * 是否已经开启某结点.
	 * @param tollgateId
	 * @param nodeId
	 * @return
	 */
	public boolean isOpenTollgateAndNode(int tollgateId,int nodeId){
		
		if(!map.containsKey(tollgateId)){
			return false;
		}
		TollgateInfo tollgateInfo = map.get(tollgateId);
		
		return tollgateInfo.isOpenNode(nodeId);
	}
	/**
	 * 是否已经开启某结点.
	 * @param tollgateId
	 * @param nodeId
	 * @return
	 */
	public boolean isOpenTollgate(int tollgateId){
		
		if(!map.containsKey(tollgateId)){
			return false;
		}
		return true;
	}
	
	

	public void parseData(DataInputStream in, int ver) {
		// TODO Auto-generated method stub
		try {
			int readSize = in.readInt();
			for (int i = 0; i < readSize; i++) {
				TollgateInfo bean = new TollgateInfo(roleId);
				bean.parseData(in, ver);
				map.put(bean.getTollgateId(), bean);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void buildData(DataOutputStream out) {
		// TODO Auto-generated method stub
		try {
			if (map == null || map.size() == 0) {
				// 太不正常了.需要重新加载.
				out.writeInt(0);
			} else {
				out.writeInt(map.size());
				for (TollgateInfo bean : map.values()) {
					bean.buildData(out);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public Map<Integer, TollgateInfo> getMap() {
		return map;
	}

	public void setMap(Map<Integer, TollgateInfo> map) {
		this.map = map;
	}

	public void release() {
		// TODO Auto-generated method stub
		this.roleId = 0L;
		if(this.map!=null){
			for (TollgateInfo bean : map.values()) {
				if(bean!=null){
					bean.release();
				}
			}
			map.clear();
			map = null;
		}
	}
	
	public TollgateInfo getTollgateInfoBeanById(int tollgateId){
		if(!map.containsKey(tollgateId)){
			return null;
		}
		
		return map.get(tollgateId);
	}

	public void showTollgateDetailInfo(ShowTollgateDetailMessage message) {
		// TODO Auto-generated method stub
		
		Collection<TollgateInfo> values = map.values();
		
		message.setValues(values);
		
	}

	public byte acceptBattleResult(int tollgateId, int nodeId, int star) {
		// TODO Auto-generated method stub
		TollgateInfo tollgateInfoBeanById = getTollgateInfoBeanById(tollgateId);
		
		return tollgateInfoBeanById.acceptBattleResult(nodeId,star);
		
	}
	
	

}
