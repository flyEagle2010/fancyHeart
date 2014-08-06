package com.doteyplay.simplescheme.value;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CompatibleNone implements ICompatibleValue
{

	public byte dataType()
	{
		return DATATYPE_NONE;
	}

	public void setValue(String value)
	{
	}

	public void setBytesValue(byte[] data)
	{
	}

	public String asString()
	{
		return null;
	}

	public long asLong()
	{
		return 0;
	}

	public int asInt()
	{
		return 0;
	}

	public short asShort()
	{
		return 0;
	}

	public float asFloat()
	{
		return 0.0f;
	}

	public byte asByte()
	{
		return 0;
	}

	public byte[] asBytes()
	{
		return null;
	}

	public ICompatibleValue clone()
	{
		return new CompatibleNone();
	}

	public void decode(InputStream instream) throws IOException
	{

	}

	public void encode(OutputStream outstream) throws IOException
	{

	}
}
