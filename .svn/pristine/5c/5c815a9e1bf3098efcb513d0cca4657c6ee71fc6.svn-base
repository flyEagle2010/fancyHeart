package com.doteyplay.game.persistence.serverdata.item;

import java.util.List;

import com.doteyplay.core.dbcs.daoproxy.IDaoExecutor;
import com.doteyplay.core.dbcs.executor.DAOInfo;
import com.doteyplay.game.domain.gamebean.ItemBean;

public interface IItemBeanDao extends IDaoExecutor
{
	@DAOInfo(Params = "")
	public List<ItemBean> selectItemBeanListByRoleId(long roleId);
	
	@DAOInfo(Params = "roleId,itemId")
	public ItemBean selectItemBeanByRoleIdItemId(long roleId,int itemId);

	@DAOInfo(Params = "")
	public void insertItemBean(ItemBean itemBean);

	@DAOInfo(Params = "")
	public void updateItemBean(ItemBean itemBean);
	
	@DAOInfo(Params = "")
	public void deleteItemBean(ItemBean itemBean);
}