package com.doteyplay.utils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

public class IoBuffer
{
	private static final Logger logger = Logger.getLogger(IoBuffer.class
			.getName());

	private int index = 0;
	private byte[] buffer;
	public int length;

	private byte[] longTemp = new byte[8];
	private byte[] intTemp = new byte[4];
	private byte[] shortTemp = new byte[2];
	private byte[] StringTemp;

	public IoBuffer(int length)
	{
		buffer = new byte[length];
		index = 0;
	}

	public IoBuffer()
	{
		buffer = new byte[2048];
		index = 0;
	}

	public void setBuffer(byte[] recBuffer, int len)
	{
		length = len;
		buffer = new byte[len];
		System.arraycopy(recBuffer, 0, buffer, 0, len);
		index = 0;
	}

	public void setBuffer(byte[] recBuffer)
	{
		buffer = recBuffer;
		index = 0;
	}

	public void clearRecived()
	{
		index = 0;
		length = 0;
		buffer = null;
	}

	public void clearSend()
	{
		index = 0;
	}

	public int getIndex()
	{
		return index;
	}

	public void putDate(Date date)
	{
		if (date == null)
		{
			return;
		}
		this.putLong(date.getTime());
	}

	public void putLong(long i)
	{
		longTemp[3] = (byte) ((i >> 32) & 0xff);
		longTemp[2] = (byte) (((i >> 32) & 0xff00) >> 8);
		longTemp[1] = (byte) (((i >> 32) & 0xff0000) >> 16);
		longTemp[0] = (byte) (((i >> 32) & 0xff000000) >> 24);
		longTemp[7] = (byte) ((i) & 0xff);
		longTemp[6] = (byte) (((i) & 0xff00) >> 8);
		longTemp[5] = (byte) (((i) & 0xff0000) >> 16);
		longTemp[4] = (byte) (((i) & 0xff000000) >> 24);
		System.arraycopy(longTemp, 0, buffer, index, 8);
		index = index + 8;
	}

	public void putInt(int i)
	{
		intTemp[3] = (byte) (0xff & i);
		intTemp[2] = (byte) ((0xff00 & i) >> 8);
		intTemp[1] = (byte) ((0xff0000 & i) >> 16);
		intTemp[0] = (byte) ((0xff000000 & i) >> 24);
		System.arraycopy(intTemp, 0, buffer, index, 4);
		index = index + 4;
	}

	public void putShort(short i)
	{
		shortTemp[1] = (byte) (0xff & i);
		shortTemp[0] = (byte) ((0xff00 & i) >> 8);
		System.arraycopy(shortTemp, 0, buffer, index, 2);
		index = index + 2;
	}

	public void putByte(byte souce)
	{
		buffer[index] = souce;
		index++;
	}

	public void putBoolean(boolean bool)
	{
		if (bool)
			putByte((byte) 1);
		else
			putByte((byte) 0);
	}

