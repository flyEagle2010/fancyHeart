package com.doteyplay.utils;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;


public class BufferUtil
{
	
	protected static final CharsetDecoder CHARSETDECODER = Charset.forName("UTF-8").newDecoder();
	protected static final CharsetEncoder CHARSETENCODER = Charset.forName("UTF-8").newEncoder();
	
	public final static void putBoolean(IoBuffer out, boolean value)
	{
		byte t = value ? (byte) 1 : (byte) 0;
		out.put(t);
	}
	
	
	public final static boolean getBoolean(IoBuffer in)
	{
		byte t = in.get();
		if (t == 1)
			return true;
		else
			return false;
	}
	
	/**
	 * ��ָ����Buffer��д��String
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
			out.putShort((short) b.length);
			out.put(b);
		}
		catch (Exception e)
		{

		}
	}
	
	/**
	 * ��Buffer�ж�ȡString
	 * 
	 * @param in
	 * @return
	 */
	public final static String getString(IoBuffer in)
	{
		short length = in.getShort();
		try
		{
			return in.getString(length, CHARSETDECODER);
		}
		catch (CharacterCodingException e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public final static byte[] getRemainBytes(IoBuffer in)
	{
		int length = in.limit() - in.position();
		byte[] tmp = new byte[length];
		in.get(tmp, 0, length);
		return tmp;
	}
}
