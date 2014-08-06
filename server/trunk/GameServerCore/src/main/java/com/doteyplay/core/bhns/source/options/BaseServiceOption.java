package com.doteyplay.core.bhns.source.options;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.doteyplay.core.bhns.IMethodCollection;
import com.doteyplay.core.bhns.ISimpleService;
import com.doteyplay.core.bhns.dummy.DummyServiceProxy;
import com.doteyplay.core.bhns.portal.SimpleMethodCollection;

/**
 * 更换服务实现类的jar包或class时，不用考虑对该类做处理，该类只对远程服务提供接口
 *
 */
public class BaseServiceOption
{
	protected static final Logger logger = Logger.getLogger(NormalServiceOption.class);

	private int _portalId; // 服务标识
	private Class<? extends ISimpleService> svrClass;// 服务接口
	protected IMethodCollection methodMap;// 函数快捷入口
	private DummyServiceProxy<? extends ISimpleService> dummyService;// 缺省调用

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseServiceOption(int ptid, Class<? extends ISimpleService> svrclass)
	{
		this._portalId = ptid;
		this.svrClass = svrclass;
		try
		{
			methodMap = new SimpleMethodCollection(this.svrClass);
			dummyService = new DummyServiceProxy(this._portalId, this.svrClass, methodMap, false);
		} catch (Exception e)
		{
			e.printStackTrace();
			dummyService = null;
		}
	}

	public int portalId()
	{
		return _portalId;
	}

	public IMethodCollection getMethodMap()
	{
		return methodMap;
	}

	public ISimpleService getDummyService()
	{
		return (dummyService != null) ? dummyService.getServiceProxy() : null;
	}

	public Object invokeDummy(String methodSignature, Object[] args)
	{
		if (methodMap != null)
		{
			Method tmpMethod = methodMap.getMeshodOfMethodSignature(methodSignature);
			return dummyService.invokeService(tmpMethod, args);
		} else
		{
			logger.error("dummy service method invoked:method:" + methodSignature + " was ignored");
			return null;
		}
	}

	public Object invokeMethod(ISimpleService service, String methodSignature, Object[] args)
	{
		if (methodMap != null)
		{
			Method tmpMethod = methodMap.getMeshodOfMethodSignature(methodSignature);
			try
			{
				return tmpMethod.invoke(service, args);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	public boolean isSyncMethod(String methodSignature)
	{
		return this.methodMap.isSyncMethod(methodSignature);
	}
	
	public Class<? extends ISimpleService> getSvrClass()
	{
		return svrClass;
	}
	
}
