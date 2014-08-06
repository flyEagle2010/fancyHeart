package com.doteyplay.simplescheme;

import java.io.IOException;

import com.doteyplay.xmlsupport.IParamterSupport;
import com.doteyplay.xmlsupport.ISimpleParamters;
import com.doteyplay.xmlsupport.XmlFileSupport;

public class SchemeDataLoader implements IParamterSupport
{
	private final static int LOADING_STACK_DEEP = 20;

	private class LoadingInfo
	{
		private boolean loadingList;
		private SimpleSchemeData refSchemeData;
		private String listFieldName;

		public LoadingInfo(SimpleSchemeData schemedata)
		{
			refSchemeData = schemedata;
			loadingList = false;
			listFieldName = null;
		}

		public LoadingInfo(SimpleSchemeData schemedata, String listfieldname)
		{
			refSchemeData = schemedata;
			loadingList = true;
			listFieldName = listfieldname;
		}

		public SimpleSchemeData getSchemeData()
		{
			return refSchemeData;
		}

		public void setFieldValue(String fieldname, String value)
		{
			refSchemeData.setFieldValue(fieldname, value);
		}

		public void appendListItem(SimpleSchemeData itemdata)
		{
			if (loadingList && listFieldName != null)
				refSchemeData.addListFieldItem(listFieldName, itemdata);
		}

		public void tidyLoadingData()
		{

		}

		public void tidyLoadingList()
		{

		}

		public boolean isLoadingList()
		{
			return loadingList;
		}
	}

	private SimpleSchemeData rootSchemeData;
	private LoadingInfo loadingSchemeData;
	private LoadingInfo[] loadingSchemeStack = new LoadingInfo[LOADING_STACK_DEEP];
	private int loadingStackCursor;

	public SchemeDataLoader(SimpleSchemeData refschemedata)
	{
		this.rootSchemeData = refschemedata;
	}

	public SimpleSchemeData load_scheme_data_from_xml(String xmldata) throws IOException
	{
		XmlFileSupport.parseXmlData(xmldata, this);
		return null;
	}

