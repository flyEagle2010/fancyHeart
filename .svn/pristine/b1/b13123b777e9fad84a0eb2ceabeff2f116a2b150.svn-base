package com.doteyplay.game.persistence.gamedata;

import java.util.List;

import com.doteyplay.core.dbcs.daoproxy.IDaoExecutor;
import com.doteyplay.core.dbcs.executor.DAOInfo;
import com.doteyplay.game.domain.gamebean.UserBean;
import com.doteyplay.game.domain.gamedatabean.GameDataBean;

public interface IGameDataBeanDao extends IDaoExecutor {
	@DAOInfo(Params = "")
	public GameDataBean selectGameDataBeanByPKId(int PKId);
	
	@DAOInfo(Params = "resourceId,resourceType")
	public GameDataBean selectGameDataBean(int resourceId,int resourceType);
	
	@DAOInfo(Params = "")
	public List<GameDataBean> selectGameDataBeanAll();

}