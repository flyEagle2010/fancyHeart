package com.doteyplay.simplescheme.field;

import com.doteyplay.simplescheme.SchemeConstants;
import com.doteyplay.simplescheme.SimpleScheme;
import com.doteyplay.xmlsupport.ISimpleParamters;

public class SchemeFieldFactory
{
	public static AbstractField createSchemeField(ISimpleParamters element, SimpleScheme scheme)
	{
		if (element == null || scheme == null)
			return null;

		byte tmpFieldType = getFieldType(element.getDataName());

		String tmpName = element.getValue(SchemeConstants.PROPERTY_FLAG_NAME);
		String tmpTitle = element.getValue(SchemeConstants.PROPERTY_FLAG_TITLE);
		String tmpDataTypeFlag = element.getValue(SchemeConstants.PROPERTY_FLAG_DATATYPE);
		byte tmpDataType = getDataType(tmpDataTypeFlag);

		AbstractField r = null;
		switch (tmpFieldType)
		{
			case SchemeConstants.FIELD_TYPE_DATA:
				if (tmpDataType == SchemeConstants.DATATYPE_OBJECT)
					r = new ObjectField(tmpName, tmpTitle, tmpDataTypeFlag.substring(1), scheme);
				else
					r = new DataField(tmpName, tmpTitle, tmpDataType, scheme);
				break;
			case SchemeConstants.FIELD_TYPE_CHOICE:
				r = new ChoiceField(tmpName, tmpTitle, tmpDataType, scheme);
				break;
			case SchemeConstants.FIELD_TYPE_REFERENCE:
				r = new ReferenceField(tmpName, tmpTitle, tmpDataType, scheme);
				break;
			case SchemeConstants.FIELD_TYPE_LIST:
				if (tmpDataType == SchemeConstants.DATATYPE_OBJECT)
					r = new ListField(tmpName, tmpTitle, tmpDataTypeFlag.substring(1), scheme);
				else
					r = new DataField(tmpName, tmpTitle, tmpDataType, scheme);
				break;
			case SchemeConstants.FIELD_TYPE_ENUM:
				r = new EnumField(tmpName, tmpTitle, tmpDataType, scheme);
				break;
			case SchemeConstants.FIELD_TYPE_STATIC:
				r = new StaticField(tmpName, tmpTitle, tmpDataType, scheme);
				break;
		}

		if (r != null)
		{
			r.setSyncFlag(element.getValue(SchemeConstants.PROPERTY_FLAG_SYNC));
			r.setData(element.getValue(SchemeConstants.PROPERTY_FLAG_DATA));
			r.initExtProperty(element);
		}

		return r;
	}

	// *****************************************************
	public static byte getFieldType(String fieldtypename)
	{
		for (byte i = 0; i < SchemeConstants.FIELD_TYPE_FLAGS.length; i++)
		{
			if (SchemeConstants.FIELD_TYPE_FLAGS[i].equalsIgnoreCase(fieldtypename))
				return i;
		}
		return 0;
	}

	public static byte getDataType(String datatypename)
	{
		if (datatypename != null && datatypename.startsWith("#"))
			return SchemeConstants.DATATYPE_OBJECT;

		for (byte i = 0; i < SchemeConstants.DATATYPE_FLAGS.length; i++)
		{
			if (SchemeConstants.DATATYPE_FLAGS[i].equalsIgnoreCase(datatypename))
				return i;
		}
		return 0;
	}
}
