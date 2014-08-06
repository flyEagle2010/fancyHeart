package com.doteyplay.luna.common.message;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.luna.common.LunaConstants;

/**
 * 通用接收消息的对象,将接收到的消息进行解码
 * 
 */
public class DecoderMessage
{
	private static Logger logger = Logger.getLogger(DecoderMessage.class
			.getName());

	/**
	 * 服务器传递回来的内容
	 */
	private IoBuffer buffer;

	/**
	 * 指令编号
	 */
	private short commandId;
	/**
	 * 报文长度
	 */
	private int messageLength;
	/**
	 * 判断Buffer是否为空
	 */
	private boolean bufferIsNull = false;
	/**
	 * 角色Id
	 */
	private long roleId;

	/**
	 * 创建DecoderMessage的时间，主要功能是用来计算内部处理的时间
	 */
	private long newTime;

	private long synKey;

	public DecoderMessage(short commandId, int length, long roleId, long synkey)
	{
		this.commandId = commandId;
		this.messageLength = length;
		this.synKey = synkey;
		this.roleId = roleId;
		this.newTime = System.currentTimeMillis();
	}

	public IoBuffer getBuffer()
	{
		return this.buffer;
	}

	public void setBuffer(IoBuffer buffer)
	{
		if ((this.messageLength - LunaConstants.MESSAGE_LENGTH - LunaConstants.ROLEID_LENGTH
				- LunaConstants.COMMAND_LENGTH - LunaConstants.SYNKEY_LENGTH) > 0)
			this.buffer = buffer.getSlice(this.messageLength - LunaConstants.MESSAGE_LENGTH
					- LunaConstants.ROLEID_LENGTH - LunaConstants.SYNKEY_LENGTH
					- LunaConstants.COMMAND_LENGTH);
		else
			this.bufferIsNull = true;
	}

	
	public byte[] getByteArray()
	{
		int length = buffer.getInt();
		byte bytes[] = new byte[length];
		buffer.get(bytes);
		return bytes;
	}
	
	public byte getByte()
	{
		return this.buffer.get();
	}

	public short getShort()
	{
		return this.buffer.getShort();
	}

	public int getInt()
	{
		return this.buffer.getInt();
	}

	public boolean getBoolean()
	{
		return this.buffer.get() == LunaConstants.TRUE;
	}

	public long getLong()
	{
		return this.buffer.getLong();
	}

	public String getString()
	{
		try
		{
			short length = this.buffer.getShort();
			return this.buffer.getString(length, LunaConstants.DECODER);
		} catch (Exception e)
		{
			logger.error("Response读取消息发生错误", e);
			return "";
		}
	}

	/**
	 * 返回对象
	 * 
	 * @return
	 */
	public Object getObject()
	{
		try
		{
			return this.buffer.getObject();
		} catch (ClassNotFoundException e)
		{
			logger.error("Response读取消息发生错误", e);
			return null;
		}
	}

	public String toString()
	{
		if (this.bufferIsNull)
			return "THIS IS A NULL COMMAND!";
		else
			return this.buffer.getHexDump();
	}

	public short getCommandId()
	{
		return this.commandId;
	}

	public void setCommandId(short commandId)
	{
		this.commandId = commandId;
	}

	public int getMessageLength()
	{
		return this.messageLength;
	}

	public void setMessageLength(int messageLength)
	{
		this.messageLength = messageLength;
	}

	public boolean bufferIsNull()
	{
		return this.bufferIsNull;
	}

	public long getNewTime()
	{
		return this.newTime;
	}

	public void setNewTime(long newTime)
	{
		this.newTime = newTime;
	}

	public long getRoleId()
	{
		return this.roleId;
	}

	public void setRoleId(long roleId)
	{
		this.roleId = roleId;
	}

	public long getSynKey()
	{
		return synKey;
	}

	public void setSynKey(long synKey)
	{
		this.synKey = synKey;
	}
}