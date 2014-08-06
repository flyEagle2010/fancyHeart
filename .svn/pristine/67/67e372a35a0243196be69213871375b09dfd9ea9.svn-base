package com.doteyplay.luna.common.message;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.luna.common.LunaConstants;

/**
 * 通用发送的消息对象，将要发送的消息进行编码
 */
public class EncoderMessage
{
	private static Logger logger = Logger.getLogger(EncoderMessage.class
			.getName());

	private IoBuffer buffer = IoBuffer
			.allocate(LunaConstants.DEFAULT_MESSAGE_LENGTH);
	/**
	 * 指令编号
	 */
	private short commandId;

	/**
	 * 是否是服务器端调用
	 */
	private boolean isClient;

	/**
	 * 角色ID
	 */
	private long roleId;

	/**
	 * 如果是同步请求消息，返回的encodeMessage里要把接受到的decodeMessage里的synKey发送回来
	 * enocdeMessage。setSynKey(decodeMessage.getSynKey());
	 */
	private long synKey;

	public EncoderMessage(short commandId, boolean client)
	{
		this.commandId = commandId;
		this.isClient = client;
		this.buffer.clear();
		this.buffer.setAutoExpand(true);
	}

	public void put(byte b)
	{
		this.buffer.put(b);
	}

	public void putByte(byte b)
	{
		this.buffer.put(b);
	}

	public void putByteArray(byte bytes[])
	{
		int length = bytes.length;
		buffer.putInt(length);
		buffer.put(bytes);
	}

	public void put(short s)
	{
		this.buffer.putShort(s);
	}

	public void putShort(short s)
	{
		this.buffer.putShort(s);
	}

	public void put(int i)
	{
		this.buffer.putInt(i);
	}

	public void putInt(int i)
	{
		this.buffer.putInt(i);
	}

	public void put(long l)
	{
		this.buffer.putLong(l);
	}

	public void putLong(long l)
	{
		this.buffer.putLong(l);
	}

	public void putBoolean(boolean b)
	{
		this.buffer.put(b ? LunaConstants.TRUE : LunaConstants.FALSE);
	}

	/**
	 * 存入对象
	 * 
	 * @param obj
	 */
	public void putObject(Object obj)
	{
		this.buffer.putObject(obj);
	}

	public void put(IoBuffer buffer)
	{
		buffer.put(buffer);
	}

	public void putBuffer(IoBuffer buffer)
	{
		buffer.put(buffer);
	}

	public void put(String str)
	{
		try
		{
			if (str == null || str.trim().equals(""))
			{
				this.buffer.putShort((short) 0);
			} else
			{
				byte[] b = str.getBytes("UTF-8");
				this.buffer.putShort((short) b.length);
				this.buffer.put(b);
			}
		} catch (Exception e)
		{
			logger.error("为IoBuffer设置String的时候发生错误", e);
		}
	}

	public void putString(String str)
	{
		try
		{
			if (str == null || str.trim().equals(""))
			{
				this.buffer.putShort((short) 0);
			} else
			{
				byte[] b = str.getBytes("UTF-8");
				this.buffer.putShort((short) b.length);
				this.buffer.put(b);
			}
		} catch (Exception e)
		{
			logger.error("为IoBuffer设置String的时候发生错误", e);
		}
	}

	/**
	 * 组织报文内容
	 * 
	 * @return
	 */
	public IoBuffer toBuffer()
	{
		int position = 0;
		if (this.buffer != null)
			position = this.buffer.position();
		IoBuffer end = IoBuffer
				.allocate(position + LunaConstants.HEADER_LENGTH);
		if (this.isClient)
			end.putShort(LunaConstants.SERVER_VALIDATOR_CODE);
		else
			end.putShort(LunaConstants.CLIENT_VALIDATOR_CODE);

		end.putInt(position + LunaConstants.MESSAGE_LENGTH
				+ LunaConstants.ROLEID_LENGTH + LunaConstants.COMMAND_LENGTH
				+ LunaConstants.SYNKEY_LENGTH);

		end.putLong(roleId);
		end.putShort(this.commandId);
		end.putLong(this.synKey);
		if (this.buffer != null)
		{
			this.buffer.rewind();
			end.put(this.buffer.getSlice(position));
			end.flip();
		}
		return end;
	}

	public IoBuffer getBuffer()
	{
		return this.buffer;
	}

	public short getCommandId()
	{
		return this.commandId;
	}

	public void setCommandId(short commandId)
	{
		this.commandId = commandId;
	}

	public boolean isClient()
	{
		return this.isClient;
	}

	public void setClient(boolean isClient)
	{
		this.isClient = isClient;
	}

	public long getRoleId()
	{
		return roleId;
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

	public int getBuffRealLength()
	{
		int position = buffer.position();
		int length = position + LunaConstants.VALIDATOR_LENGTH
				+ LunaConstants.MESSAGE_LENGTH + LunaConstants.ROLEID_LENGTH
				+ LunaConstants.COMMAND_LENGTH + LunaConstants.SYNKEY_LENGTH;
		return length;
	}
}
