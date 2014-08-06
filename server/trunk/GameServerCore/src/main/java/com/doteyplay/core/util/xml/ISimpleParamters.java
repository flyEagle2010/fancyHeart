package com.doteyplay.core.util.xml;

//参数读取接口
public interface ISimpleParamters
{
	public void release();

	public int getLength();

	public String getDataName();

	public String getParamterName(int index);

	public String getValue(int index);

	public int getIndex(String paramname);

	public String getValue(String paramname);
}
