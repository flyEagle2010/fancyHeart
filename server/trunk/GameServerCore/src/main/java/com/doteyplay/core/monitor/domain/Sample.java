package com.doteyplay.core.monitor.domain;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;

public class Sample implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 服务Id
	 */
	private int portalId = 0;

	/**
	 * 请求的方法名
	 */
	private String methodName;

	/**
	 * 采样次数
	 */
	private int count;

	/**
	 * 采样点时间
	 */
	private long curTime = new Date().getTime();

	/**
	 * 样本原子执行总时间
	 */
	private long totalCost = 0;

	/**
	 * 采样最大执行时间
	 */
	private long maxCost = 0;

	/**
	 * 采样最小执行时间
	 */
	private long minCost = 0;
	/**
	 * 平均时间
	 */
	private long avaCost = 0;

	/**
	 * 下行累计流量
	 */
	private long totalGetIoSize = 0l;

	private long maxGetIoSize = 0;

	private long minGetIoSize = 0;

	private long avaGetIoSize = 0;

	/**
	 * 上行累计流量
	 */
	private long totalSendIoSize = 0l;

	private long maxSendIoSize = 0l;

	private long minSendIoSize = 0l;

	private long avaSendIoSize = 0l;

	/**
	 * 接收访问的请求流量【下行】
	 */
	private long totalGetCallIoSize;

	private long maxGetCallIoSize;

	private long minGetCallIoSize;

	private long avaGetCallIoSize;

	/**
	 * 接收到调用其他服务返回的流量【下行】
	 */
	private long totalGetReturnIoSize;

	private long maxGetReturnIoSize;

	private long minGetReturnIoSize;

	private long avaGetReturnIoSize;

	/**
	 * 调用其他服务时发送出去的流量【上行】;
	 */
	private long totalSendCallIoSize;

	private long maxSendCallIoSize;

	private long minSendCallIoSize;

	private long avaSendCallIoSize;

	/**
	 * 外界调用方法return时送出去的流量【上行】
	 */
	private long totalSendReturnIoSize;

	private long maxSendReturnIoSize;

	private long minSendReturnIoSize;

	private long avaSendReturnIoSize;

	/**
	 * sendmessage时送出去的流量【上行】
	 */
	private long totalSendMessageIoSize;

	private long maxSendMessageIoSize;

	private long minSendMessageIoSize;

	private long avaSendMessageIoSize;

	public void addUp(ClipInfo info) {
		this.addUpCost(info);
		this.addUpGetReturnIoSize(info);
		this.addUpGetCallIoSize(info);
		this.addUpGetIoSize(info);
		this.addUpSendCallIoSize(info);
		this.addUpSendIoSize(info);
		this.addUpSendMessageIoSize(info);
		this.addUpSendReturnIoSize(info);

		this.count++;
	}

	private void addUpCost(ClipInfo info) {
		long cost = info.getProcessCost();
		totalCost += cost;

		if (this.count <= 0) {
			maxCost = cost;
			minCost = cost;
		} else {
			if (cost > maxCost)
				maxCost = cost;

			if (cost < minCost)
				minCost = cost;
		}
	}

	private void addUpGetIoSize(ClipInfo info) {
		long getIoSize = info.getGetCallIoSize() + info.getGetReturnIoSize();
		totalGetIoSize += getIoSize;

		if (this.count <= 0) {
			maxGetIoSize = getIoSize;
			minGetIoSize = getIoSize;
		} else {
			if (getIoSize > maxGetIoSize)
				maxGetIoSize = getIoSize;

			if (getIoSize < minGetIoSize)
				minGetIoSize = getIoSize;
		}
	}

	private void addUpSendIoSize(ClipInfo info) {
		long sendIoSize = info.getSendCallIoSize()
				+ info.getSendMessageIoSize() + info.getSendReturnIoSize();
		totalSendIoSize += sendIoSize;

		if (this.count <= 0) {
			maxSendIoSize = sendIoSize;
			minSendIoSize = sendIoSize;
		} else {
			if (sendIoSize > maxSendIoSize)
				maxSendIoSize = sendIoSize;

			if (sendIoSize < minSendIoSize)
				minSendIoSize = sendIoSize;
		}
	}

	private void addUpGetCallIoSize(ClipInfo info) {
		long getCallIoSize = info.getGetCallIoSize();
		totalGetCallIoSize += getCallIoSize;

		if (this.count <= 0) {
			maxGetCallIoSize = getCallIoSize;
			minGetCallIoSize = getCallIoSize;
		} else {
			if (getCallIoSize > maxGetCallIoSize)
				maxGetCallIoSize = getCallIoSize;

			if (getCallIoSize < minGetCallIoSize)
				minGetCallIoSize = getCallIoSize;
		}
	}

	private void addUpGetReturnIoSize(ClipInfo info) {
		long getReturnIoSize = info.getGetReturnIoSize();
		totalGetReturnIoSize += getReturnIoSize;

		if (this.count <= 0) {
			maxGetReturnIoSize = getReturnIoSize;
			minGetReturnIoSize = getReturnIoSize;
		} else {
			if (getReturnIoSize > maxGetReturnIoSize)
				maxGetReturnIoSize = getReturnIoSize;

			if (getReturnIoSize < minGetReturnIoSize)
				minGetReturnIoSize = getReturnIoSize;
		}
	}

	private void addUpSendCallIoSize(ClipInfo info) {
		long sendCallIoSize = info.getSendCallIoSize();
		totalSendCallIoSize += sendCallIoSize;

		if (this.count <= 0) {
			maxSendCallIoSize = sendCallIoSize;
			minSendCallIoSize = sendCallIoSize;
		} else {
			if (sendCallIoSize > maxSendCallIoSize)
				maxSendCallIoSize = sendCallIoSize;

			if (sendCallIoSize < minSendCallIoSize)
				minSendCallIoSize = sendCallIoSize;
		}
	}

	private void addUpSendReturnIoSize(ClipInfo info) {
		long sendReturnIoSize = info.getSendReturnIoSize();
		totalSendReturnIoSize += sendReturnIoSize;

		if (this.count <= 0) {
			maxSendReturnIoSize = sendReturnIoSize;
			minSendReturnIoSize = sendReturnIoSize;
		} else {
			if (sendReturnIoSize > maxSendReturnIoSize)
				maxSendReturnIoSize = sendReturnIoSize;

			if (sendReturnIoSize < minSendReturnIoSize)
				minSendReturnIoSize = sendReturnIoSize;
		}
	}

	private void addUpSendMessageIoSize(ClipInfo info) {
		long sendMessageIoSize = info.getSendMessageIoSize();
		totalSendMessageIoSize += sendMessageIoSize;

		if (this.count <= 0) {
			maxSendMessageIoSize = sendMessageIoSize;
			minSendMessageIoSize = sendMessageIoSize;
		} else {
			if (sendMessageIoSize > maxSendMessageIoSize)
				maxSendMessageIoSize = sendMessageIoSize;

			if (sendMessageIoSize < minSendMessageIoSize)
				minSendMessageIoSize = sendMessageIoSize;
		}
	}

	/**
	 * @return the avaGetIoSize
	 */
	public long getAvaGetIoSize() {
		if (this.count > 0)
			avaGetIoSize = totalGetIoSize / count;
		return avaGetIoSize;
	}

	/**
	 * @return the avaSendIoSize
	 */
	public long getAvaSendIoSize() {
		if (this.count > 0)
			avaSendIoSize = totalSendIoSize / count;
		return avaSendIoSize;
	}

	/**
	 * @return the avaGetCallIoSize
	 */
	public long getAvaGetCallIoSize() {
		if (this.count > 0)
			avaGetCallIoSize = totalGetCallIoSize / count;
		return avaGetCallIoSize;
	}

	/**
	 * @return the avaGetReturnIoSize
	 */
	public long getAvaGetReturnIoSize() {
		if (this.count > 0)
			avaGetReturnIoSize = totalGetReturnIoSize / count;
		return avaGetReturnIoSize;
	}

	/**
	 * @return the avaSendCallIoSize
	 */
	public long getAvaSendCallIoSize() {
		if (this.count > 0)
			avaSendCallIoSize = totalSendCallIoSize / count;
		return avaSendCallIoSize;
	}

	/**
	 * @return the avaSendReturnIoSize
	 */
	public long getAvaSendReturnIoSize() {
		if (this.count > 0)
			avaSendReturnIoSize = totalSendReturnIoSize / count;
		return avaSendReturnIoSize;
	}

	/**
	 * @return the avaSendMessageIoSize
	 */
	public long getAvaSendMessageIoSize() {
		if (this.count > 0)
			avaSendMessageIoSize = totalSendMessageIoSize / count;
		return avaSendMessageIoSize;
	}

	/**
	 * @return the avaCost
	 */
	public long getAvaCost() {
		if (this.count > 0)
			avaCost = totalCost / count;
		return avaCost;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName
	 *            the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the curTime
	 */
	public long getCurTime() {
		return curTime;
	}

	/**
	 * @param curTime
	 *            the curTime to set
	 */
	public void setCurTime(long curTime) {
		this.curTime = curTime;
	}

	/**
	 * @return the portalId
	 */
	public int getPortalId() {
		return portalId;
	}

	/**
	 * @param portalId
	 *            the portalId to set
	 */
	public void setPortalId(int portalId) {
		this.portalId = portalId;
	}

	/**
	 * @return the totalCost
	 */
	public long getTotalCost() {
		return totalCost;
	}

	/**
	 * @return the maxCost
	 */
	public long getMaxCost() {
		return maxCost;
	}

	/**
	 * @return the minCost
	 */
	public long getMinCost() {
		return minCost;
	}

	/**
	 * @return the totalGetIoSize
	 */
	public long getTotalGetIoSize() {
		return totalGetIoSize;
	}

	/**
	 * @return the maxGetIoSize
	 */
	public long getMaxGetIoSize() {
		return maxGetIoSize;
	}

	/**
	 * @return the minGetIoSize
	 */
	public long getMinGetIoSize() {
		return minGetIoSize;
	}

	/**
	 * @return the totalSendIoSize
	 */
	public long getTotalSendIoSize() {
		return totalSendIoSize;
	}

	/**
	 * @return the maxSendIoSize
	 */
	public long getMaxSendIoSize() {
		return maxSendIoSize;
	}

	/**
	 * @return the minSendIoSize
	 */
	public long getMinSendIoSize() {
		return minSendIoSize;
	}

	/**
	 * @return the totalGetCallIoSize
	 */
	public long getTotalGetCallIoSize() {
		return totalGetCallIoSize;
	}

	/**
	 * @return the maxGetCallIoSize
	 */
	public long getMaxGetCallIoSize() {
		return maxGetCallIoSize;
	}

	/**
	 * @return the minGetCallIoSize
	 */
	public long getMinGetCallIoSize() {
		return minGetCallIoSize;
	}

	/**
	 * @return the totalGetReturnIoSize
	 */
	public long getTotalGetReturnIoSize() {
		return totalGetReturnIoSize;
	}

	/**
	 * @return the maxGetReturnIoSize
	 */
	public long getMaxGetReturnIoSize() {
		return maxGetReturnIoSize;
	}

	/**
	 * @return the minGetReturnIoSize
	 */
	public long getMinGetReturnIoSize() {
		return minGetReturnIoSize;
	}

	/**
	 * @return the totalSendCallIoSize
	 */
	public long getTotalSendCallIoSize() {
		return totalSendCallIoSize;
	}

	/**
	 * @return the maxSendCallIoSize
	 */
	public long getMaxSendCallIoSize() {
		return maxSendCallIoSize;
	}

	/**
	 * @return the minSendCallIoSize
	 */
	public long getMinSendCallIoSize() {
		return minSendCallIoSize;
	}

	/**
	 * @return the totalSendReturnIoSize
	 */
	public long getTotalSendReturnIoSize() {
		return totalSendReturnIoSize;
	}

	/**
	 * @return the maxSendReturnIoSize
	 */
	public long getMaxSendReturnIoSize() {
		return maxSendReturnIoSize;
	}

	/**
	 * @return the minSendReturnIoSize
	 */
	public long getMinSendReturnIoSize() {
		return minSendReturnIoSize;
	}

	/**
	 * @return the totalSendMessageIoSize
	 */
	public long getTotalSendMessageIoSize() {
		return totalSendMessageIoSize;
	}

	/**
	 * @return the maxSendMessageIoSize
	 */
	public long getMaxSendMessageIoSize() {
		return maxSendMessageIoSize;
	}

	/**
	 * @return the minSendMessageIoSize
	 */
	public long getMinSendMessageIoSize() {
		return minSendMessageIoSize;
	}

	public String toString() {
		return "portalId=" + portalId + "," + "methodName=" + methodName + ","
				+ "count=" + count + "," + "curTime=" + curTime + ","
				+ "totalCost=" + totalCost + "," + "maxCost=" + maxCost + ","
				+ "minCost=" + minCost + "," + "avaCost=" + getAvaCost() + ","
				+ "totalGetIoSize=" + totalGetIoSize + "," + "maxGetIoSize="
				+ maxGetIoSize + "," + "minGetIoSize=" + minGetIoSize + ","
				+ "avaGetIoSize=" + getAvaGetIoSize() + ","
				+ "totalSendIoSize=" + totalSendIoSize + "," + "maxSendIoSize="
				+ maxSendIoSize + "," + "minSendIoSize=" + minSendIoSize + ","
				+ "avaSendIoSize=" + getAvaSendIoSize() + ","
				+ "totalGetCallIoSize=" + totalGetCallIoSize + ","
				+ "maxGetCallIoSize=" + maxGetCallIoSize + ","
				+ "minGetCallIoSize=" + minGetCallIoSize + ","
				+ "avaGetCallIoSize=" + getAvaGetCallIoSize() + ","
				+ "totalGetReturnIoSize=" + totalGetReturnIoSize + ","
				+ "maxGetReturnIoSize=" + maxGetReturnIoSize + ","
				+ "minGetReturnIoSize=" + minGetReturnIoSize + ","
				+ "avaGetReturnIoSize=" + getAvaGetReturnIoSize() + ","
				+ "totalSendCallIoSize=" + totalSendCallIoSize + ","
				+ "maxSendCallIoSize=" + maxSendCallIoSize + ","
				+ "minSendCallIoSize=" + minSendCallIoSize + ","
				+ "avaSendCallIoSize=" + getAvaSendCallIoSize() + ","
				+ "totalSendReturnIoSize=" + totalSendReturnIoSize + ","
				+ "maxSendReturnIoSize=" + maxSendReturnIoSize + ","
				+ "minSendReturnIoSize=" + minSendReturnIoSize + ","
				+ "avaSendReturnIoSize=" + getAvaSendReturnIoSize() + ","
				+ "totalSendMessageIoSize=" + totalSendMessageIoSize + ","
				+ "maxSendMessageIoSize=" + maxSendMessageIoSize + ","
				+ "minSendMessageIoSize=" + minSendMessageIoSize + ","
				+ "avaSendMessageIoSize=" + getAvaSendMessageIoSize();
	}

	public static String csvTitle() {
		return "portalId,methodName," + "count,curTime," + "totalCost,maxCost,"
				+ "minCost,avaCost," + "totalGetIoSize,maxGetIoSize,"
				+ "minGetIoSize,avaGetIoSize,"
				+ "totalSendIoSize,maxSendIoSize,"
				+ "minSendIoSize,avaSendIoSize,"
				+ "totalGetCallIoSize,maxGetCallIoSize,"
				+ "minGetCallIoSize,avaGetCallIoSize,"
				+ "totalGetReturnIoSize,maxGetReturnIoSize,"
				+ "minGetReturnIoSize,avaGetReturnIoSize,"
				+ "totalSendCallIoSize,maxSendCallIoSize,"
				+ "minSendCallIoSize,avaSendCallIoSize,"
				+ "totalSendReturnIoSize,maxSendReturnIoSize,"
				+ "minSendReturnIoSize,avaSendReturnIoSize,"
				+ "totalSendMessageIoSize,maxSendMessageIoSize,"
				+ "minSendMessageIoSize,avaSendMessageIoSize";
	}

	public String csv() {
		return portalId + "," + methodName + "," + count + "," + curTime + ","
				+ totalCost + "," + maxCost + "," + minCost + ","
				+ getAvaCost() + "," + totalGetIoSize + "," + maxGetIoSize
				+ "," + minGetIoSize + "," + getAvaGetIoSize() + ","
				+ totalSendIoSize + maxSendIoSize + "," + minSendIoSize + ","
				+ getAvaSendIoSize() + "," + totalGetCallIoSize + ","
				+ maxGetCallIoSize + "," + minGetCallIoSize + ","
				+ getAvaGetCallIoSize() + "," + totalGetReturnIoSize + ","
				+ maxGetReturnIoSize + "," + minGetReturnIoSize + ","
				+ getAvaGetReturnIoSize() + "," + totalSendCallIoSize + ","
				+ maxSendCallIoSize + "," + minSendCallIoSize + ","
				+ getAvaSendCallIoSize() + "," + totalSendReturnIoSize + ","
				+ maxSendReturnIoSize + "," + minSendReturnIoSize + ","
				+ getAvaSendReturnIoSize() + "," + totalSendMessageIoSize + ","
				+ maxSendMessageIoSize + "," + minSendMessageIoSize + ","
				+ getAvaSendMessageIoSize();
	}

	public static void main(String[] args) {
		for (Method f : Sample.class.getDeclaredMethods()) {
			if (f.getName().startsWith("get"))
				System.out.println(f.getName());
		}
	}
}
