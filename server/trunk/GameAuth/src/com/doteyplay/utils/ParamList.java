package com.doteyplay.utils;

import java.util.Properties;

public class ParamList extends Properties
{
	private static final long serialVersionUID = 4112578634028874840L;

	public Object putParam(String paramname, String paramvalue)
	{
		if (paramname != null && paramname.length() > 0)
			return setProperty(paramname, (paramvalue != null) ? paramvalue : "");
		else
			return null;
	}

	public String getParam(String paramname)
	{
		if (paramname != null && paramname.length() > 0)
			return this.getProperty(paramname);
		else
			return null;
	}

	public void addMultiParam(String paramname, String paramvalue)
	{
		if (paramname != null && paramname.length() > 0)
		{
			if (this.containsKey(paramname))
			{
				String tmpValue = this.getProperty(paramname);
				if (tmpValue != null && tmpValue.startsWith("[") && tmpValue.endsWith("]"))
				{
					if (paramvalue != null)
						tmpValue = tmpValue + "&[" + paramvalue + "]";
					else
						tmpValue = tmpValue + "&[]";
				}
				else
				{
					if (paramvalue != null)
						tmpValue = "[" + tmpValue + "]&[" + paramvalue + "]";
					else
						tmpValue = "[" + tmpValue + "]&[]";
				}
				this.setProperty(paramname, tmpValue);
			}
			else
				setProperty(paramname, (paramvalue != null) ? paramvalue : "");
		}
	}

	public String[] getMultiParams(String paramname)
	{
		if (paramname != null && paramname.length() > 0)
		{
			return parseMultiValues(this.getProperty(paramname));
		}
		else
			return null;
	}

	public static String[] parseMultiValues(String refdata)
	{
		return StrUtils.parseBracketsStr(refdata, "&");
	}
	
	public static void main(String[] args)
	{
		ParamList tmpParams=new ParamList();
		tmpParams.putParam("aa","bb");
		tmpParams.addMultiParam("aa", "cc");
		tmpParams.addMultiParam("aa", "cc[;;]ffadsfasd");
		tmpParams.putParam("aax","bb");
		String[] tmpValues=tmpParams.getMultiParams("aa");
		if(tmpValues!=null)
		{
			for(int i=0;i<tmpValues.length;i++)
				System.out.println(tmpValues[i]);
		}
		
		String tmpData=ParamUtils.properties2Xml(tmpParams);
		System.out.println(tmpData);
		Properties tmpParamList=ParamUtils.xml2Properties(tmpData);
		tmpValues=ParamList.parseMultiValues(tmpParamList.getProperty("aa"));
		if(tmpValues!=null)
		{
			for(int i=0;i<tmpValues.length;i++)
				System.out.println(tmpValues[i]);
		}
		
		
	}
}
