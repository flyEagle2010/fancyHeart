package com.doteyplay.net.codec;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.doteyplay.net.message.AbstractMessage;

public class DefaultMessageDecoder extends CumulativeProtocolDecoder
{

	private final static Logger logger = Logger
			.getLogger(DefaultMessageDecoder.class);

	@Override
	public boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception
	{
		short headerFlag = AbstractMessage.MESSAGE_HEADER_FLAG;
		int start = in.position();
		int limit = in.limit();

		// 如果剩余少于2字节就等待消息头标识
		if (limit - start < 2)
			return false;

		// 查找消息头标识0x2425
		int i = start;
		while (i < limit - 1)
		{
			int tmp  = in.getShort(i);
			if (tmp == headerFlag)
				break;
			i++;
		}

		// 设置指针指向消息头标识
		in.position(i);
		
		// 如果没有找到则跳过
		if (i == limit - 1)
		{
			return true;
		}
		
		start = i;

		//跳过校验码后开始继续读
		i++;
		i++;

		// 如果剩余少于2字节就等待消息体长度
		if (limit - i < 2)
			return false;

		// 读取消息体长度
		short messageLength = in.getShort(i);

		// 如果剩余少于消息体长度就等待消息体数据
		if (limit - i < messageLength)
			return false;

		// 将消息体剪切出来以IoBuffer类型发送给后续逻辑处理
		IoBuffer message = in.getSlice(start, messageLength + 2);
		out.write(message);

		// 强制调过本次数据块,矫正指针位置
		in.position(start + messageLength + 2);
		return true;
	}
}