package com.doteyplay.net.message;

import java.io.Serializable;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.core.configloader.MessageRegistry;
import com.doteyplay.core.util.CommonUtil;
import com.doteyplay.game.MessageCommands;

public abstract class AbstractMessage implements Serializable
{

	private static Logger logger = Logger.getLogger("AbstractMessage");
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 消息头标识
	 */
	public static final short MESSAGE_HEADER_FLAG = 3637;
	
	/**
	 * 发送缓冲默认大小 80%左右的消息都很小,所以初始设置小点
	 */
	public static final int MESSAGE_SENDBUFFER_DEFAULTSIZE = 256;
	/**
	 * 消息commandId索引位置
	 */
	public static final int MESSAGE_COMMANDID_INDEX = 4;

	protected static final CharsetDecoder CHARSETDECODER = Charset.forName(
			"UTF-8").newDecoder();
	protected static final CharsetEncoder CHARSETENCODER = Charset.forName(
			"UTF-8").newEncoder();
	public static final byte NULL_POINT_ERROR = 1;

	/**
	 * 协议命令ID
	 */
	protected short commandId;

	/**
	 * 消息体数据长度
	 */
	protected int totalLength;
	/**
	 * 消息总长度
	 */
	protected int bufferLength;
	/**
	 * 时间戳
	 */
	protected long timeStamp;
	/**
	 * 消息优先级
	 */
	protected int priority;
	
	private long sessionId;

	public AbstractMessage(MessageCommands command)
	{
		this.commandId = (short) command.COMMAND_ID;
		this.priority = MessageSettings.getPriority(commandId);
	}

	public byte[] getBytes(IoBuffer buf)
	{
		int length = buf.getShort();
		byte[] bytes = new byte[length];
		buf.get(bytes);
		return bytes;
	}
	
	public AbstractMessage()
	{
	}

	public byte[] getProtoBufBytes(IoBuffer buf)
	{
		int length = buf.getShort(buf.position() - 4) - 4;
		byte[] bytes = new byte[length];
		buf.get(bytes);
		return bytes;
	}
	
	/**
	 * 可管理释放函数
	 * 
	 * @return
	 */
	public static final void freeMessage(AbstractMessage message)
	{
		MessageRegistry.getInstance().freeMessage(message);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + ": commandId=" + commandId;
	}

	public abstract void release();

	public long getTimeStamp()
	{
		return timeStamp;
	}

	/**
	 * 解析包体。包头的解析已经完成。
	 * 
	 * @param in
	 */
	public abstract void decodeBody(IoBuffer in);

	/**
	 * 封装包体。包头的封装已经完成。
	 * 
	 * @param out
	 */
	public abstract void encodeBody(IoBuffer out);


	public final void decodeHeader(IoBuffer in) throws Exception
	{
		this.timeStamp = System.currentTimeMillis();
		// 消息头标识0x2425
		in.getShort();
		// 消息体长度
		totalLength = in.getShort();
		bufferLength = totalLength + 2;
		// 消息指令Id
		commandId = in.getShort();
	}

	public final void decodeMessage(IoBuffer in) throws Exception
	{
		decodeHeader(in);
		decodeBody(in);
	}

	public final void encodeHeader(IoBuffer out) throws Exception
	{
		// 消息头标识0x2425
		out.putShort(MESSAGE_HEADER_FLAG);
//		// 消息体长度
		out.putShort((short)0);
		// 消息指令Id
		out.putShort(commandId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.doteyplay.net.mina.handler.IMessage#encodeMessage(org.apache.
	 * mina.core.buffer.IoBuffer)
	 */
	public final void encodeMessage(IoBuffer out) throws Exception
	{
		encodeHeader(out);

		int start = out.position()-4;
		
		encodeBody(out);

		totalLength = out.position() - start;

		bufferLength = totalLength + 2;
		
		out.putShort(start, (short) totalLength);
	}

	public IoBuffer encodeIoBuffer()
	{
		IoBuffer ioBuffer = encodeIoBuffer(null);
		if (ioBuffer != null)
			ioBuffer.flip();

		return ioBuffer;
	}

	/**
	 * 消息内容封装到IoBuffer 可以获取真实的消息体长度和消息包总长度
	 * 
	 * @return
	 */
	public final IoBuffer encodeIoBuffer(IoBuffer buffer)
	{
		try
		{
			if (buffer == null)
			{
				// 创建IOBuffer
				buffer = IoBuffer.allocate(MESSAGE_SENDBUFFER_DEFAULTSIZE);
				buffer.setAutoExpand(true);
			}

			encodeMessage(buffer);
			return buffer;
		} catch (Exception e)
		{
			logger.error("指令编码发生错误，指令编号[" + this.commandId + "]", e);
		}
		return null;
	}


	/**
	 * 从Buffer中读取String
	 * 
	 * @param in
	 * @return
	 */
	public static final String getString(IoBuffer in)
	{
		short length = in.getShort();
		try
		{
			return in.getString(length, AbstractMessage.CHARSETDECODER);
		} catch (CharacterCodingException e)
		{
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 向指定的Buffer中写入String
	 * 
	 * @param out
	 * @param s
	 */
	public final static void putString(IoBuffer out, String s)
	{
		try
		{
			if (s == null || s.length() == 0)
			{
				out.putShort((short) 0);
				return;
			}
			byte[] b = s.getBytes("UTF-8");
			out.putShort((short) (b.length+1));
			out.put(b);
			out.put((byte)0);
		} catch (Exception e)
		{

		}
	}

	public final void setCommandId(short commandId)
	{
		this.commandId = commandId;
	}

	public final int getCommandId()
	{
		return commandId;
	}

	public final void setTotalLength(int totalLength)
	{
		this.totalLength = totalLength;
	}

	public final int getTotalLength()
	{
		return totalLength;
	}

	public final long getSessionId()
	{
		return sessionId;
	}

	public final static void putBoolean(IoBuffer out, boolean value)
	{
		out.put(CommonUtil.boolean2Byte(value));
	}

	public final static boolean getBoolean(IoBuffer in)
	{
		return CommonUtil.byte2Boolean(in.get());
	}

	public final int getBufferLength()
	{
		return bufferLength;
	}
	
	public static AbstractMessage createDefaultMessage(MessageCommands command,final byte[] data)
	{
		return new DefaultMessage(command,data);
	}

	public void setSessionId(long sessionId)
	{
		this.sessionId = sessionId;
	}
}
