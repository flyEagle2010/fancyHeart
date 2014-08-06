package com.doteyplay.simplescheme;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import com.doteyplay.simplescheme.field.AbstractField;
import com.doteyplay.simplescheme.fieldvalue.AbstractFieldValue;
import com.doteyplay.xmlsupport.SimpleXmlBuilder;

public class SimpleSchemeData implements IFastRef, IDataSync
{
	public class FastFieldRef implements IFastRefManager
	{
		private int currentFieldRefId;
		private HashMap<Integer, IFastRef> refField;

		public FastFieldRef()
		{
			currentFieldRefId = 1;
			refField = new HashMap<Integer, IFastRef>();
		}

		public synchronized int newRefId()
		{
			return currentFieldRefId++;
		}

		public synchronized void regRef(IFastRef refobj)
		{
			if (refobj != null && refobj.getRefId() > 0)
			{
				refField.put(Integer.valueOf(refobj.getRefId()), refobj);
			}

		}

		public synchronized void unregRef(IFastRef refobj)
		{
			if (refobj != null && refobj.getRefId() > 0)
			{
				refField.remove(Integer.valueOf(refobj.getRefId()));
			}

		}

		public synchronized IFastRef findRef(int refid)
		{
			return refField.get(Integer.valueOf(refid));
		}
	}

	private String id;
	private String name;
	private String version;
	private SimpleScheme refScheme;
	private AbstractFieldValue[] fieldValues;
	private IFastRefManager fastFieldRef;
	private boolean inited;

	private int refId;

	public SimpleSchemeData(SimpleScheme refscheme, String primarykey, String name, String version)
	{
		this.refScheme = refscheme;
		this.id = (primarykey != null) ? primarykey : "0";
		this.version = version;
		if ((name == null || name.length() <= 0) && this.refScheme != null)
			this.name = this.refScheme.getTitle();
		else
			this.name = name;
		fastFieldRef = (this.refScheme != null && !this.refScheme.isSubScheme()) ? new FastFieldRef() : null;
		refId = 0;
		if (fastFieldRef != null)
		{
			refId = fastFieldRef.newRefId();
			fastFieldRef.regRef(this);
		}
		inited = false;
	}

	public int getRefId()
	{
		return this.refId;
	}

	public String getSchemeName()
	{
		return (refScheme != null) ? refScheme.getName() : null;
	}

	public byte getRefType()
	{
		return IFastRef.REF_TYPE_DATA;
	}

	public IFastRefManager getRefManager()
	{
		return fastFieldRef;
	}

	public void bindRefManager(IFastRefManager fastfieldref)
	{
		if (this.refScheme != null && this.refScheme.isSubScheme() && fastfieldref != null)
		{
			if (fastFieldRef != null)
				fastFieldRef.unregRef(this);
			this.fastFieldRef = fastfieldref;
			this.refId = fastFieldRef.newRefId();
			fastFieldRef.regRef(this);
		}
	}

	public IFastRef fastGetRef(int refid)
	{
		return (fastFieldRef != null) ? fastFieldRef.findRef(refid) : null;
	}

	public boolean isInited()
	{
		return inited;
	}

	public void setInited(boolean v)
	{
		inited = v;
	}

	public AbstractFieldValue getFieldValue(String fieldname)
	{
		int idx = refScheme.fieldIndex(fieldname);
		if (idx >= 0 && idx < fieldValues.length)
			return fieldValues[idx];
		else
			return null;
	}

	public SimpleScheme getRefScheme()
	{
		return refScheme;
	}

	public int newRefId()
	{
		return (fastFieldRef != null) ? fastFieldRef.newRefId() : 0;
	}

