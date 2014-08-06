package com.doteyplay.utils;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AssembleUtils
{
	private final static String ASSEMBLE_CHARSET = "UTF-8";

	public static byte decodeByte(InputStream instream) throws IOException
	{
		return (byte) instream.read();
	}

	public static void encodeByte(OutputStream outstream, byte bv) throws IOException
	{
		outstream.write(bv & 0xFF);
	}

	public static int decodeUnsignedByte(InputStream instream) throws IOException
	{
		return instream.read();
	}

	public static void encodeUnsignedByte(OutputStream outstream, int bv) throws IOException
	{
		outstream.write(bv);
	}

	public static short decodeShort(InputStream instream) throws IOException
	{
		int ch1 = instream.read();
		int ch2 = instream.read();
		if ((ch1 | ch2) < 0)
			throw new EOFException();
		return (short) ((ch1 << 8) + (ch2 << 0));
	}

	public static void encodeShort(OutputStream outstream, int sv) throws IOException
	{
		outstream.write((sv >>> 8) & 0xFF);
		outstream.write((sv >>> 0) & 0xFF);
	}
	
	public static int decodeUnsignedShort(InputStream instream) throws IOException
	{
		int ch1 = instream.read();
		int ch2 = instream.read();
		if ((ch1 | ch2) < 0)
			throw new EOFException();
		return ((ch1 << 8) + (ch2 << 0));
	}

	public static void encodeUnsignedShort(OutputStream outstream, int iv) throws IOException
	{
		outstream.write((iv >>> 8) & 0xFF);
		outstream.write((iv >>> 0) & 0xFF);
	}

	public static int decodeInt(InputStream instream) throws IOException
	{
		int ch1 = instream.read();
		int ch2 = instream.read();
		int ch3 = instream.read();
		int ch4 = instream.read();
		if ((ch1 | ch2 | ch3 | ch4) < 0)
			throw new EOFException();
		return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
	}

	public static void encodeInt(OutputStream outstream, int iv) throws IOException
	{
		outstream.write((iv >>> 24) & 0xFF);
		outstream.write((iv >>> 16) & 0xFF);
		outstream.write((iv >>> 8) & 0xFF);
		outstream.write((iv >>> 0) & 0xFF);
	}

	public static long decodeLong(InputStream instream) throws IOException
	{
		byte[] tmpBuffer = new byte[8];
		int count = instream.read(tmpBuffer, 0, 8);
		if (count < 8)
			throw new IOException();
		return (((long) tmpBuffer[0] << 56) + ((long) (tmpBuffer[1] & 255) << 48)
				+ ((long) (tmpBuffer[2] & 255) << 40) + ((long) (tmpBuffer[3] & 255) << 32)
				+ ((long) (tmpBuffer[4] & 255) << 24) + ((tmpBuffer[5] & 255) << 16)
				+ ((tmpBuffer[6] & 255) << 8) + ((tmpBuffer[7] & 255) << 0));
	}

	public static void encodeLong(OutputStream outstream, long lv) throws IOException
	{
		byte[] tmpBuffer = new byte[8];
		tmpBuffer[0] = (byte) (lv >>> 56);
		tmpBuffer[1] = (byte) (lv >>> 48);
		tmpBuffer[2] = (byte) (lv >>> 40);
		tmpBuffer[3] = (byte) (lv >>> 32);
		tmpBuffer[4] = (byte) (lv >>> 24);
		tmpBuffer[5] = (byte) (lv >>> 16);
		tmpBuffer[6] = (byte) (lv >>> 8);
		tmpBuffer[7] = (byte) (lv >>> 0);
		outstream.write(tmpBuffer, 0, 8);
	}

	public static float decodeFloat(InputStream instream) throws IOException
	{
		return Float.intBitsToFloat(decodeInt(instream));
	}

	public static void encodeFloat(OutputStream outstream, float fv) throws IOException
	{
		encodeInt(outstream,Float.floatToIntBits(fv));
	}
	
	public static double decodeDouble(InputStream instream) throws IOException
	{
		return Double.longBitsToDouble(decodeLong(instream));
	}

	public static void encodeDouble(OutputStream outstream, double dv) throws IOException
	{
		encodeLong(outstream,Double.doubleToLongBits(dv));
	}

	public static String decodeString(InputStream instream) throws IOException
	{
		byte[] tmpData = decodeBytes(instream);
		if (tmpData != null && tmpData.length > 0)
			return new String(tmpData, ASSEMBLE_CHARSET);
		else
			return null;
	}

	public static void encodeString(OutputStream outstream, String text) throws IOException
	{
		byte[] tmpData = text.getBytes(ASSEMBLE_CHARSET);
		encodeBytes(outstream, tmpData);
	}

	public static byte[] decodeBytes(InputStream instream) throws IOException
	{
		int ch1 = instream.read();
		int ch2 = instream.read();
		if ((ch1 | ch2) < 0)
			throw new IOException();
		int dataLength = (ch1 << 8) + ch2;
		byte[] bytes = new byte[dataLength];
		instream.read(bytes);
		return bytes;
	}

	public static void encodeBytes(OutputStream outstream, byte[] bytes) throws IOException
	{
		int dataLength = bytes.length;
		outstream.write((dataLength >> 8) & 0xFF);
		outstream.write(dataLength & 0xFF);
		outstream.write(bytes, 0, dataLength);
	}
	
}
