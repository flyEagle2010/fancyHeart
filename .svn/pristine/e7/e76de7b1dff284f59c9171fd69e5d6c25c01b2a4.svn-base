package com.doteyplay.simplescheme.value;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ICompatibleValue
{
	public final static byte DATATYPE_NONE = 0;
	public final static byte DATATYPE_STRING = 1;
	public final static byte DATATYPE_BYTE = 2;
	public final static byte DATATYPE_SHORT = 3;
	public final static byte DATATYPE_INT = 4;
	public final static byte DATATYPE_LONG = 5;
	public final static byte DATATYPE_FLOAT = 6;
	public final static byte DATATYPE_LIST = 7;
	public final static byte DATATYPE_BYTES = 8;
	public final static byte DATATYPE_IMAGE = 9;
	public final static byte DATATYPE_COUNT = 8;

	public byte dataType();

	public void setValue(String value);

	public void setBytesValue(byte[] data);

	public String asString();

	public long asLong();

	public int asInt();

	public short asShort();

	public float asFloat();

	public byte asByte();

	public byte[] asBytes();

	public void encode(OutputStream outstream) throws IOException;

	public void decode(InputStream instream) throws IOException;

	public ICompatibleValue clone();
}
