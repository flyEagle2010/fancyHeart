package com.doteyplay.game.persistence.serverdata.outfit;

import java.util.List;

import com.doteyplay.core.dbcs.daoproxy.IDaoExecutor;
import com.doteyplay.core.dbcs.executor.DAOInfo;
import com.doteyplay.game.domain.gamebean.OutfitBean;

public interface IOutfitBeanDao extends IDaoExecutor
{
	@DAOInfo(Params = "")
	public List<OutfitBean> selectOutfitBeanListByRoleId(long roleId);

	@DAOInfo(Params = "")
	public void insertOutfitBean(OutfitBean outfitBean);

	@DAOInfo(Params = "")
	public void updateOutfitBean(OutfitBean outfitBean);
}