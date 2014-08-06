package com.doteyplay.simplescheme.value;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

import com.doteyplay.utils.AssembleUtils;
import com.doteyplay.utils.StrUtils;

public class CompatibleFloat implements ICompatibleValue
{
	private final static DecimalFormat sdf = new DecimalFormat("#0.0000");

	private float value;

	public byte dataType()
	{
		return DATATYPE_FLOAT;
	}

	public void setValue(String value)
	{
		this.value = StrUtils.parsefloat(value, 0.0f);
	}

	public void setBytesValue(byte[] data)
	{
	}

	public String asString()
	{
		return sdf.format(value);
	}

	public long asLong()
	{
		return (long) value;
	}

	public int asInt()
	{
		return (int) value;
	}

	public short asShort()
	{
		return (short) value;
	}

	public float asFloat()
	{
		return value;
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
		CompatibleFloat r = new CompatibleFloat();
		r.value = this.value;
		return r;
	}

	public void decode(InputStream instream) throws IOException
	{
		value = AssembleUtils.decodeFloat(instream);
	}

	public void encode(OutputStream outstream) throws IOException
	{
		AssembleUtils.encodeFloat(outstream, value);
	}

}
