package com.doteyplay.simplescheme.field;

import com.doteyplay.simplescheme.SchemeConstants;
import com.doteyplay.simplescheme.SimpleScheme;
import com.doteyplay.simplescheme.SimpleSchemeData;
import com.doteyplay.simplescheme.fieldvalue.AbstractFieldValue;
import com.doteyplay.simplescheme.fieldvalue.ChoiceFieldValue;
import com.doteyplay.simplescheme.value.CompatibleByte;
import com.doteyplay.simplescheme.value.ICompatibleValue;
import com.doteyplay.xmlsupport.ISimpleParamters;
import com.doteyplay.xmlsupport.SimpleXmlBuilder;

public class ChoiceField extends AbstractField
{
	private CompatibleByte defaultValue;

	public ChoiceField(String name, String title, byte datatype, SimpleScheme scheme)
	{
		super(name, title, ICompatibleValue.DATATYPE_BYTE, scheme);
		defaultValue = new CompatibleByte();
	}

	public byte fieldType()
	{
		return SchemeConstants.FIELD_TYPE_CHOICE;
	}

	public void initExtProperty(ISimpleParamters element)
	{
		defaultValue.setValue(element.getValue(SchemeConstants.PROPERTY_FLAG_DEFAULT));
	}

	public SimpleXmlBuilder exportXml(SimpleXmlBuilder xmlbuffer)
	{
		SimpleXmlBuilder tmpNode = xmlbuffer.addNode(SchemeConstants.ELEMENT_FLAG_CHOICE).addPrty(
				SchemeConstants.PROPERTY_FLAG_NAME, this.name).addPrty(SchemeConstants.PROPERTY_FLAG_TITLE,
				this.title).addPrty(SchemeConstants.PROPERTY_FLAG_DATATYPE, this.dataType).addPrty(
				SchemeConstants.PROPERTY_FLAG_INDEX, this.getLocalIndex());
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
		if (this.data != null && this.data.startsWith("@"))
		{
			AbstractField tmpRefField = this.scheme.getField(this.data.substring(1));
			this.data = tmpRefField.getData();
		}
	}

	public AbstractFieldValue creaetFieldValue(SimpleSchemeData refdata)
	{
		return new ChoiceFieldValue(refdata, this, defaultValue.asByte());
	}

}
