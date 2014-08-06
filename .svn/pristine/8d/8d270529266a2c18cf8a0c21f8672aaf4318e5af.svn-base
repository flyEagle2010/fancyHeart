package com.doteyplay.simplescheme.fieldvalue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import com.doteyplay.simplescheme.SchemeConstants;
import com.doteyplay.simplescheme.SimpleScheme;
import com.doteyplay.simplescheme.SimpleSchemeData;
import com.doteyplay.simplescheme.field.AbstractField;
import com.doteyplay.utils.AssembleUtils;
import com.doteyplay.xmlsupport.SimpleXmlBuilder;

public class ListFieldValue extends AbstractFieldValue
{
	private SimpleScheme refScheme;
	private Vector<SimpleSchemeData> listDatas;

	public ListFieldValue(SimpleSchemeData refdata, AbstractField reffield, SimpleScheme itemscheme)
	{
		super(refdata, reffield);
		refScheme = itemscheme;
		listDatas = null;
	}

	@Override
	public void decode(InputStream instream)
	{
		try
		{
			int listCount = AssembleUtils.decodeUnsignedByte(instream);
			if (listCount > 0)
			{
				SimpleSchemeData tmpData;
				for (int j = 0; j < listCount; j++)
				{
					tmpData = refScheme.newInstnce("", "", refScheme.getVersion());
					tmpData.decode(instream);
					listDatas.add(tmpData);
				}
			}
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
			if (listDatas != null)
			{
				SimpleSchemeData tmpData;
				AssembleUtils.encodeUnsignedByte(outstream, listDatas.size());
				for (int j = 0; j < listDatas.size(); j++)
				{
					tmpData = listDatas.get(j);
					tmpData.encode(outstream);
				}
			}
			else
				AssembleUtils.encodeUnsignedByte(outstream, 0);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void setValue(String v)
	{
	}

	@Override
	public void addSubData(SimpleSchemeData adata)
	{
		if (listDatas == null)
		{
			listDatas = new Vector<SimpleSchemeData>();
		}
		listDatas.add(adata);
	}

	@Override
	public String asString()
	{
		return String.valueOf((listDatas != null) ? listDatas.size() : 0);
	}

	@Override
	public SimpleXmlBuilder exportXml(boolean expanddetail, SimpleXmlBuilder xmlbuffer) throws IOException
	{
		SimpleXmlBuilder tmpListNode = xmlbuffer
				.addNode(SchemeConstants.FIELD_TYPE_FLAGS[SchemeConstants.FIELD_TYPE_LIST]);
		tmpListNode.addPrty(SchemeConstants.PROPERTY_FLAG_NAME, getField().getName()).addPrty(
				SchemeConstants.PROPERTY_FLAG_DATATYPE, "@" + refScheme.getName()).addPrty(
				SchemeConstants.PROPERTY_FLAG_VALUE, this.asString()).addPrty(
				SchemeConstants.PROPERTY_FLAG_REF, getRefId());
		if (expanddetail)
		{
			tmpListNode.addPrty(SchemeConstants.PROPERTY_FLAG_TITLE, getField().getTitle());
		}

		if (this.listDatas != null && listDatas.size() > 0)
		{
			for (SimpleSchemeData tmpData : listDatas)
			{
				tmpData.exportToXmlBuffer(expanddetail, tmpListNode);
			}
		}
		return tmpListNode;
	}

	public String getExtProperty(String prtyname)
	{
		return null;
	}
}