	public void load_dataScheme_from_file(String schemefile)
	{
		try
		{
			XmlFileSupport.parseXmlFromFile(schemefile, this);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void loadingPrepare()
	{
		loadingSchemeData = null;
		loadingStackCursor = -1;
		for (int i = 0; i < LOADING_STACK_DEEP; i++)
			loadingSchemeStack[i] = null;
	}

	private boolean pushLoading(LoadingInfo lodinginfo)
	{
		if (loadingStackCursor < 0)
			loadingStackCursor = 0;
		loadingSchemeData = lodinginfo;
		loadingSchemeStack[loadingStackCursor] = loadingSchemeData;
		loadingStackCursor++;
		return true;
	}

	private boolean popLoading()
	{
		LoadingInfo lastLoading = null;
		if (loadingStackCursor > 0)
		{
			lastLoading = loadingSchemeStack[loadingStackCursor - 1];
			loadingSchemeStack[loadingStackCursor - 1] = null;
			loadingStackCursor--;
		}
		if (loadingStackCursor > 0)
		{
			loadingSchemeData = loadingSchemeStack[loadingStackCursor - 1];
			if (loadingSchemeData.isLoadingList())
				loadingSchemeData.appendListItem(lastLoading.getSchemeData());
		}
		else
		{
			loadingSchemeData = null;
			loadingSchemeStack[0] = null;
			loadingStackCursor = -1;
		}
		return true;
	}

	@Override
	public void putParamter(ISimpleParamters element)
	{
		if (element == null)
			return;

		String tmpElementName = element.getDataName();
		if (SchemeConstants.ELEMENT_FLAG_SCHEMEDATA.equals(tmpElementName))
		{
			String tmpVersion = element.getValue(SchemeConstants.PROPERTY_FLAG_VERSION);
			String tmpSchemeName = element.getValue(SchemeConstants.PROPERTY_FLAG_SCHEME);
			String tmpPrimaryKey = element.getValue(SchemeConstants.PROPERTY_FLAG_ID);
			String tmpName = element.getValue(SchemeConstants.PROPERTY_FLAG_NAME);
			SimpleScheme tmpScheme = SchemeLoader.getScheme(tmpSchemeName);
			SimpleSchemeData tmpSchemeData;
			if (loadingSchemeData == null)
			{
				if (loadingStackCursor <= 0)
				{
					if (rootSchemeData == null)
						rootSchemeData = tmpScheme.newInstnce(tmpPrimaryKey, tmpName, tmpVersion);
					else
					{
						//TODO���汾���
					}
					pushLoading(new LoadingInfo(rootSchemeData));
				}
				else
				{
					tmpSchemeData = tmpScheme.newInstnce(tmpPrimaryKey, tmpName, tmpVersion);
					tmpSchemeData.bindRefManager(rootSchemeData.getRefManager());
					pushLoading(new LoadingInfo(tmpSchemeData));
				}
			}
			else
			{
				tmpVersion = element.getValue(SchemeConstants.PROPERTY_FLAG_VERSION);
				if (tmpVersion == null || tmpVersion.length() == 0)
					tmpVersion = loadingSchemeData.getSchemeData().getVersion();
				tmpSchemeData = tmpScheme.newInstnce(tmpPrimaryKey, tmpName, tmpVersion);
				tmpSchemeData.bindRefManager(loadingSchemeData.getSchemeData().getRefManager());
				tmpSchemeData.initNewInstance();
				pushLoading(new LoadingInfo(tmpSchemeData));
			}
		}
		else if (SchemeConstants.ELEMENT_FLAG_FIELD.equals(tmpElementName))
		{
			if (loadingSchemeData != null)
			{
				String tmpFieldName = element.getValue(SchemeConstants.PROPERTY_FLAG_NAME);
				String tmpFieldValue = element.getValue(SchemeConstants.PROPERTY_FLAG_VALUE);
				loadingSchemeData.setFieldValue(tmpFieldName, tmpFieldValue);
			}
			else
			{
				// �����־
			}
		}
		else if (SchemeConstants.ELEMENT_FLAG_LIST.equals(tmpElementName))
		{
			if (loadingSchemeData != null)
			{
				String tmpFieldName = element.getValue(SchemeConstants.PROPERTY_FLAG_NAME);
				pushLoading(new LoadingInfo(loadingSchemeData.getSchemeData(), tmpFieldName));
			}
		}

	}

	@Override
	public void endParamter(String paramtername)
	{
		if (SchemeConstants.ELEMENT_FLAG_SCHEMEDATA.equals(paramtername))
		{
			if (loadingSchemeData != null)
			{
				loadingSchemeData.tidyLoadingData();
			}
			else
			{
				// ������Ϣ
			}
			this.popLoading();
		}
		else if (SchemeConstants.ELEMENT_FLAG_LIST.equals(paramtername))
		{
			if (loadingSchemeData != null)
			{
				loadingSchemeData.tidyLoadingList();
			}
			else
			{
				// ������Ϣ
			}
			this.popLoading();
		}
	}
	
	@Override
	public void onComplete()
	{
		
	}

	public SimpleSchemeData getRootSchemeData()
	{
		return rootSchemeData;
	}

	// *****************************************************

	public static SimpleSchemeData loadSchemeData(SimpleSchemeData refdata,String xmldata) throws IOException
	{
		SchemeDataLoader tmpLoader = new SchemeDataLoader(refdata);
		tmpLoader.load_scheme_data_from_xml(xmldata);
		SimpleSchemeData r = tmpLoader.getRootSchemeData();
		tmpLoader = null;
		return r;
	}

	public static SimpleSchemeData loadSchemeDataFromXmlFile(SimpleSchemeData refdata,String filename) throws IOException
	{
		SchemeDataLoader tmpLoader = new SchemeDataLoader(refdata);
		tmpLoader.load_dataScheme_from_file(filename);
		SimpleSchemeData r = tmpLoader.getRootSchemeData();
		tmpLoader = null;
		return r;
	}

	public static void main(String[] args)
	{
//		SchemeLoader.loadAllScheme("E:/work/LunaScheme/schemes");
//		System.out.println(SchemeLoader.summary());
//		try
//		{
//			SimpleSchemeData tmpData = SchemeDataLoader
//					.loadSchemeDataFromXmlFile("E:/work/LunaScheme/data/Animation/1001.xml");
//			System.out.println(tmpData.exportToXml());
//			FileOutputStream tmpOutStream = new FileOutputStream("E:/work/LunaScheme/data/Animation/1001.dat");
//			tmpData.encode(tmpOutStream);
//			tmpOutStream.close();
//			SimpleSchemeData tmpData2 = SchemeLoader
//					.newSchemeInstance("Animation", "1003", "1234", "1.0.0.1");
//			FileInputStream tmpInStream = new FileInputStream("E:/work/LunaScheme/data/Animation/1001.dat");
//			tmpData2.decode(tmpInStream);
//			System.out.println(tmpData2.exportToXml());
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
	}
}
