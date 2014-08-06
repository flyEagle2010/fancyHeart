package com.doteyplay.utils;

import org.apache.mina.core.buffer.IoBuffer;

public class MessageUtil
{

	public static byte[] getBlob(IoBuffer buf)
	{
		short leng = buf.getShort();
		byte[] blob = new byte[leng];
		for (short i = 0; i < leng; i++)
		{
			blob[i] = buf.get();
		}
		return blob;
	}

	public static void putBytes(IoBuffer buf, byte[] bytes)
	{
		short leng = (short) bytes.length;
		for (short i = 0; i < leng; i++)
		{
			buf.put(bytes[i]);
		}
	}
}
