package com.doteyplay.simplescheme.value;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import com.doteyplay.utils.AssembleUtils;

public class CompatibleString implements ICompatibleValue
{
	private String value;

	public byte dataType()
	{
		return DATATYPE_STRING;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public void setBytesValue(byte[] data)
	{
		if (data != null)
		{
			try
			{
				this.value = new String(data, "utf-8");
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
	}

	public String asString()
	{
		return value;
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
		try
		{
			return (value != null) ? value.getBytes("UTF-8") : null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public ICompatibleValue clone()
	{
		CompatibleString r = new CompatibleString();
		r.value = this.value;
		return r;
	}

	public void decode(InputStream instream) throws IOException
	{
		value = AssembleUtils.decodeString(instream);
	}

	public void encode(OutputStream outstream) throws IOException
	{
		AssembleUtils.encodeString(outstream, value);
	}

}
