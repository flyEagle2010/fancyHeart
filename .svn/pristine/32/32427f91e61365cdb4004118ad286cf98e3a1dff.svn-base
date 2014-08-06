package com.doteyplay.simplescheme.fieldvalue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.doteyplay.simplescheme.IFastRef;
import com.doteyplay.simplescheme.SimpleSchemeData;
import com.doteyplay.simplescheme.field.AbstractField;
import com.doteyplay.xmlsupport.SimpleXmlBuilder;

public abstract class AbstractFieldValue implements IFastRef
{
	private int refId;
	private AbstractField refField;
	private SimpleSchemeData refData;

	public AbstractFieldValue(SimpleSchemeData refdata, AbstractField reffield)
	{
		this.refData = refdata;
		this.refId = refData.newRefId();
		this.refField = reffield;
	}

	public int getRefId()
	{
		return refId;
	}

	public byte getRefType()
	{
		return IFastRef.REF_TYPE_FIELD;
	}

	public AbstractField getField()
	{
		return refField;
	}
	
	public String getFieldName()
	{
		return (refField!=null)?refField.getName():null;
	}

	public SimpleSchemeData getParentData()
	{
		return refData;
	}

	public abstract void setValue(String v);

	public abstract void addSubData(SimpleSchemeData adata);

	public abstract String asString();

	public abstract SimpleXmlBuilder exportXml(boolean expanddetail,SimpleXmlBuilder xmlbuffer) throws IOException;

	public abstract void decode(InputStream instream);

	public abstract void encode(OutputStream outstream);

	public abstract String getExtProperty(String prtyname);
}
