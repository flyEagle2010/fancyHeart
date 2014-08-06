package com.doteyplay.game.service.bo.item;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

import com.doteyplay.game.util.StackTrace;

public class ItemLock
{
	private static final Logger logger = Logger.getLogger(ItemLock.class);

	private AtomicBoolean lock = new AtomicBoolean(false);
	private String threadInfo = null;
	private long serviceId;

	public ItemLock(long serviceId)
	{
		this.serviceId = serviceId;
	}

	public AtomicBoolean getLock()
	{
		return lock;
	}

	public void setLock(AtomicBoolean lock)
	{
		this.lock = lock;
	}

	public String getThreadInfo()
	{
		return threadInfo;
	}

	public void setThreadInfo(String threadInfo)
	{
		this.threadInfo = threadInfo;
	}

	public boolean locked()
	{
		return lock.get();
	}
	
	public boolean lock()
	{
		boolean getLock = lock.compareAndSet(false, true);

		String curStackInfo = StackTrace.getStackTrace(ItemLock.class);
		
		//打印锁住的线程和当前失败的线程
		if (!getLock)
			logger.error("getLock fail,serviceId:" + serviceId
					+ ",/n lord Thread:" + threadInfo + "/n" + "curTread:"
					+ curStackInfo);
		else
			threadInfo = curStackInfo;

		return getLock;
	}

	public void unlock()
	{
		lock.set(false);
		threadInfo = null;
	}
}
