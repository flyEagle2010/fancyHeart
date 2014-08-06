package com.doteyplay.core.bhns.rmi;

import com.doteyplay.core.bhns.ISimpleService;

public class ServiceProxyStatus<T extends ISimpleService>
{
	public final static byte INACTIVE_STATUS = 0;
	public final static byte ACTIVE_STATUS = 1;
	public final static byte UNAVAILABLE_STATUS = -1;

	private byte status;
	private T entrance;

	public ServiceProxyStatus()
	{
		status = UNAVAILABLE_STATUS;
		entrance = null;
	}

	public void bindEntrance(T entrance)
	{
		this.entrance = entrance;
	}

	public byte getStatus()
	{
		return status;
	}

	public void setStatus(byte v)
	{
		this.status = v;
	}

	public T getEntrance()
	{
		return this.entrance;
	}

	public boolean isEntranceSingleton()
	{
		if (entrance != null)
			return entrance.isSingleton();
		else
			return false;
	}

}
