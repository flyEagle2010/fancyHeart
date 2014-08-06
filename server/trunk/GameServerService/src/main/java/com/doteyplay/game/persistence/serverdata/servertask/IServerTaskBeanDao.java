package com.doteyplay.game.persistence.serverdata.servertask;

import com.doteyplay.core.dbcs.daoproxy.IDaoExecutor;
import com.doteyplay.core.dbcs.executor.DAOInfo;
import com.doteyplay.game.domain.gamebean.ServerTaskBean;

public interface IServerTaskBeanDao extends IDaoExecutor
{
	@DAOInfo(Params = "")
	public ServerTaskBean selectServerTaskBean(int serverTaskId);
	
	@DAOInfo(Params = "")
	public void insertServerTaskBean(ServerTaskBean serverTaskBean);

	@DAOInfo(Params = "")
	public void updateServerTaskBean(ServerTaskBean serverTaskBean);

}