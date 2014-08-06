package com.doteyplay.core.monitor.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

/**
 * 死锁线程检查
 * 
 */
public class ThreadLockCheck implements Runnable
{
	
	private static final Logger logger = Logger.getLogger(ThreadLockCheck.class);
	
	private AtomicBoolean isRunning = new AtomicBoolean(false);
	private long intervalTime = 30 * 1000;
	private int sampleCount = 30;
	private int curFinishedCount = 0;
	
	private Thread curThread = null;
	
	@Override
	public void run()
	{
		while(isRunning.get() && curFinishedCount < sampleCount)
		{
			printDeadThreadStack();
			curFinishedCount ++;
			try
			{
				Thread.sleep(intervalTime);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		isRunning.set(false);
	}
	
	public boolean start(long intervaltime, int samplecount)
	{
		if(intervaltime < 10)
		{
			System.out.println("采样时间间隔设置太小，最少10秒以上");
			return false;
		}
		if(this.isRunning.get())
			return false;
		this.isRunning.set(true);
		this.intervalTime = intervaltime * 1000;
		this.sampleCount = samplecount;
		
		curThread = new Thread(this);
		curThread.start();
		
		return true;
	}
	
	public void forceStop()
	{
		this.isRunning.set(false);
		curThread = null;
		curFinishedCount = 0;
	}
	
	public boolean isRunning()
	{
		return this.isRunning.get();
	}
	
	//**********************************************
	private static ThreadLockCheck instance = new ThreadLockCheck();
	
	public static ThreadLockCheck getInstance()
	{
		return instance;
	}
	
	public void printDeadThreadStack()
	{
		logger.error("printDeadThreadStack is running,left count = "+ (sampleCount - curFinishedCount));
		ThreadMXBean th = (ThreadMXBean) ManagementFactory.getThreadMXBean();
		long[] ll = th.findMonitorDeadlockedThreads();
		if (ll != null && ll.length > 0)
		{
			ThreadInfo[] tia = th.getThreadInfo(ll, Integer.MAX_VALUE);
			StringBuffer sb = new StringBuffer();
			sb.append("findMonitorDeadlockedThreads Info :\r\n");
			for (int i = 0; i < tia.length; i++)
			{
				ThreadInfo ti = tia[i];
				StackTraceElement[] ste = ti.getStackTrace();
				for (int j = 0; j < ste.length; j++)
				{
					sb.append(ste[j].toString());
					sb.append("\r\n");
				}
				sb.append("\r\n");
				sb.append("\r\n");
			}
			logger.error(sb.toString(), new RuntimeException("补获到死锁堆栈异常信息如下:"));
		}
	}
	
	public static void main(String[] args)
	{
		ThreadLockCheck.getInstance().start(11000, 3);
	}
//	static AtomicBoolean isA = new AtomicBoolean();
//	static CountDownLatch cd = new CountDownLatch(2);
//	
//	static Object lockA = new Object();
//	static Object lockB = new Object();
//	
//	public static void main(String[] argc)
//	{
//		init();
//	}


//	public static void init()
//	{
//		// new Timer().schedule(new ThreadLockCheck(), 0, 10 * 60 * 1000);
//		ExecutorService es = Executors.newFixedThreadPool(2);
//		es.execute(new ThreadLockCheck());
//		try
//		{
//			Thread.sleep(100);
//		}catch (Exception e) 
//		{
//			e.printStackTrace();
//		}
//		es.execute(new ThreadLockCheck());
//		
//		try
//		{
//			Thread.sleep(3000);
//		}catch (Exception e) 
//		{
//			e.printStackTrace();
//		}
//		printDeadThreadStack();
//		es.shutdown();
//	}
//
//	@Override
//	public void run()
//	{
//		if(isA.compareAndSet(false, true))
//			a();
//		else
//			b();
//	}
//	
//	public void a()
//	{
//		synchronized (lockA)
//		{
//			System.out.println(Thread.currentThread().getId()+"get a");
//			cd.countDown();
//			try
//			{
//				cd.await();
//			} catch (InterruptedException e)
//			{
//				e.printStackTrace();
//			}
//				b();
//		}
//	}
//	
//	public synchronized void b()
//	{
//		synchronized (lockB)
//		{
//			System.out.println(Thread.currentThread().getId()+"get b");
//			cd.countDown();
//			try
//			{
//				cd.await();
//			} catch (InterruptedException e)
//			{
//				e.printStackTrace();
//			}
//			a();
//		}
//	}
}
