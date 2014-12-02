package com.doteyplay.game.domain.drop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.game.config.template.DropDataTemplate;
import com.doteyplay.game.service.bo.item.IItemService;
import com.doteyplay.game.util.GameUtil;
import com.doteyplay.game.util.excel.TemplateService;

/**
 * @className:DropDataManager.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年10月14日 下午5:48:50
 */
public class DropDataManager {

	
	private static DropDataManager instantce =null;
	
	/**
	 * 静态数据缓存.将所有的掉落组数据,按组Id存贮.
	 */
	private static Map<Integer,List<DropDataTemplate>> dropGroupMap = new HashMap<Integer,List<DropDataTemplate>>();
	/**
	 * 静态数据缓存.将用户的副本的挑战奖励物品缓存起来.
	 */
	private static Map<Long,List<DropDataTemplate>>  roleTollgateDropDataCache = new HashMap<Long,List<DropDataTemplate>>();
	
	private DropDataManager(){
		if(dropGroupMap.isEmpty()){
			init();
		}
	}
	
	
	public static DropDataManager getInstance(){
		if(instantce==null){
			synchronized (dropGroupMap) {
				if(instantce==null){
					instantce =  new DropDataManager();
				}
			}
		}
		return instantce;
	}
	
	private void init() {

		Map<Integer, DropDataTemplate> all = TemplateService.getInstance()
				.getAll(DropDataTemplate.class);

		for (int sourceKey : all.keySet()) {
			int key = sourceKey / 1000;//取前三位.
			List<DropDataTemplate> list = dropGroupMap.get(key);
			if (list == null) {
				list = new ArrayList<DropDataTemplate>();
				dropGroupMap.put(key, list);
			}
			list.add(all.get(sourceKey));
		}
	}
	/**
	 * 返回副本的掉落物品.
	 * 
	 * @param roleId
	 * @param dropId
	 * @param num
	 * @param isCreate 为true时,首先清掉缓存,再去生成.  为false时,取缓存,如果有,返回,没有即生成,即返回.
	 * @return
	 */
	public List<DropDataTemplate> getTollgateDropGroup(long roleId,int dropId,int num,boolean isCreate){
		
		if(isCreate){
			roleTollgateDropDataCache.remove(roleId);
		}
		List<DropDataTemplate> list = roleTollgateDropDataCache.get(roleId);
		
		if(list==null){
			list =getRollDropGroup(dropId, num);
			roleTollgateDropDataCache.put(roleId, list);
		}
		
		return list;
	}
	
	/**
	 * 返回随机的所有的物品
	 */
	public List<DropDataTemplate> getRollDropGroup(int dropId,int num){
		List<DropDataTemplate>  list = new ArrayList<DropDataTemplate>();
		
		List<DropDataTemplate> dropList = dropGroupMap.get(dropId);
		
		if(dropList==null||dropList.size()==0){
			return null;
		}
		
		while(true){
			for(DropDataTemplate dropItem:dropList){
				
				int rate = dropItem.getRate();
				//万分之多少.
				int random = GameUtil.random(0, 10000);
				
				if(random<rate){
					list.add(dropItem);
					if(list.size()>=num){
						break;
					}
				}
				
			}
			if(list.size()>=num){
				break;
			}
		}
		
		return list;
	}
	
	/**
	 * 奖励掉落组物品.
	 * @param rollDropGroup 
	 * @param roleId 
	 */
	public boolean rewardDropItem(long roleId, List<DropDataTemplate> rollDropGroup){
		
		if(rollDropGroup==null||rollDropGroup.size()==0){
			return false;
		}
		IItemService itemService = BOServiceManager.findService(
				IItemService.PORTAL_ID, roleId);
	
		for (DropDataTemplate dropData : rollDropGroup) {
			try {
				itemService.lock();
				itemService.addOrRemoveItem(dropData.getItemId(), dropData.getNum(), true);
			} finally {
				itemService.unlock();
			}
			
		}
		clearHistory(roleId);
		return true;
	}


	public void clearHistory(long serviceId) {
		// TODO Auto-generated method stub
		if(roleTollgateDropDataCache!=null&&serviceId>0){
			roleTollgateDropDataCache.remove(serviceId);
		}
	}
	
	
	
}