	public void putString(String souce)
	{
		if (souce == null || souce.equals(""))
			putShort((short) 0);
		else
		{
			try
			{
				StringTemp = souce.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
			short leng = (short) StringTemp.length;
			putShort(leng);
			System.arraycopy(StringTemp, 0, buffer, index, leng);
			index = index + leng;
		}
	}

	public void putString(String souce, int len)
	{
		if (souce == null || souce.equals(""))
			putShort((short) 0);
		else
		{
			try
			{
				StringTemp = souce.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
			System.arraycopy(StringTemp, 0, buffer, index, StringTemp.length);
			for (int i = StringTemp.length; i < len; i++)
			{
				buffer[index + i] = 0;
			}
			index = index + len;
		}
	}

	public void putBlob(byte[] bytes)
	{
		short blength = (short) bytes.length;
		this.putShort(blength);
		System.arraycopy(bytes, 0, this.buffer, index, blength);
		index += blength;
	}

	public byte[] toBuffer()
	{
		byte[] temp = new byte[index];// +1
		System.arraycopy(buffer, 0, temp, 0, index);
		return temp;
	}

	// ////////////////////////////////////////////////////////////////////////////

	private byte returnByte;
	private int returnInt;
	private String returnString;
	private short returnShort;
	private long returnLong;
	private byte[] tempByte = new byte[270];

	public byte getByte()
	{
		returnByte = buffer[index];
		index++;
		return returnByte;
	}

	public int getUnsignedByte()
	{
		returnInt = buffer[index] & 0xff;
		index++;
		return returnInt;
	}

	public boolean getBoolean()
	{
		return getByte() == 0 ? false : true;
	}

	public final int getInt()
	{
		returnInt = (0xff & buffer[index]) << 24
				| (0xff & buffer[index + 1]) << 16
				| (0xff & buffer[index + 2]) << 8 | (0xff & buffer[index + 3]);
		index += 4;
		return returnInt;
	}

	public long getLong()
	{
		returnLong = ((((long) buffer[index + 0] & 0xff) << 56)
				| (((long) buffer[index + 1] & 0xff) << 48)
				| (((long) buffer[index + 2] & 0xff) << 40)
				| (((long) buffer[index + 3] & 0xff) << 32)
				| (((long) buffer[index + 4] & 0xff) << 24)
				| (((long) buffer[index + 5] & 0xff) << 16)
				| (((long) buffer[index + 6] & 0xff) << 8) | (((long) buffer[index + 7] & 0xff) << 0));
		index += 8;
		return returnLong;
	}

	public short getShort()
	{
		try
		{
			returnShort = (short) ((0xff & buffer[index]) << 8 | (0xff & buffer[index + 1]));
			index += 2;
		} catch (ArrayIndexOutOfBoundsException e)
		{
			logger.error("index = " + index + "; buffer length = "
					+ buffer.length + ".");
			throw e;
		}
		return returnShort;
	}

	public int getUnsignedShort()
	{
		returnInt = (int) ((0xff & buffer[index]) << 8 | (0xff & buffer[index + 1]));
		index += 2;
		return returnInt;
	}

	public String getString()
	{
		short leng = getShort();
		if (leng > 0)
		{
			try
			{
				returnString = new String(buffer, index, leng, "UTF-8");
			} catch (Exception e)
			{

			}
			index += leng;
			return returnString;
		} else
		{
			return "";
		}
	}

	public String getString(int length)
	{
		int leng = 0;
		for (int i = 0; i < length; i++)
		{
			if (buffer[index + i] != 0)
			{
				tempByte[leng] = buffer[index + i];
				leng++;
			}
		}

		try
		{
			returnString = new String(tempByte, 0, leng, "UTF-8");
		} catch (Exception e)
		{
		}

		index += length;
		return returnString;
	}

	public Date getDate()
	{
		long time = this.getLong();
		Timestamp date = null;
		try
		{
			date = new Timestamp(time);
		} catch (Exception e)
		{
			logger.error("�ַ�תʱ�����", e);
		}
		return date;
	}

	// ����ʽ��ȡ���µ��ֽ�
	public DataInputStream getDataInputStream()
	{
		ByteArrayInputStream in = new ByteArrayInputStream(buffer, index,
				length - index);
		index = length;
		return new DataInputStream(in);
	}

	// ���鷽ʽ��ȡ
	public byte[] getByteBuffer(short leng)
	{
		byte[] returnByte = new byte[leng];
		System.arraycopy(buffer, index, returnByte, 0, leng);
		index = index + leng;
		return returnByte;
	}

	// ���鷽ʽ��ȡ
	public byte[] getBlob()
	{
		short leng = this.getShort();
		return this.getByteBuffer(leng);
	}

	// ���鷽ʽ��ȡ
	public byte[] getByteBuffer(byte[] data, int offset, short leng)
	{
		System.arraycopy(buffer, index, data, offset, leng);
		index = index + leng;
		return data;
	}
}
