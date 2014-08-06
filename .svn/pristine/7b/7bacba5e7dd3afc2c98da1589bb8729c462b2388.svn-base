package com.doteyplay.net.message;

import com.doteyplay.game.MessageCommands;

/**
 * 消息队列相关信息设置: 包括基础优先级
 */
public class MessageSettings
{
	private MessageSettings()
	{
	}
	
	/**
	 * 获取指定消息的基础优先级
	 * @param commandId 消息ID
	 * @return
	 * @throws ArrayIndexOutOfBoundsException 如果消息ID无效,将抛出此异常
	 */
	public static int getPriority(int commandId)
	{
		return priorities[commandId];
	}
	
	private final static int[] priorities = new int[MessageCommands.MESSAGE_NUM];
	
	static
	{
		for(int i=0; i < MessageCommands.MESSAGE_NUM; ++i)
		{
			priorities[i] = 8;
		}
		
		initPriorities();
		checkPriorities();
	}
	
	/**
	 * 消息基础优先级自检
	 */
	private static void checkPriorities()
	{
		for(int i=0; i < MessageCommands.MESSAGE_NUM; ++i)
		{
			int value = priorities[i];
			if(value < 0 || value > 9)
				throw new IllegalArgumentException("消息:" + i + "的优先级设置无效:" + value);
		}
	}

	/**
	 * 定制消息基础优先级
	 */
	private static void initPriorities()
	{
		priorities[	MessageCommands.ACK_MESSAGE.COMMAND_ID ] = 9;
	}
}
