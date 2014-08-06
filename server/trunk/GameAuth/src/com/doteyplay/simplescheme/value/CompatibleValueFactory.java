package com.doteyplay.simplescheme.value;

public class CompatibleValueFactory
{
	public final static ICompatibleValue createValue(byte datatype)
	{
		ICompatibleValue r = null;
		switch (datatype)
		{
			case ICompatibleValue.DATATYPE_STRING:
				r = new CompatibleString();
				break;
			case ICompatibleValue.DATATYPE_BYTE:
				r = new CompatibleByte();
				break;
			case ICompatibleValue.DATATYPE_SHORT:
				r = new CompatibleShort();
				break;
			case ICompatibleValue.DATATYPE_INT:
				r = new CompatibleInt();
				break;
			case ICompatibleValue.DATATYPE_LONG:
				r = new CompatibleLong();
				break;
			case ICompatibleValue.DATATYPE_FLOAT:
				r = new CompatibleFloat();
				break;
			case ICompatibleValue.DATATYPE_BYTES:
				r = new CompatibleBytes();
				break;
			case ICompatibleValue.DATATYPE_IMAGE:
				r = new CompatibleImage();
				break;
			default:
				r = new CompatibleNone();
				break;
		}
		return r;
	}
}
