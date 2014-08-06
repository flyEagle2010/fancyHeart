package com.doteyplay.luna.common.protocol.codec;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

import com.doteyplay.luna.common.LunaConstants;
import com.doteyplay.luna.common.message.DecoderMessage;

/**
 * 执行解码的类
 */
public class DefaultMessageDecoder implements MessageDecoder
{

	private final static Logger logger = Logger
			.getLogger(DefaultMessageDecoder.class);

	/**
	 * 检测是否可以被完整的解码
	 */
	@Override
	public MessageDecoderResult decodable(IoSession session, IoBuffer in)
	{
		if (session == null || in == null)
			return MessageDecoderResult.NOT_OK;
		if (!session.isConnected())
			return MessageDecoderResult.NOT_OK;
		if (in.remaining() < LunaConstants.VALIDATOR_LENGTH)
		{
			return MessageDecoderResult.NEED_DATA;
		}
		// 这里需要验证IoBuffer中的头两个字节是否合法
		short validatorCode = in.getShort();
		if (LunaConstants.SERVER_VALIDATOR_CODE != validatorCode
				&& LunaConstants.CLIENT_VALIDATOR_CODE != validatorCode)
		{
			logger.error("[luna]解码时消息头错误.[" + validatorCode + "]...RemoteIP="
					+ session.getRemoteAddress().toString() + ".LocalIP="
					+ session.getLocalAddress().toString());
			session.close(true);
			return MessageDecoderResult.NOT_OK;
		}
		// 检测报文头是否完整
		if (in.remaining() < LunaConstants.MESSAGE_LENGTH
				+ LunaConstants.ROLEID_LENGTH + LunaConstants.SYNKEY_LENGTH  +LunaConstants.COMMAND_LENGTH)
		{
			logger.error("[luna]报文内容不够，需要等待报文" + in.remaining());
			return MessageDecoderResult.NEED_DATA;
		}
		// 验证报文长度是否合法
		int messageLength = in.getInt();// 报文长度
		if (messageLength > LunaConstants.MAX_MESSAGE_LENGTH
				|| messageLength < 0)
		{
			logger.error("[luna]强制关闭会话 : 消息体长度超过"
					+ LunaConstants.MAX_MESSAGE_LENGTH + "个字节,报文长度["
					+ messageLength + "]..." + ".RemoteIP="
					+ session.getRemoteAddress().toString() + ".LocalIP="
					+ session.getLocalAddress().toString());
			session.close(true);
			return MessageDecoderResult.NOT_OK;
		} else if (messageLength < LunaConstants.MIN_MESSAGE_LENGTH)
		{
			return MessageDecoderResult.NEED_DATA;
		}
		// 验证消息体是否完整
		if (in.remaining() >= messageLength - 4)
		{
			if (logger.isDebugEnabled())
				logger.debug("REMAIN:" + in.remaining());
			if (logger.isDebugEnabled())
				logger.debug("消息完整");
			return MessageDecoderResult.OK;
		} else
		{
			return MessageDecoderResult.NEED_DATA;
		}
	}

	/**
	 * 执行解码的操作
	 */
	@Override
	public MessageDecoderResult decode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception
	{
		if (session == null || in == null)
			return MessageDecoderResult.NOT_OK;
		if (!session.isConnected())
			return MessageDecoderResult.NOT_OK;

		in.getShort();// 读取消息头标志
		int messageLength = in.getInt();// 读取报文长度
		long roleId = in.getLong();// 读取角色编号
		short commandId = in.getShort();// 读取指令编号
		long synKey = in.getLong();
		
		
		DecoderMessage message = new DecoderMessage(commandId, messageLength,
				roleId,synKey);
		
		try
		{
			message.setBuffer(in);
		} catch (Exception e)
		{
			logger.error("【luna】强制关闭会话 : 消息体解析错误[commandId=" + commandId
					+ ":RemoteIP=" + session.getRemoteAddress().toString()
					+ ".LocalIP=" + session.getLocalAddress().toString() + "]",
					e);
			logger.error(in.getHexDump());
			session.close(true);
			return MessageDecoderResult.NOT_OK;
		}
		out.write(message);// 发送给IoHandler
		return MessageDecoderResult.OK;
	}

	/**
	 * 当解码结束后所要进行的操作
	 */
	@Override
	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1)
			throws Exception
	{
		if (logger.isDebugEnabled())
			logger.debug("-----解码结束----");
	}
}
