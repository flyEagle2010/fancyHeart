package com.doteyplay.core.util;

import org.apache.mina.core.buffer.IoBuffer;

public class ByteBufUitl
{
	public static byte[] getOutPut(EncodeBufCallBack cb)
	{
		IoBuffer out = IoBuffer.allocate(100);
		out.setAutoExpand(true);
		
		cb.encode(out);
		
		byte[] tmp = new byte[out.position()];
		System.arraycopy(out.array(), 0, tmp, 0, out.position());
		return tmp;
	}
	
	public static IoBuffer getOutPutBuf(EncodeBufCallBack cb)
	{
		IoBuffer out = IoBuffer.allocate(100);
		out.setAutoExpand(true);
		
		cb.encode(out);
		out.flip();
		return out;
	}
	
	/**
	 * 向指定的Buffer中写入String
	 * 
	 * @param out
	 * @param s
	 */
	public final static void putString(IoBuffer out, String s) {
		try {
			if (s == null || s.length() == 0) {
				out.putShort((short) 0);
				return;
			}
			byte[] b = s.getBytes("UTF-8");
			out.putShort((short) b.length);
			out.put(b);
		} catch (Exception e) {

		}
	}
}
