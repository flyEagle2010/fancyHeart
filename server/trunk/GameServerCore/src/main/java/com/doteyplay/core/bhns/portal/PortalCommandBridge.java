package com.doteyplay.core.bhns.portal;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.doteyplay.core.bhns.IPortalCommandHandler;
import com.doteyplay.core.bhns.IServicePortal;
import com.doteyplay.core.service.CoreServiceConstants;
import com.doteyplay.core.util.IRequestParamters;
import com.doteyplay.core.util.IResponseParamters;

public class PortalCommandBridge
{
	private Map<String, Method> methodMap;
	private Set<String> syncMethodSet;
	
	private Object refObj;
	private IServicePortal<?> portal;

	public PortalCommandBridge(IServicePortal<?> portal)
	{
		methodMap = new HashMap<String, Method>();
		syncMethodSet = new HashSet<String>();
		this.portal = portal;
	}

	public void bindHandler(IPortalCommandHandler handler)
	{
		if (handler == null)
			return;

		Method[] tmpMethods = handler.getClass().getMethods();
		if (tmpMethods != null)
		{
			refObj = handler;

			handler.initialPortal(this.portal);
			Class<?>[] tmpParams;
			for (Method tmpMethod : tmpMethods)
			{
				tmpParams = tmpMethod.getParameterTypes();
				if (tmpParams != null && tmpParams.length == 2 && !methodMap.containsKey(tmpMethod.getName())
						&& tmpMethod.getReturnType() == void.class && tmpParams[0] == IRequestParamters.class
						&& tmpParams[1] == IResponseParamters.class)
				{
					methodMap.put(tmpMethod.getName(), tmpMethod);
				}
				
				if(tmpMethod.getName().toLowerCase().indexOf(CoreServiceConstants.SYNC_METHOD_FLAG) >= 0)
				{
					syncMethodSet.add(tmpMethod.getName());
				}
			}
		}
	}
	
	public boolean isSyncMethod(String methodName)
	{
		return syncMethodSet.contains(methodName);
	}

	public void invokeMethod(String methodName, IRequestParamters request, IResponseParamters response)
	{
		if (methodName == null || request == null || response == null)
			return;

		Method tmpMethod = methodMap.get(methodName);
		if (tmpMethod == null)
			return;

		try
		{
			Object r=tmpMethod.invoke(refObj, new Object[] { request, response });
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
