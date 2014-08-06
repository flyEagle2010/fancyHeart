package com.doteyplay.core.dbcs;

import com.doteyplay.core.dbcs.daoproxy.IDaoExecutor;
import com.doteyplay.core.dbcs.dbspace.DBSpace;
import com.doteyplay.core.dbcs.executor.ExecutorManager;
/**
 * 分布式数据库代理服务
 * @author 
 * 
 */
public class DBCS
{
	public static void initialize(String dbconfigpath)
	{
		DBSpace.initialize(dbconfigpath);
		ExecutorManager.initialize();
	}

	public static <T extends IDaoExecutor> T getExector(Class<T> interfaceClass)
	{
		return ExecutorManager.getExector(interfaceClass);
	}
	
	public static void startTransaction()
	{
		ExecutorManager.getExector(IDaoExecutor.class).startTransaction();
	}
	
	public static void commitTransaction()
	{
		ExecutorManager.getExector(IDaoExecutor.class).commitTransaction();
	}
	
	public static void endTransaction()
	{
		ExecutorManager.getExector(IDaoExecutor.class).endTransaction();
	}
}
