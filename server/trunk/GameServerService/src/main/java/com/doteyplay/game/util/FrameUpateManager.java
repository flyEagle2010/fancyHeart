package com.doteyplay.game.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

public class FrameUpateManager<T extends IFrameUpdatable>
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FrameUpateManager.class);

	protected Collection<T> list = new ConcurrentLinkedQueue<T>();
	
	/**
	 * 
	 */
	public FrameUpateManager()
	{
	}

	public void add(T obj)
	{
		this.list.add(obj);
	}
	
	public void remove(T obj)
	{
		this.list.remove(obj);
	}
	
	public void release()
	{
		list.clear();
	}
	public void update()
	{
		
		boolean changed = false;
		Iterator<T> it = list.iterator();
		while(it.hasNext())
		{
			IFrameUpdatable obj = it.next();
			try
			{
				if(obj.update())
				{
					it.remove();
					changed = true;
				}
			}
			catch (Throwable e)
			{
				logger.error(e.getMessage(), e);
			}
		}
		
	}
}
