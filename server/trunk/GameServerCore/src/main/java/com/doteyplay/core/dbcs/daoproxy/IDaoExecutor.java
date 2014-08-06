package com.doteyplay.core.dbcs.daoproxy;

/**
 * DAO接口标签
 * 
 * @author 
 * 
 */
public interface IDaoExecutor
{
	// TODO:增加增，删，改，查基础服务定义
	
	public boolean startTransaction();
	
	public boolean commitTransaction();
	
	public boolean endTransaction();
}
