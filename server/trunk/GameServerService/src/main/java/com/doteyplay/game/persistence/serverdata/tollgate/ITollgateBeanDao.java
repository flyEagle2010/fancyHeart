package com.doteyplay.game.persistence.serverdata.tollgate;

import com.doteyplay.core.dbcs.daoproxy.IDaoExecutor;
import com.doteyplay.core.dbcs.executor.DAOInfo;
import com.doteyplay.game.domain.gamebean.TollgateBean;

/**
 * @className:IRoleInstanceBeanDao.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年6月23日 下午2:55:35
 */
public interface ITollgateBeanDao extends IDaoExecutor {
	// 获取当前用户的关卡信息
	@DAOInfo(Params = "")
	public TollgateBean selectTollgateBean(long roleId);
	//增加当前用户的关卡信息
	@DAOInfo(Params = "")
	public void insertTollgateBean(TollgateBean tollgateBean);
	//更新当前用户的关卡信息.
	@DAOInfo(Params = "")
	public void updateTollgateBean(TollgateBean tollgateBean);
}
