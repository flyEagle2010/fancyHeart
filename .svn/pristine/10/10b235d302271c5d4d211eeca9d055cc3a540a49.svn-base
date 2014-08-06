package com.doteyplay.simplescheme.value;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.doteyplay.utils.StrUtils;

public class CompatibleByte implements ICompatibleValue
{
	private byte value;

	public byte dataType()
	{
		return DATATYPE_BYTE;
	}

	public void setValue(String value)
	{
		this.value = StrUtils.parsebyte(value, (byte) 0);
	}

	public String asString()
	{
		return String.valueOf(value);
	}

	public void setBytesValue(byte[] data)
	{
	}

	public long asLong()
	{
		return value;
	}

	public int asInt()
	{
		return value;
	}

	public short asShort()
	{
		return value;
	}

	public float asFloat()
	{
		return value;
	}

	public byte asByte()
	{
		return value;
	}

	public byte[] asBytes()
	{
		return new byte[] { value };
	}

	public ICompatibleValue clone()
	{
		CompatibleByte r = new CompatibleByte();
		r.value = this.value;
		return r;
	}

	public void decode(InputStream instream) throws IOException
	{
		value = (byte) instream.read();
	}

	public void encode(OutputStream outstream) throws IOException
	{
		outstream.write(value);
	}

}
