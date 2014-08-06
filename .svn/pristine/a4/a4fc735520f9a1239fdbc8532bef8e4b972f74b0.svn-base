package com.doteyplay.core.bhns.rmi;

import java.io.Serializable;

import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.net.message.AbstractMessage;

/**
 * 远端代理和实现直接传输信息的聚合类
 * 
 * @author
 * 
 */
public class ProxyBean implements Serializable, IRmiBean
{
	private static final long serialVersionUID = 5298464166939143549L;

	private int portalId;

	private byte internalFlag;

	private byte endpointId;

	private String methodSignature;

	private Object[] parameter = null;

	public int getPortalId()
	{
		return portalId;
	}

	public void setPortalId(int behaviorID)
	{
		this.portalId = behaviorID;
	}

	public String getMethodSignature()
	{
		return methodSignature;
	}

	public void setMethodSignature(String methodSignature)
	{
		this.methodSignature = methodSignature;
	}

	public Object[] getParameter()
	{
		return parameter;
	}

	public void setParameter(Object... parameter)
	{
		this.parameter = parameter;
	}

	public byte getInternalFlag()
	{
		return internalFlag;
	}

	public void setInternalFlag(byte internalFlag)
	{
		this.internalFlag = internalFlag;
	}

	public byte getEndpointId()
	{
		return endpointId;
	}

	public void setEndpointId(byte endpointId)
	{
		this.endpointId = endpointId;
	}

	@Override
	public void encode(IoBuffer out)
	{
		out.putInt(portalId);
		out.put(internalFlag);
		out.put(endpointId);
		AbstractMessage.putString(out, methodSignature);

		if (this.parameter == null || this.parameter.length <= 0)
			out.put((byte) 0);
		else
		{
			out.put((byte) this.parameter.length);
			for (Object bean : this.parameter)
				out.putObject(bean);
		}
	}

	@Override
	public void decode(IoBuffer in)
	{
		this.portalId = in.getInt();
		this.internalFlag = in.get();
		this.endpointId = in.get();
		this.methodSignature = AbstractMessage.getString(in);

		int size = in.get();
		if (size <= 0)
			return;
		else
		{
			parameter = new Object[size];
			for (int i = 0; i < size; i++)
			{
				try
				{
					parameter[i] = in.getObject();
				} catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

}
