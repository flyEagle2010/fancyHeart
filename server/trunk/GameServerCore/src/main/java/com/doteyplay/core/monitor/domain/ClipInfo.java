package com.doteyplay.core.monitor.domain;

import java.util.Date;

public class ClipInfo
{
	/**
	 * 服务Id
	 */
	private int portalId = 0;
	
	/**
	 * 请求的方法名
	 */
	private String methodName;
	/**
	 * 接收访问的请求流量【下行】
	 */
	private int getCallIoSize;
	
	/**
	 * 接收到调用其他服务返回的流量【下行】
	 */
	private int getReturnIoSize;
	
	/**
	 * 调用其他服务时发送出去的流量【上行】;
	 */
	private int sendCallIoSize;
	/**
	 * 外界调用方法return时送出去的流量【上行】
	 */
	private int sendReturnIoSize;
	/**
	 * sendmessage时送出去的流量【上行】
	 */
	private int sendMessageIoSize;
	
	/**
	 * 进入方法的开始时间
	 */
	private long startTime = new Date().getTime();
	
	/**
	 * 方法运行的完成时间
	 */
	private long endTime;
	
	
	public void addGetCallIoSize(long value)
	{
		this.getCallIoSize += value;
	}
	
	public void addSendCallIoSize(long value)
	{
		this.sendCallIoSize += value;
	}
	
	public void addSendReturnIoSize(long value)
	{
		this.sendReturnIoSize += value;
	}
	
	public void addSendMessageIoSize(long value)
	{
		this.sendMessageIoSize += value;
	}
	
	public void addGetReturnIoSize(long value)
	{
		this.getReturnIoSize += value;
	}
	
	public long getProcessCost()
	{
		return endTime - startTime;
	}
	
	/**
	 * @return the portalId
	 */
	public int getPortalId()
	{
		return portalId;
	}

	/**
	 * @param portalId the portalId to set
	 */
	public void setPortalId(int portalId)
	{
		this.portalId = portalId;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName()
	{
		return methodName;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName)
	{
		this.methodName = methodName;
	}

	/**
	 * @return the getCallIoSize
	 */
	public int getGetCallIoSize()
	{
		return getCallIoSize;
	}

	/**
	 * @param getCallIoSize the getCallIoSize to set
	 */
	public void setGetCallIoSize(int getCallIoSize)
	{
		this.getCallIoSize = getCallIoSize;
	}

	/**
	 * @return the getReturnIoSize
	 */
	public int getGetReturnIoSize()
	{
		return getReturnIoSize;
	}

	/**
	 * @param getReturnIoSize the getReturnIoSize to set
	 */
	public void setGetReturnIoSize(int getReturnIoSize)
	{
		this.getReturnIoSize = getReturnIoSize;
	}

	/**
	 * @return the sendCallIoSize
	 */
	public int getSendCallIoSize()
	{
		return sendCallIoSize;
	}

	/**
	 * @param sendCallIoSize the sendCallIoSize to set
	 */
	public void setSendCallIoSize(int sendCallIoSize)
	{
		this.sendCallIoSize = sendCallIoSize;
	}

	/**
	 * @return the sendReturnIoSize
	 */
	public int getSendReturnIoSize()
	{
		return sendReturnIoSize;
	}

	/**
	 * @param sendReturnIoSize the sendReturnIoSize to set
	 */
	public void setSendReturnIoSize(int sendReturnIoSize)
	{
		this.sendReturnIoSize = sendReturnIoSize;
	}

	/**
	 * @return the sendMessageIoSize
	 */
	public int getSendMessageIoSize()
	{
		return sendMessageIoSize;
	}

	/**
	 * @param sendMessageIoSize the sendMessageIoSize to set
	 */
	public void setSendMessageIoSize(int sendMessageIoSize)
	{
		this.sendMessageIoSize = sendMessageIoSize;
	}

	/**
	 * @return the startTime
	 */
	public long getStartTime()
	{
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(long startTime)
	{
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public long getEndTime()
	{
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(long endTime)
	{
		this.endTime = endTime;
	}
	
}
