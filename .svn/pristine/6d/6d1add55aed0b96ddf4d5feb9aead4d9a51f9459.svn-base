package com.doteyplay.core.bhns.dummy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.log4j.Logger;

import com.doteyplay.core.bhns.IMethodCollection;
import com.doteyplay.core.bhns.ISimpleService;

/**
 * 虚服务代理
 * 
 * @author 
 * 
 */
public class DummyServiceProxy<T extends ISimpleService>
{
	private static final long MIN_LOG_INTERVAL = 30000l;
	private static final Logger logger = Logger.getLogger(DummyServiceProxy.class);

	private int portalId;
	private T serviceProxy;
	private long nextLogStamp;
	protected IMethodCollection methodMap;

	@SuppressWarnings("unchecked")
	public DummyServiceProxy(int portalId, Class<T> interfaceClass, IMethodCollection methodmap,
			boolean validflag)
	{
		this.portalId = portalId;
		this.nextLogStamp = 0;
		this.methodMap = methodmap;
		Class<?> clazzProxy = Proxy.getProxyClass(interfaceClass.getClassLoader(), interfaceClass);
		try
		{
			Constructor<?> boProxyConstructor = clazzProxy.getConstructor(InvocationHandler.class);
			serviceProxy = (T) boProxyConstructor.newInstance(new DummyServiceProxyEntrance(portalId, this,
					validflag));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	protected void postLog(String log)
	{
		if(log==null || log.length()<=0)
			return;
		
		if (System.currentTimeMillis() > nextLogStamp && logger != null)
		{
			logger.error(log);
			nextLogStamp = System.currentTimeMillis() + MIN_LOG_INTERVAL;
		}
	}

	protected Object getDefaultResult(Method method)
	{
		if (method == null)
			return null;

		if (int.class == method.getReturnType() || Integer.class == method.getReturnType())
			return 0;
		else if (float.class == method.getReturnType() || Float.class == method.getReturnType())
			return (float) 0.0;
		else if (double.class == method.getReturnType() || Double.class == method.getReturnType())
			return 0.0;
		else if (short.class == method.getReturnType() || Short.class == method.getReturnType())
			return (short) 0;
		else if (byte.class == method.getReturnType() || Byte.class == method.getReturnType())
			return (byte) 0;
		else
			return null;
	}

	public Object invokeService(Method method, Object[] args)
	{
		postLog("dummy service method invoked:" + method + " was ignored");
//		throw new UnsupportedOperationException(method.toGenericString()+"("+method.toString()+")"); 
		return getDefaultResult(method);
	}

	public T getServiceProxy()
	{
		return serviceProxy;
	}

	public int getPortalId()
	{
		return portalId;
	}

}
