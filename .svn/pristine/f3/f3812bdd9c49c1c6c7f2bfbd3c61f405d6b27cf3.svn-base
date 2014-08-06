package com.doteyplay.game.service.bo.virtualworld.chat.handle;

import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.core.dbcs.DBCS;
import com.doteyplay.game.domain.gamebean.ItemBean;
import com.doteyplay.game.domain.role.Role;
import com.doteyplay.game.persistence.serverdata.item.IItemBeanDao;
import com.doteyplay.game.service.bo.item.IItemService;
import com.doteyplay.game.service.bo.virtualworld.chat.patameter.GoodsPatameterObject;

/**
 * @className:GoodsHandlerImpl.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年7月10日 下午4:35:27
 */
public class GoodsHandlerImpl extends AbstractHandlerImpl<GoodsPatameterObject> {

	@Override
	public void onlineHandle(Role role, GoodsPatameterObject t) {
		// TODO Auto-generated method stub
		for (GoodsPatameterObject.ItemObject item : t.getItemObjects()) {
			IItemService itemService = BOServiceManager.findService(
					IItemService.PORTAL_ID, role.getRoleId());
			try {
				itemService.lock();
				itemService.addOrRemoveItem(item.itemId, item.itemNum, true);
			} finally {
				itemService.unlock();
			}
		}
	}

	@Override
	public void offlineHandle(long roleId, GoodsPatameterObject t) {
		// TODO Auto-generated method stub
		for (GoodsPatameterObject.ItemObject item : t.getItemObjects()) {

			IItemBeanDao itemBeanDao = DBCS.getExector(IItemBeanDao.class);
			ItemBean itemBeanDB = itemBeanDao.selectItemBeanByRoleIdItemId(
					roleId, item.itemId);
			if (itemBeanDB == null) {
				itemBeanDB = new ItemBean();
				itemBeanDB.setItemId(item.itemId);
				itemBeanDB.setItemNum(item.itemNum);
				itemBeanDB.setRoleId(roleId);
				itemBeanDao.insertItemBean(itemBeanDB);
			} else {
				int newGoodsNum = item.itemNum + itemBeanDB.getItemNum();
				itemBeanDB.setItemNum(newGoodsNum > 0 ? newGoodsNum : 0);
				itemBeanDao.updateItemBean(itemBeanDB);
			}
		}
	}
}
