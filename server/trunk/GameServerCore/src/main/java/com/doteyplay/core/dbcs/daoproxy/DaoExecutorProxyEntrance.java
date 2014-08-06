package com.doteyplay.core.dbcs.daoproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.doteyplay.core.dbcs.executor.ExecutorProxy;
/**
 * DAO接口代理类实现
 * 
 * @author 
 * 
 */
public class DaoExecutorProxyEntrance implements InvocationHandler
{
	private ExecutorProxy excutorProxy;
	public DaoExecutorProxyEntrance(ExecutorProxy excutorProxy)
	{
		this.excutorProxy=excutorProxy;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		if(excutorProxy!=null){
			return excutorProxy.invokeStatement(method.getName(),args);
		}
		return null;
	}

}
