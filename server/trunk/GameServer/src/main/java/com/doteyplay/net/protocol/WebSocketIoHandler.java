package com.doteyplay.net.protocol;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.doteyplay.game.service.pipeline.GatewayServerPipeline;
import com.doteyplay.game.service.runtime.GlobalSessionCache;

/** 
* 
* 
*/
public class WebSocketIoHandler extends IoHandlerAdapter
{

	private static final Logger logger = Logger
			.getLogger(WebSocketIoHandler.class);
	
	public static final String INDEX_KEY = WebSocketIoHandler.class.getName()
			+ ".INDEX";

	private static final String Exception_Key = "ExceptionCounts";

	private GatewayServerPipeline pipeline;

	// key=sessionId value = session sid 和 session对应
	private Map<Long, IoSession> ioSessionMap = new HashMap<Long, IoSession>();

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception
	{
		// System.out.println(ioBufferToString(message));
		// 吧传入的消息转换成流
		IoBuffer buffer = (IoBuffer) message;
		// 转换成字节数组
		byte[] b = new byte[buffer.limit()];
		buffer.get(b);

		// 获取sessionId
		Long sid = session.getId();

		// 如果没有此sessionId则代表第一次连接
		if (!ioSessionMap.containsKey(sid))
		{
			// 把此session放入map
			ioSessionMap.put(sid, session);

			byte[] bufferAry = buffer.array();
			String m = new String(bufferAry);

			// 获取握手协议字符串
			String sss = getSecWebSocketAccept(m);

			buffer.clear();
			buffer.put(sss.getBytes("utf-8"));

			buffer.flip();
			session.write(buffer);

			buffer.free();
		} else
		{
			// 存在session
			// 解析传输的数据内容
			pipeline.dispatchAction(session, (IoBuffer) message);
		}
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception
	{
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60);
	}

	/**
	 * 当ws连接断开时触发
	 */
	@Override
	public void sessionClosed(IoSession session) throws Exception
	{
		// 如果不连接的话 则删除
		ioSessionMap.remove(session);
		GlobalSessionCache.getInstance().disconnect(session);
	}

	/**
	 * 
	 * @author  获取握手协议 字符串 首先要获取到请求头中的Sec-WebSocket-Key的值，再把这一段GUID
	 *         "258EAFA5-E914-47DA-95CA-C5AB0DC85B11
	 *         加到获取到的Sec-WebSocket-Key的值的后面，然后拿这个字符串做SHA-1
	 *         hash计算，然后再把得到的结果通过base64加密
	 * @param key
	 * @return
	 */
	private String getSecWebSocketAccept(String key)
	{
		String secKey = getSecWebSocketKey(key);

		String guid = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
		secKey += guid;
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(secKey.getBytes("iso-8859-1"), 0, secKey.length());
			byte[] sha1Hash = md.digest();
			secKey = base64Encode(sha1Hash);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		String rtn = "HTTP/1.1 101 Switching Protocols\r\nUpgrade: websocket\r\nConnection: Upgrade\r\nSec-WebSocket-Accept: "
				+ secKey + "\r\n\r\n";
		return rtn;
	}

	/**
	 * 
	 * @author 获取到请求头中的Sec-WebSocket-Key的
	 * @param req
	 * @return
	 */
	private String getSecWebSocketKey(String req)
	{
		Pattern p = Pattern.compile("^(Sec-WebSocket-Key:).+",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		Matcher m = p.matcher(req);
		if (m.find())
		{
			String foundstring = m.group();
			return foundstring.split(":")[1].trim();
		} else
		{
			return null;
		}

	}

	/**
	 * base64
	 * 
	 * @param input
	 * @return
	 */
	private String base64Encode(byte[] input)
	{
		return new String(org.apache.mina.util.Base64.encodeBase64(input));
	}

	/**
	 * @author  把传入的消息解码
	 * @param receivedDataBuffer
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String decode(byte[] receivedDataBuffer)
			throws UnsupportedEncodingException
	{
		String result = null;

		// 数据开始的位数 前面2个byte 固定必须存在
		int dataStartIndex = 2;

		// 查看第一帧的值 代表是否结束
		int isend = receivedDataBuffer[0] >> 7 & 0x1;
		System.out.println("是否结束：【" + (isend == 1 ? "yes" : "no") + "】");

		// 获取是否需要掩码
		boolean mask = ((receivedDataBuffer[1] >> 7 & 0x1) == 1) ? true : false;

		System.out.println("掩码：【" + (mask ? "yes" : "no") + "】");

		// Payload length: 传输数据的长度，以字节的形式表示：7位、7+16位、或者7+64位。
		// 如果这个值以字节表示是0-125这个范围，那这个值就表示传输数据的长度；
		// 如果这个值是126，则随后的两个字节表示的是一个16进制无符号数，用来表示传输数据的长度；
		// 如果这个值是127,则随后的是8个字节表示的一个64位无符合数，这个数用来表示传输数据的长度
		int dataLength = receivedDataBuffer[1] & 0x7F;

		System.out.println("描述消息长度：【" + dataLength + "】");

		// 查看 消息描述 是否大于 126 如果大于

		if (dataLength < 126)
		{
			// 126以内取本身
		} else if (dataLength == 126)
		{
			dataStartIndex = dataStartIndex + 2;
		} else if (dataLength == 127)
		{
			dataStartIndex = dataStartIndex + 8;
		}

		// 掩码数组
		byte[] frameMaskingAry = new byte[4];
		if (mask)
		{
			for (int i = 0; i < frameMaskingAry.length; i++)
			{
				frameMaskingAry[i] = receivedDataBuffer[dataStartIndex + i];
			}
			dataStartIndex += 4;
		}

		// 计算非空位置
		int lastStation = receivedDataBuffer.length - 1;

		// 利用掩码对org-data进行异或
		int frame_masking_key = 0;

		// 保存数据的 数组
		byte[] dataByte = new byte[lastStation - dataStartIndex + 1];

		if (mask)
		{
			for (int i = dataStartIndex; i <= lastStation; i++)
			{
				// 吧数据进行异或运算
				receivedDataBuffer[i] = (byte) (receivedDataBuffer[i] ^ frameMaskingAry[frame_masking_key % 4]);
				// 吧进行异或运算之后的 数据放入数组
				dataByte[i - dataStartIndex] = receivedDataBuffer[i];
				frame_masking_key++;
			}
		}

		result = new String(dataByte, "UTF-8");
		return result;

	}

	public Map<Long, IoSession> getIoSessionMap()
	{
		return ioSessionMap;
	}

	public void setIoSessionMap(Map<Long, IoSession> ioSessionMap)
	{
		this.ioSessionMap = ioSessionMap;
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
	{
		if (cause instanceof java.io.IOException)
			return;
		logger.error(cause.getMessage(), cause);
	}


	public void setPipeline(GatewayServerPipeline pipeline)
	{
		this.pipeline = pipeline;
	}
}