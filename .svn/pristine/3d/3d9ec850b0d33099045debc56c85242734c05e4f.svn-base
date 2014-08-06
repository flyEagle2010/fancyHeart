package com.doteyplay.core.monitor;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

import com.doteyplay.core.monitor.domain.ClipInfo;
import com.doteyplay.core.monitor.manager.SampleManager;

/**
 * 
 * @version
 */
public class Monitor
{

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger("Monitor");

	/**
	 * 开关
	 */
	private static AtomicBoolean isOpen = new AtomicBoolean(false);
	
	/**
	 * 线程对应正在执行信息表
	 */
	private static ConcurrentHashMap<Long, ClipInfo> threadMethodInfoMap = new ConcurrentHashMap<Long, ClipInfo>();

	/**
	 * 开启采集
	 * 
	 * @Title: beginSamlping
	 * @Description:
	 * @param
	 * @return void
	 * @throws
	 */
	public static void beginSamlping()
	{
		int tmp = 5000;
		while (tmp > 0)
		{
			try
			{
				Thread.sleep(1);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			tmp--;
		}
		isOpen.set(true);
	}

	/**
	 * 停止采集 开始回收
	 * 
	 * @Title: stopSamlping
	 * @Description:
	 * @param
	 * @return void
	 * @throws
	 */
	public static void stopSamlping()
	{
		isOpen.set(false);
		
		SampleManager.getInstance().putOutInfo(MonitorSchedule.getCurrentSampleCount());
		SampleManager.getInstance().release();
	}

	/**
	 * 标识线程对应处理指令
	 * 
	 * @Title: mark
	 * @Description:
	 * @param
	 * @param commandID
	 * @return void
	 * @throws
	 */
	public static void mark(String methodName,int portalId)
	{
		if (!isOpen.get())
		{
			return;
		}
		long id = Thread.currentThread().getId();
		ClipInfo info = new ClipInfo();
		info.setPortalId(portalId);
		info.setMethodName(methodName);
		threadMethodInfoMap.put(id, info);
	}
	
	public static void recordGetCallIoSize(long value)
	{
		if (!isOpen.get())
		{
			return;
		}
		long id = Thread.currentThread().getId();
		if(threadMethodInfoMap.containsKey(id)){
			ClipInfo info = threadMethodInfoMap.get(id);
			if(info!=null)
				info.addGetCallIoSize(value);
		}
	}
	
	public static void recordGetReturnIoSize(long value)
	{
		if (!isOpen.get())
		{
			return;
		}
		long id = Thread.currentThread().getId();
		if(threadMethodInfoMap.containsKey(id)){
			ClipInfo info = threadMethodInfoMap.get(id);
			if(info!=null)
				info.addGetReturnIoSize(value);	
		}
	}
	
	public static void recordSendCallIoSize(long value)
	{
		if (!isOpen.get())
		{
			return;
		}
		long id = Thread.currentThread().getId();
		if(threadMethodInfoMap.containsKey(id)){
			ClipInfo info = threadMethodInfoMap.get(id);
			if(info!=null)
				info.addSendCallIoSize(value);
		}
	}
	
	public static void recordSendReturnIoSize(long value)
	{
		if (!isOpen.get())
		{
			return;
		}
		long id = Thread.currentThread().getId();
		if(threadMethodInfoMap.containsKey(id)){
			ClipInfo info = threadMethodInfoMap.get(id);
			if(info!=null)
				info.addSendReturnIoSize(value);
		}
	}
	
	public static void recordSendMessageIoSize(long value)
	{
		if (!isOpen.get())
		{
			return;
		}
		long id = Thread.currentThread().getId();
		if(threadMethodInfoMap.containsKey(id)){
			ClipInfo info = threadMethodInfoMap.get(id);
			if(info!=null)
				info.addSendMessageIoSize(value);
		}
	}
	
	/**
	 * 发采样切片
	 * 
	 * @Title: postSimpleInfo
	 * @Description:
	 * @param
	 * @param markWord
	 * @param
	 * @param sampletype
	 * @param
	 * @param timecost
	 * @param
	 * @param extdetail
	 * @return void
	 * @throws
	 */
	public static void recordSimpleInfo()
	{
		if (!isOpen.get())
		{
			return;
		}		
		long id = Thread.currentThread().getId();
		if(threadMethodInfoMap.containsKey(id)){
			ClipInfo info = threadMethodInfoMap.get(id);
			if(info!=null){
				info.setEndTime(new Date().getTime());
				SampleManager.getInstance().analy(info);
			}
		}
	}

}
