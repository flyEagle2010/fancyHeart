package com.doteyplay.core.util;

public interface IResponseParamters
{
	public void setEnumValue(Enum<?> key, String value);

	public KvMap addKeyValue(String key, String value);

	public KvMap addEnumValue(Enum<?> key, String value);
	
    public KvMap addValue(String key, byte value);
	
	public KvMap addEnumValue(Enum<?> key, byte value);

	public KvMap addValue(String key, short value);
	
	public KvMap addEnumValue(Enum<?> key, short value);
	
	public KvMap addValue(String key, int value);
	
	public KvMap addEnumValue(Enum<?> key, int value);

	public KvMap addValue(String key, long value);
	
	public KvMap addEnumValue(Enum<?> key, long value);
	
	public KvMap addValue(String key, float value);
	
	public KvMap addEnumValue(Enum<?> key, float value);

	public KvMap addValue(String key, double value);
	
	public KvMap addEnumValue(Enum<?> key, double value);
}
