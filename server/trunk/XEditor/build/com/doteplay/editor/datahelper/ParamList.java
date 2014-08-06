package com.doteplay.editor.datahelper;

import java.util.Hashtable;

import com.doteplay.editor.util.xml.simpleXML.StrUtils;

public class ParamList extends Hashtable<String, String>
{

	private static final long serialVersionUID = 4112578634028874840L;

	public Object putParam(String paramname, String paramvalue)
	{
		if (paramname != null && paramname.length() > 0)
			return this.put(paramname, (paramvalue != null) ? paramvalue : "");
		else
			return null;
	}

	public String getParam(String paramname)
	{
		if (paramname != null && paramname.length() > 0)
			return (String) this.get(paramname);
		else
			return null;
	}

	public void addMultiParam(String paramname, String paramvalue)
	{
		if (paramname != null && paramname.length() > 0)
		{
			if (this.containsKey(paramname))
			{
				String tmpValue = (String) this.get(paramname);
				if (tmpValue != null && tmpValue.startsWith("[")
						&& tmpValue.endsWith("]"))
				{
					if (paramvalue != null)
						tmpValue = tmpValue + "&[" + paramvalue + "]";
					else
						tmpValue = tmpValue + "&[]";
				} else
				{
					if (paramvalue != null)
						tmpValue = "[" + tmpValue + "]&[" + paramvalue + "]";
					else
						tmpValue = "[" + tmpValue + "]&[]";
				}
				this.put(paramname, tmpValue);
			} else
				this.put(paramname, (paramvalue != null) ? paramvalue : "");
		}
	}

	public String[] getMultiParams(String paramname)
	{
		if (paramname != null && paramname.length() > 0)
		{
			return parseMultiValues(this.get(paramname));
		} else
			return null;
	}

	public static String[] parseMultiValues(String refdata)
	{
		return StrUtils.parseBracketsStr(refdata, "&");
	}
}
