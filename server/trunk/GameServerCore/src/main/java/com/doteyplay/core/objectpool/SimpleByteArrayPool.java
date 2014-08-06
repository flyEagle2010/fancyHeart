package com.doteyplay.core.objectpool;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

//虽然不是很严格的同步，但是不会出现太多内存泄露
public class SimpleByteArrayPool
{
	private final int arrayLength;
	private final int poolSize;
	private final AtomicInteger curPoolSize;
	private final ConcurrentLinkedQueue<byte[]> pool;
	
	public SimpleByteArrayPool(int poolsize, int arraylength)
	{
		this.arrayLength = arraylength;
		this.poolSize = poolsize;
		pool = new ConcurrentLinkedQueue<byte[]>();
		for(int i = 0 ; i < poolSize ; i++)
			pool.offer(new byte[arrayLength]);
		curPoolSize = new AtomicInteger(poolSize);
	}
	
	public byte[] borrow()
	{
		byte[] tmp = pool.poll();
		if(tmp == null)
			tmp = new byte[arrayLength];
		else
		{
			if(curPoolSize.get() > 0)
				curPoolSize.decrementAndGet();
		}
		return tmp;
	}
	
	public void free(byte[] bytes)
	{
		if(curPoolSize.get() > poolSize)
			return;
		if(bytes.length != arrayLength)
			return;
		Arrays.fill(bytes, (byte)0);
		pool.offer(bytes);
		curPoolSize.incrementAndGet();
	}
}
