package com.doteyplay.simplescheme.value;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.doteyplay.utils.AssembleUtils;
import com.doteyplay.utils.StrUtils;

public class CompatibleShort implements ICompatibleValue
{
	private short value;

	public byte dataType()
	{
		return DATATYPE_SHORT;
	}

	public void setValue(String value)
	{
		this.value = StrUtils.parseshort(value, (short) 0);
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
		return value;
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
		CompatibleShort r = new CompatibleShort();
		r.value = this.value;
		return r;
	}

	public void decode(InputStream instream) throws IOException
	{
		value = AssembleUtils.decodeShort(instream);
	}

	public void encode(OutputStream outstream) throws IOException
	{
		AssembleUtils.encodeShort(outstream, value);
	}

}
