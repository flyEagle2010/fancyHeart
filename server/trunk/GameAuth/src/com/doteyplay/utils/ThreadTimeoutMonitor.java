package com.doteyplay.utils;


public class ThreadTimeoutMonitor extends Thread
{
	long oEndTime = 0;
	int oSleepTime = 10;
	Thread oStartingThread = Thread.currentThread();
	Thread oRunningThread = null;

	public ThreadTimeoutMonitor(int aLength, int aSleepTime)
	{
		// Both times are in ms
		oSleepTime = aSleepTime;
		oEndTime = System.currentTimeMillis() + aLength;
	}

	public void run()
	{
		oRunningThread = Thread.currentThread();
		// Loop until the end time is reached or reset() was called.
		while (System.currentTimeMillis() < oEndTime)
		{			
				try
				{
					Thread.sleep(oSleepTime);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}			
		}
		if (oEndTime > 0)
		{
			timeout();
		}
	}

	public void reset()
	{
		oEndTime = 0;
	}

	public void timeout()
	{
		oStartingThread.interrupt();
	}
}
