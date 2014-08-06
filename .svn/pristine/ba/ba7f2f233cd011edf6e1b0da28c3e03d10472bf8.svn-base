package com.doteyplay.simplescheme.field;

import com.doteyplay.simplescheme.SchemeConstants;
import com.doteyplay.simplescheme.SimpleScheme;
import com.doteyplay.simplescheme.SimpleSchemeData;
import com.doteyplay.simplescheme.fieldvalue.AbstractFieldValue;
import com.doteyplay.xmlsupport.ISimpleParamters;
import com.doteyplay.xmlsupport.SimpleXmlBuilder;

public class NoneField extends AbstractField
{

	public NoneField(String name, String title, byte datatype, SimpleScheme scheme)
	{
		super(name, title, datatype, scheme);
	}

	public byte fieldType()
	{
		return SchemeConstants.FIELD_TYPE_NONE;
	}

	public void initExtProperty(ISimpleParamters element)
	{

	}

	public SimpleXmlBuilder exportXml(SimpleXmlBuilder xmlbuffer)
	{
		SimpleXmlBuilder tmpNode = xmlbuffer.addNode(SchemeConstants.ELEMENT_FLAG_NONE).addPrty(
				SchemeConstants.PROPERTY_FLAG_INDEX, this.getLocalIndex());
		return tmpNode;
	}

	public void init()
	{

	}

	public AbstractFieldValue creaetFieldValue(SimpleSchemeData refdata)
	{
		return null;
	}

	public String getExtProperty(String prtyname)
	{
		return null;
	}

}
