package com.doteyplay.simplescheme.value;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.doteyplay.utils.AssembleUtils;
import com.doteyplay.utils.StrUtils;

public class CompatibleInt implements ICompatibleValue
{
	private int value;

	public byte dataType()
	{
		return DATATYPE_INT;
	}

	public void setValue(String value)
	{
		this.value = StrUtils.parseint(value, 0);
	}

	public void setBytesValue(byte[] data)
	{
	}

	public String asString()
	{
		return String.valueOf(value);
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
		return (short) value;
	}

	public float asFloat()
	{
		return (float) value;
	}

	public byte asByte()
	{
		return (byte) value;
	}

	public byte[] asBytes()
	{
		return null;
	}

	public ICompatibleValue clone()
	{
		CompatibleInt r = new CompatibleInt();
		r.value = this.value;
		return r;
	}

	public void decode(InputStream instream) throws IOException
	{
		value = AssembleUtils.decodeInt(instream);
	}

	public void encode(OutputStream outstream) throws IOException
	{
		AssembleUtils.encodeInt(outstream, value);
	}
}
