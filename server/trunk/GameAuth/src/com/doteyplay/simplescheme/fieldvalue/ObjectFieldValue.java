package com.doteyplay.simplescheme.fieldvalue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.doteyplay.simplescheme.SchemeConstants;
import com.doteyplay.simplescheme.SchemeDataLoader;
import com.doteyplay.simplescheme.SimpleScheme;
import com.doteyplay.simplescheme.SimpleSchemeData;
import com.doteyplay.simplescheme.field.AbstractField;
import com.doteyplay.simplescheme.field.ObjectField;
import com.doteyplay.xmlsupport.SimpleXmlBuilder;

public class ObjectFieldValue extends AbstractFieldValue
{
	private SimpleSchemeData value;
	private ObjectField objField;

	public ObjectFieldValue(SimpleSchemeData refdata, AbstractField reffield, SimpleScheme datascheme)
	{
		super(refdata, reffield);
		objField = (ObjectField) reffield;
		value = datascheme.newInstnce("0", "", datascheme.getVersion());
	}

	public String asString()
	{
		return value.toString();
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
		try
		{
			value = SchemeDataLoader.loadSchemeData(value, v);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public SimpleXmlBuilder exportXml(boolean expanddetail, SimpleXmlBuilder xmlbuffer) throws IOException
	{
		SimpleXmlBuilder tmpNode = xmlbuffer
				.addNode(SchemeConstants.FIELD_TYPE_FLAGS[SchemeConstants.FIELD_TYPE_DATA]);
		tmpNode.addPrty(SchemeConstants.PROPERTY_FLAG_NAME, getField().getName()).addPrty(
				SchemeConstants.PROPERTY_FLAG_DATATYPE, "@" + objField.getDataScheme().getName()).addPrty(
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
		if (SchemeConstants.FLD_EXT_PRTY_DATATYPEFLAG.equals(prtyname))
		{
			return SchemeConstants.DATATYPE_FLAGS[this.getField().getDataType()];
		}
		else
			return null;
	}

}
