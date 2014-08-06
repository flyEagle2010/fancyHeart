package com.doteyplay.core.bhns.event;

import com.doteyplay.core.bhns.IMethodCollection;
import com.doteyplay.core.bhns.portal.SimpleMethodCollection;

/**
 * 事件侦听函数触发入口
 * 
 * @author 
 * 
 */
public class EventTrigger
{
	private IServiceEvent _triggerimpl;
	private IMethodCollection _methodMap;

	public EventTrigger(IServiceEvent triggerimpl)
	{
		this._triggerimpl = triggerimpl;
		this._methodMap = new SimpleMethodCollection(_triggerimpl.getClass());
	}

	public Object invokeMethod(String methodSignature, Object[] args)
	{
		if (_methodMap != null)
			return _methodMap.invokeMethod(methodSignature, _triggerimpl, args);
		else
			return null;
	}

}
