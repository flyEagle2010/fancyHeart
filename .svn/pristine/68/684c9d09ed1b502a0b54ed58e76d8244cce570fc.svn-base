package com.doteyplay.simplescheme.field;

import com.doteyplay.simplescheme.SchemeConstants;
import com.doteyplay.simplescheme.SimpleScheme;
import com.doteyplay.simplescheme.SimpleSchemeData;
import com.doteyplay.simplescheme.fieldvalue.AbstractFieldValue;
import com.doteyplay.simplescheme.fieldvalue.DataFieldValue;
import com.doteyplay.simplescheme.value.CompatibleValueFactory;
import com.doteyplay.simplescheme.value.ICompatibleValue;
import com.doteyplay.xmlsupport.ISimpleParamters;
import com.doteyplay.xmlsupport.SimpleXmlBuilder;

public class DataField extends AbstractField
{
	private ICompatibleValue defaultValue;

	public DataField(String name, String title, byte datatype, SimpleScheme scheme)
	{
		super(name, title, datatype, scheme);
	}

	public byte fieldType()
	{
		return SchemeConstants.FIELD_TYPE_DATA;
	}

	public ICompatibleValue defaultValue()
	{
		return defaultValue;
	}

	public void initExtProperty(ISimpleParamters element)
	{
		defaultValue = CompatibleValueFactory.createValue(this.dataType);
		defaultValue.setValue(element.getValue("DEFAULT"));
	}

	public SimpleXmlBuilder exportXml(SimpleXmlBuilder xmlbuffer)
	{
		SimpleXmlBuilder tmpNode = xmlbuffer.addNode(SchemeConstants.ELEMENT_FLAG_FIELD).addPrty(
				SchemeConstants.PROPERTY_FLAG_NAME, this.name).addPrty(SchemeConstants.PROPERTY_FLAG_TITLE,
				this.title).addPrty(SchemeConstants.PROPERTY_FLAG_DATATYPE,
				SchemeConstants.DATATYPE_FLAGS[this.dataType]).addPrty(SchemeConstants.PROPERTY_FLAG_INDEX,
				this.getLocalIndex());
		if (defaultValue.asByte() != 0)
			tmpNode.addPrty(SchemeConstants.PROPERTY_FLAG_DEFAULT, defaultValue.asString());
		if (this.data != null)
			tmpNode.addPrty(SchemeConstants.PROPERTY_FLAG_DATA, this.data);
		if (this.syncFlag != null)
			tmpNode.addPrty(SchemeConstants.PROPERTY_FLAG_SYNC, this.syncFlag);
		return tmpNode;
	}

	public void init()
	{
	}

	public AbstractFieldValue creaetFieldValue(SimpleSchemeData refdata)
	{
		return new DataFieldValue(refdata, this, defaultValue.clone());
	}

}
