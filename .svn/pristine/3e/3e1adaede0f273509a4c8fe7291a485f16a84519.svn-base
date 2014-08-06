package com.doteyplay.simplescheme.field;

import com.doteyplay.simplescheme.SimpleScheme;
import com.doteyplay.simplescheme.SimpleSchemeData;
import com.doteyplay.simplescheme.fieldvalue.AbstractFieldValue;
import com.doteyplay.xmlsupport.ISimpleParamters;
import com.doteyplay.xmlsupport.SimpleXmlBuilder;

public abstract class AbstractField
{
	protected SimpleScheme scheme;
	protected String name;
	protected String title;
	protected byte dataType;
	protected String syncFlag;
	protected String data;
	protected boolean valid;
	private byte localIndex;

	public AbstractField(String name, String title, byte datatype, SimpleScheme scheme)
	{
		this.scheme = scheme;
		this.name = name;
		this.title = title;
		this.dataType = datatype;
		this.syncFlag = null;
		this.valid = true;
	}

	public String getName()
	{
		return name;
	}

	public String getSyncFlag()
	{
		return syncFlag;
	}

	public void setSyncFlag(String syncflag)
	{
		if (syncflag != null && syncflag.startsWith("@"))
			syncFlag = syncflag.substring(1);
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public byte getLocalIndex()
	{
		return localIndex;
	}

	public void setLocalIndex(byte idx)
	{
		this.localIndex = idx;
	}

	public String getTitle()
	{
		return title;
	}

	public byte getDataType()
	{
		return dataType;
	}

	public boolean isValid()
	{
		return valid;
	}

	public abstract byte fieldType();

	public abstract void initExtProperty(ISimpleParamters element);

	public abstract void init();

	public abstract AbstractFieldValue creaetFieldValue(SimpleSchemeData refdata);
	
	public abstract SimpleXmlBuilder exportXml(SimpleXmlBuilder xmlbuffer);

}
