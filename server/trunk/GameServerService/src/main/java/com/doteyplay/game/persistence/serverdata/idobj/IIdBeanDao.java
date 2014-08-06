package com.doteyplay.game.persistence.serverdata.idobj;

import java.util.List;

import com.doteyplay.core.dbcs.daoproxy.IDaoExecutor;
import com.doteyplay.core.dbcs.executor.DAOInfo;
import com.doteyplay.game.constants.IdType;
import com.doteyplay.game.domain.gamebean.IdBean;

public interface IIdBeanDao extends IDaoExecutor {
	@DAOInfo(Params = "tableName,groupName,keyName")
	public List<IdBean> selectIdBean(String tableName,String groupName,String keyName);

}