	public void initNewInstance()
	{
		this.fieldValues = new AbstractFieldValue[refScheme.getFieldCount()];
		for (int i = 0; i < refScheme.getFieldCount(); i++)
		{
			AbstractField tmpField = refScheme.getFieldByIndex(i);
			if (tmpField.fieldType() == SchemeConstants.FIELD_TYPE_DATA
					|| tmpField.fieldType() == SchemeConstants.FIELD_TYPE_CHOICE
					|| tmpField.fieldType() == SchemeConstants.FIELD_TYPE_REFERENCE
					|| tmpField.fieldType() == SchemeConstants.FIELD_TYPE_LIST)
			{
				fieldValues[i] = tmpField.creaetFieldValue(this);
				fastFieldRef.regRef(fieldValues[i]);
			}
			else
				fieldValues[i] = null;
		}
	}

	public String getPrimaryKey()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public String getVersion()
	{
		return version;
	}

	public boolean decode(byte[] bytedata) throws IOException
	{
		ByteArrayInputStream instream = new ByteArrayInputStream(bytedata);
		boolean r = decode(instream);
		instream.close();
		instream = null;
		return r;
	}

	public boolean decode(InputStream instream) throws IOException
	{
		int i;
		try
		{
			for (i = 0; i < fieldValues.length; i++)
			{
				fieldValues[i].decode(instream);
			}
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public byte[] encode() throws IOException
	{
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		encode(outstream);
		byte[] r = outstream.toByteArray();
		outstream.close();
		outstream = null;
		return r;
	}

	public void encode(OutputStream outstream) throws IOException
	{
		int i;
		try
		{
			for (i = 0; i < fieldValues.length; i++)
			{
				fieldValues[i].encode(outstream);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public SimpleXmlBuilder exportToXmlBuffer(boolean expanddetail, SimpleXmlBuilder xmlbuffer)
			throws IOException
	{
		SimpleXmlBuilder databuffer;
		if (xmlbuffer == null)
			databuffer = new SimpleXmlBuilder(SchemeConstants.ELEMENT_FLAG_SCHEMEDATA);
		else
			databuffer = xmlbuffer.addNode(SchemeConstants.ELEMENT_FLAG_SCHEMEDATA);
		databuffer.addPrty(SchemeConstants.PROPERTY_FLAG_SCHEME, refScheme.getName()).addPrty(
				SchemeConstants.PROPERTY_FLAG_ID, this.id).addPrty(SchemeConstants.PROPERTY_FLAG_NAME,
				this.name).addPrty(SchemeConstants.PROPERTY_FLAG_VERSION, refScheme.getVersion()).addPrty(
				SchemeConstants.PROPERTY_FLAG_REF, this.refId);
		for (int i = 0; i < this.fieldValues.length; i++)
		{
			if (fieldValues[i] != null)
			{
				fieldValues[i].exportXml(expanddetail, databuffer);
			}
		}
		return databuffer;
	}

	public String exportToXml() throws IOException
	{
		return exportToXml(false);
	}

	public String exportToXml(boolean expanddetail) throws IOException
	{
		SimpleXmlBuilder tmpBuffer = exportToXmlBuffer(expanddetail, null);
		return tmpBuffer.exportXml();
	}

	public void verify(IDataVerify verify)
	{

	}

	public void setFieldValue(String fieldname, String value)
	{
		AbstractField tmpField = refScheme.getField(fieldname);
		if (tmpField != null)
		{
			if (tmpField.getSyncFlag() != null)
				value = syncValue(tmpField.getSyncFlag(), value);
			fieldValues[tmpField.getLocalIndex()].setValue(value);
		}
	}

	public void addListFieldItem(String fieldname, SimpleSchemeData itemdata)
	{
		AbstractField tmpField = refScheme.getField(fieldname);
		if (tmpField != null && tmpField.fieldType() == SchemeConstants.FIELD_TYPE_LIST)
			fieldValues[tmpField.getLocalIndex()].addSubData(itemdata);
	}

	@Override
	public String syncValue(String name, String value)
	{
		AbstractField tmpField = refScheme.getField(name);
		if (tmpField != null)
		{
			fieldValues[tmpField.getLocalIndex()].setValue(value);
		}
		else if ("_ID".equalsIgnoreCase(name))
		{
			this.id = value;
		}
		else if ("_NAME".equalsIgnoreCase(name))
		{
			this.name = value;
		}
		return value;
	}
}
