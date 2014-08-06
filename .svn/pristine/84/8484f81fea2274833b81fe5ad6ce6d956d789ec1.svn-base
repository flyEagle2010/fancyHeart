package com.doteyplay.simplescheme.field;

import com.doteyplay.simplescheme.SchemeConstants;
import com.doteyplay.simplescheme.SimpleScheme;
import com.doteyplay.simplescheme.SimpleSchemeData;
import com.doteyplay.simplescheme.fieldvalue.AbstractFieldValue;
import com.doteyplay.simplescheme.value.CompatibleValueFactory;
import com.doteyplay.simplescheme.value.ICompatibleValue;
import com.doteyplay.xmlsupport.ISimpleParamters;
import com.doteyplay.xmlsupport.SimpleXmlBuilder;

public class StaticField extends AbstractField
{
	private ICompatibleValue value;

	public StaticField(String name, String title, byte datatype, SimpleScheme scheme)
	{
		super(name, title, datatype, scheme);
		value = CompatibleValueFactory.createValue(this.dataType);
	}

	public byte fieldType()
	{
		return SchemeConstants.FIELD_TYPE_STATIC;
	}

	public ICompatibleValue define()
	{
		return value;
	}

	public void initExtProperty(ISimpleParamters element)
	{
		value.setValue(element.getValue(SchemeConstants.PROPERTY_FLAG_VALUE));
	}

	public SimpleXmlBuilder exportXml(SimpleXmlBuilder xmlbuffer)
	{
		SimpleXmlBuilder tmpNode = xmlbuffer.addNode(SchemeConstants.ELEMENT_FLAG_STATIC).addPrty(
				SchemeConstants.PROPERTY_FLAG_NAME, this.name).addPrty(SchemeConstants.PROPERTY_FLAG_TITLE,
				this.title).addPrty(SchemeConstants.PROPERTY_FLAG_DATATYPE,
				SchemeConstants.DATATYPE_FLAGS[this.dataType]).addPrty(SchemeConstants.PROPERTY_FLAG_VALUE,
				value.asString()).addPrty(SchemeConstants.PROPERTY_FLAG_INDEX, this.getLocalIndex());
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
