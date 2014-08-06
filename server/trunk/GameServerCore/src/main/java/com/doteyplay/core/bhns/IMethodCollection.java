package com.doteyplay.core.bhns;

import java.lang.reflect.Method;
/**
 * 接口所有函数按名称调用
 * 
 */
public interface IMethodCollection
{
	public Object invokeMethod(String methodSignature, Object refobj, Object[] args);

	public String getMethodSignature(Method method);
	
	public Method getMeshodOfMethodSignature(String methodSignature);
	
	public boolean isSyncMethod(String methodSignature);
}
