package com.doteyplay.simplescheme;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import com.doteyplay.simplescheme.field.SchemeFieldFactory;
import com.doteyplay.utils.IOUtils;
import com.doteyplay.xmlsupport.IParamterSupport;
import com.doteyplay.xmlsupport.ISimpleParamters;
import com.doteyplay.xmlsupport.XmlFileSupport;

public class SchemeLoader implements IParamterSupport
{
	private final static Logger logger = Logger.getLogger("DataScheme");
	private final static int LOADING_STACK_DEEP = 20;

	private Map<String, SimpleScheme> dataSchemeMap = new ConcurrentHashMap<String, SimpleScheme>();
	private List<SimpleScheme> dataSchemeList = new Vector<SimpleScheme>();
	private SimpleScheme loadingScheme;
	private SimpleScheme[] loadingSchemeStack = new SimpleScheme[LOADING_STACK_DEEP];
	private int loadingStackCursor;

	private void load_dataScheme_from_file(String schemefile)
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
		loadingScheme = null;
		loadingStackCursor = 0;
		for (int i = 0; i < LOADING_STACK_DEEP; i++)
			loadingSchemeStack[i] = null;
	}

	private void load_scheme(String schemepath)
	{
		// �����������
		load_datascheme_folder(schemepath, null);
		init_all_scheme();
	}

	private void init_all_scheme()
	{
		// ��ʼ�����������ϵ
		SimpleScheme tmpScheme;
		for (int i = 0; i < this.dataSchemeList.size(); i++)
		{
			tmpScheme = (SimpleScheme) dataSchemeList.get(i);
			tmpScheme.init();
		}
	}

	private List<SimpleScheme> get_all_scheme()
	{
		List<SimpleScheme> tmpList = new ArrayList<SimpleScheme>(dataSchemeList.size());
		tmpList.addAll(dataSchemeList);
		return tmpList;
	}

	private void load_scheme_text(String schemetext)
	{
		XmlFileSupport.parseXmlFromFile(schemetext, this);
	}

	private void load_datascheme_folder(String schemepath, String namespace)
	{
		try
		{
			schemepath = IOUtils.fixPath(schemepath, true);
			File a = new File(schemepath);
			if (a.exists())
			{
				loadingPrepare();

				FileInputStream input;
				String[] file = a.list();
				File temp = null;
				for (int i = 0; i < file.length; i++)
				{
					temp = new File(schemepath + file[i]);

					if (temp.isFile() && temp.getName().endsWith(".xml"))
					{
						input = null;
						try
						{
							input = new FileInputStream(temp);
							XmlFileSupport.parseXmlData(input, this);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						finally
						{
							if (input != null)
								input.close();
						}
					}
					else if (temp.isDirectory())
					{
						if (namespace != null && namespace.length() > 0)
							load_datascheme_folder(schemepath + File.separator + file[i], namespace + "."
									+ temp.getName());
						else
							load_datascheme_folder(schemepath + File.separator + file[i], temp.getName());
					}
					temp = null;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private boolean pushScheme(SimpleScheme scheme)
	{
		if (loadingStackCursor < 0)
			loadingStackCursor = 0;
		loadingScheme = scheme;
		loadingSchemeStack[loadingStackCursor] = loadingScheme;
		loadingStackCursor++;
		return true;
	}

	private boolean popScheme()
	{
		if (loadingStackCursor > 0)
		{
			loadingSchemeStack[loadingStackCursor - 1] = null;
			loadingStackCursor--;
		}

		if (loadingStackCursor > 0)
		{
			loadingScheme = loadingSchemeStack[loadingStackCursor - 1];
		}
		else
		{
			loadingScheme = null;
		}
		return true;
	}

	@Override
	public void putParamter(ISimpleParamters element)
	{
		if (element == null)
			return;

		String tmpElementName = element.getDataName();
		String tmpVersion = null;
		if (SchemeConstants.ELEMENT_FLAG_SCHEME.equals(tmpElementName))
		{
			if (loadingScheme == null)
			{
				pushScheme(new SimpleScheme(element.getValue(SchemeConstants.PROPERTY_FLAG_NAME), element
						.getValue(SchemeConstants.PROPERTY_FLAG_TITLE), element
						.getValue(SchemeConstants.PROPERTY_FLAG_VERSION), null, this));
			}
			else
			{
				tmpVersion = element.getValue(SchemeConstants.PROPERTY_FLAG_VERSION);
				if (tmpVersion == null || tmpVersion.length() == 0)
					tmpVersion = loadingScheme.getVersion();
				pushScheme(new SimpleScheme(element.getValue(SchemeConstants.PROPERTY_FLAG_NAME), element
						.getValue(SchemeConstants.PROPERTY_FLAG_TITLE), tmpVersion, loadingScheme, this));
			}
		}
		else
		{
			if (loadingScheme != null)
			{
				loadingScheme.regField(SchemeFieldFactory.createSchemeField(element, loadingScheme));
			}
			else
			{
				// �����־
			}
		}

	}

	@Override
	public void endParamter(String paramtername)
	{
		if (SchemeConstants.ELEMENT_FLAG_SCHEME.equals(paramtername))
		{
			if (loadingScheme != null)
			{
				loadingScheme.lockScheme();// ��סģ��
				this.dataSchemeList.add(loadingScheme);
				this.dataSchemeMap.put(loadingScheme.getName(), loadingScheme);
			}
			else
			{
				// ������Ϣ
			}
			this.popScheme();
		}
	}
	
	@Override
	public void onComplete()
	{
	}
	
	private String get_summary()
	{
		StringBuffer tmpBuffer = new StringBuffer();
		SimpleScheme tmpScheme;
		for (int i = 0; i < this.dataSchemeList.size(); i++)
		{
			tmpScheme = (SimpleScheme) dataSchemeList.get(i);
			tmpBuffer.append(tmpScheme.exportXml()).append("\r\n");
		}
		return tmpBuffer.toString();
	}

	private SimpleScheme get_scheme(String schemename)
	{
		if (schemename != null && schemename.length() > 0)
			return this.dataSchemeMap.get(schemename);
		else
			return null;
	}

	// *****************************************************
	private static SchemeLoader instance;

	private synchronized static SchemeLoader getInstance()
	{
		if (instance == null)
		{
			instance = new SchemeLoader();
		}
		return instance;
	}

	public static void loadAllScheme(String folderpath)
	{
		SchemeLoader tmpManager = getInstance();
		if (tmpManager != null)
			tmpManager.load_scheme(folderpath);
	}

	public static void manualLoadSchemeText(String schemetext)
	{
		SchemeLoader tmpManager = getInstance();
		if (tmpManager != null)
			tmpManager.load_scheme_text(schemetext);
	}

	public static void manualInitAllScheme()
	{
		SchemeLoader tmpManager = getInstance();
		if (tmpManager != null)
			tmpManager.init_all_scheme();
	}

	public static List<SimpleScheme> getAllScheme()
	{
		SchemeLoader tmpManager = getInstance();
		if (tmpManager != null)
			return tmpManager.get_all_scheme();
		else
			return null;
	}

	public static String summary()
	{
		SchemeLoader tmpManager = getInstance();
		if (tmpManager != null)
			return tmpManager.get_summary();
		else
			return null;
	}

	public static SimpleScheme getScheme(String schemename)
	{
		SchemeLoader tmpManager = getInstance();
		if (tmpManager != null)
			return tmpManager.get_scheme(schemename);
		else
			return null;
	}

	public static SimpleSchemeData newSchemeInstance(String schemename, String primarykey, String name,
			String version)
	{
		SchemeLoader tmpManager = getInstance();
		if (tmpManager != null)
		{
			SimpleScheme tmpScheme = tmpManager.get_scheme(schemename);
			if (tmpScheme != null)
				return tmpScheme.newInstnce(primarykey, name, version);
		}
		return null;
	}

	public static void main(String[] args)
	{
		loadAllScheme("E:\\work\\LunaScheme\\schemes");
		System.out.println(summary());
	}

}
