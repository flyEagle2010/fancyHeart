package com.doteyplay.simplescheme.fieldvalue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.doteyplay.simplescheme.SchemeConstants;
import com.doteyplay.simplescheme.SimpleSchemeData;
import com.doteyplay.simplescheme.field.AbstractField;
import com.doteyplay.simplescheme.value.CompatibleByte;
import com.doteyplay.xmlsupport.SimpleXmlBuilder;

public class ChoiceFieldValue extends AbstractFieldValue
{
	private CompatibleByte value;

	public ChoiceFieldValue(SimpleSchemeData refdata, AbstractField reffield, byte dfv)
	{
		super(refdata, reffield);
		value = new CompatibleByte();
		value.setValue(String.valueOf(dfv));
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
				.addNode(SchemeConstants.FIELD_TYPE_FLAGS[SchemeConstants.FIELD_TYPE_CHOICE]);
		tmpNode.addPrty(SchemeConstants.PROPERTY_FLAG_NAME, getField().getName()).addPrty(
				SchemeConstants.PROPERTY_FLAG_DATATYPE,
				SchemeConstants.DATATYPE_FLAGS[getField().getDataType()]).addPrty(
				SchemeConstants.PROPERTY_FLAG_VALUE, this.asString()).addPrty(
				SchemeConstants.PROPERTY_FLAG_REF, getRefId());
		if (expanddetail)
		{
			tmpNode.addPrty(SchemeConstants.PROPERTY_FLAG_TITLE, this.getField().getTitle()).addPrty(
					SchemeConstants.FLD_EXT_PRTY_CHOICETEXT,
					this.getExtProperty(SchemeConstants.FLD_EXT_PRTY_CHOICETEXT));
		}
		return tmpNode;
	}

	public String getExtProperty(String prtyname)
	{
		if (SchemeConstants.FLD_EXT_PRTY_CHOICETEXT.equals(prtyname))
			return this.getField().getData();
		else if (SchemeConstants.FLD_EXT_PRTY_DATATYPEFLAG.equals(prtyname))
			return SchemeConstants.DATATYPE_FLAG_BYTE;
		else
			return null;
	}
}
