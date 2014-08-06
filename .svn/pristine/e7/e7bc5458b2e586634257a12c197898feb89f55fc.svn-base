package com.doteyplay.game.service.servertask;

import com.doteyplay.core.dbcs.DBCS;
import com.doteyplay.core.server.servertaskimpl.TimerTaskInfo;
import com.doteyplay.core.util.servertask.ServerTaskInfo;
import com.doteyplay.core.util.servertask.IServerTaskDBManager;
import com.doteyplay.game.domain.gamebean.ServerTaskBean;
import com.doteyplay.game.persistence.serverdata.servertask.IServerTaskBeanDao;

public class ServerTaskDBManager implements IServerTaskDBManager
{

	@Override
	public void saveServerTaskInfo(ServerTaskInfo info)
	{
		IServerTaskBeanDao dao = DBCS.getExector(IServerTaskBeanDao.class);
		ServerTaskBean bean = new ServerTaskBean();
		bean.setId(info.getId());
		bean.setNextUpdateTime(info.getNextExecuteTime());
		dao.insertServerTaskBean(bean);
	}

	@Override
	public ServerTaskInfo getServerTaskInfo(int id)
	{
		IServerTaskBeanDao dao = DBCS.getExector(IServerTaskBeanDao.class);
		ServerTaskBean bean = dao.selectServerTaskBean(id);
		if(bean == null)
			return null;
		ServerTaskInfo info = new TimerTaskInfo();
		info.setNextExecuteTime(bean.getNextUpdateTime());
		return info;
	}

	@Override
	public void updateTaskInfo(ServerTaskInfo info)
	{
		IServerTaskBeanDao dao = DBCS.getExector(IServerTaskBeanDao.class);
		ServerTaskBean bean = new ServerTaskBean();
		bean.setId(info.getId());
		bean.setNextUpdateTime(info.getNextExecuteTime());
		dao.updateServerTaskBean(bean);
	}

}
