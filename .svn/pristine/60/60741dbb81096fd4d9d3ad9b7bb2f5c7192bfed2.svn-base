package com.doteyplay.core.bhns.portal;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.doteyplay.core.bhns.IMethodCollection;
import com.doteyplay.core.service.CoreServiceConstants;

/**
 * 方法调用快速索引
 * 
 */
public class SimpleMethodCollection implements IMethodCollection
{
	/**
	 * key :方法的名字。拼的串。Value 方法。
	 */
	private Map<String, Method> methodMap;
	/**
	 * key :方法。String 方法的名字。拼的串
	 */
	private Map<Method, String> methodSignatureMap;
	/**
	 * 有同步限制的方法
	 */
	private Set<String> syncMethodSet; 

	public SimpleMethodCollection(Class<?> refclass)
	{
		methodMap = new HashMap<String, Method>();
		methodSignatureMap = new HashMap<Method, String>();
		syncMethodSet = new HashSet<String>();
		init(refclass);
	}

	private void init(Class<?> refclass)
	{
		if (refclass != null)
		{
			Method[] tmpMethods = refclass.getMethods();
			if (tmpMethods != null)
			{
				Class<?>[] tmpParams;
				StringBuffer tmpSignatureBuffer;
				String tmpSignature;
				for (Method tmpMethod : tmpMethods)
				{
					tmpSignatureBuffer = new StringBuffer(tmpMethod.getName());
					tmpParams = tmpMethod.getParameterTypes();
					if (tmpParams != null)
					{
						for (Class<?> tmpParamClass : tmpParams)
						{
							tmpSignatureBuffer.append(tmpParamClass.getSimpleName());
						}
					}
					tmpSignature = tmpSignatureBuffer.toString();
					methodMap.put(tmpSignature, tmpMethod);
					methodSignatureMap.put(tmpMethod, tmpSignature);
					
					//同步标识
					if(tmpSignature.toLowerCase().indexOf(CoreServiceConstants.SYNC_METHOD_FLAG) >= 0)
						syncMethodSet.add(tmpSignature);
				}
			}
		}
	}
	/**
	 * 调用方法 处理.
	 * methodSignature : 方法名+参数类型名+参数类型名....多个
	 * refobj 对象。
	 * args 所有的参数。
	 */
	public Object invokeMethod(String methodSignature, Object refobj, Object[] args)
	{
		if (methodSignature == null || refobj == null)
			return null;

		Method tmpMethod = this.methodMap.get(methodSignature);
		if (tmpMethod == null)
			return null;

		try
		{
			return tmpMethod.invoke(refobj, args);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public Method getMeshodOfMethodSignature(String methodSignature)
	{
		return (methodSignature != null) ? methodMap.get(methodSignature) : null;
	}

	public String getMethodSignature(Method method)
	{
		return (method != null) ? methodSignatureMap.get(method) : null;
	}

	@Override
	public boolean isSyncMethod(String methodSignature)
	{
		return this.syncMethodSet.contains(methodSignature);
	}

}
