package com.doteyplay.simplescheme.value;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.doteyplay.utils.AssembleUtils;

public class CompatibleBytes implements ICompatibleValue
{
	private byte[] value;

	public byte dataType()
	{
		return DATATYPE_BYTES;
	}

	public void setValue(String value)
	{
	}

	public void setBytesValue(byte[] data)
	{
		value = data;
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
		return value;
	}

	public ICompatibleValue clone()
	{
		CompatibleBytes r = new CompatibleBytes();
		if (value == null)
			r.value = null;
		else
		{
			r.value = new byte[this.value.length];
			System.arraycopy(this.value, 0, r.value, 0, this.value.length);
		}
		return r;
	}

	public void decode(InputStream instream) throws IOException
	{
		value = AssembleUtils.decodeBytes(instream);
	}

	public void encode(OutputStream outstream) throws IOException
	{
		AssembleUtils.encodeBytes(outstream, value);
	}
}
