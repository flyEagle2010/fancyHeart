package com.doteyplay.simplescheme.fieldvalue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.doteyplay.simplescheme.SchemeConstants;
import com.doteyplay.simplescheme.SimpleSchemeData;
import com.doteyplay.simplescheme.field.AbstractField;
import com.doteyplay.simplescheme.value.CompatibleInt;
import com.doteyplay.xmlsupport.SimpleXmlBuilder;

public class ReferenceFieldValue extends AbstractFieldValue
{
	private CompatibleInt value;

	public ReferenceFieldValue(SimpleSchemeData refdata, AbstractField reffield, int refobjid)
	{
		super(refdata, reffield);
		value = new CompatibleInt();
		value.setValue(String.valueOf(refobjid));
	}

	public String asString()
	{
		return value.asString();
	}

	@Override
	public void decode(InputStream instream)
	{
		try
		{
			value.decode(instream);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void encode(OutputStream outstream)
	{
		try
		{
			value.encode(outstream);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void addSubData(SimpleSchemeData adata)
	{
	}

	@Override
	public void setValue(String v)
	{
		value.setValue(v);
	}

	@Override
	public SimpleXmlBuilder exportXml(boolean expanddetail, SimpleXmlBuilder xmlbuffer) throws IOException
	{
		SimpleXmlBuilder tmpNode = xmlbuffer
				.addNode(SchemeConstants.FIELD_TYPE_FLAGS[SchemeConstants.FIELD_TYPE_REFERENCE]);
		tmpNode.addPrty(SchemeConstants.PROPERTY_FLAG_NAME, getField().getName()).addPrty(
				SchemeConstants.PROPERTY_FLAG_VALUE, this.asString()).addPrty(
				SchemeConstants.PROPERTY_FLAG_REF, getRefId());
		if (expanddetail)
		{
			tmpNode.addPrty(SchemeConstants.PROPERTY_FLAG_TITLE, this.getField().getTitle());
		}
		return tmpNode;
	}

	public String getExtProperty(String prtyname)
	{
		return null;
	}
}
