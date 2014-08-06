package com.doteyplay.game.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import com.doteyplay.core.bhns.active.ActiveServiceInfo;
import com.doteyplay.core.util.dependence.DepandenceSorter;
import com.doteyplay.core.util.dependence.DependenceScaner;
import com.doteyplay.core.util.dependence.IDependence;
import com.doteyplay.core.util.dependence.IDependenceAssembly;
import com.doteyplay.game.config.ServerConfig;

public class TemplateService implements ITemplateService,IDependenceAssembly
{

	private Map<Class<?>, Map<Integer, TemplateObject>> templateObjects;
	private Map<String,TemplateConfig> templateConfigs = new LinkedHashMap<String, TemplateConfig>();
	private TemplateFileParser objectsParser;
	private String resourceFolder;

	private TemplateService(String resourceFolder)
	{
		this.resourceFolder = resourceFolder;
	}

	public void init(URL cfgPath)
	{
		this.loadConfig(cfgPath);
		
		templateObjects = new LinkedHashMap<Class<?>, Map<Integer, TemplateObject>>();
		objectsParser = new TemplateFileParser();
		InputStream is = null;
		String fileName = null;

		for (TemplateConfig cfg : templateConfigs.values())
		{
			try
			{
				fileName = cfg.getFileName();
				if (fileName == null)
				{
					this.getTemplateParser(cfg).parseXlsFile(cfg.getClasses(),
							templateObjects, null);
					continue;
				}

				String xlsPath = resourceFolder + File.separator
						+ cfg.getFileName();
				is = new FileInputStream(xlsPath);
				this.getTemplateParser(cfg).parseXlsFile(cfg.getClasses(),
						templateObjects, is);
				is.close();
			} catch (Exception e)
			{
				throw new ConfigException(
						"Errors occurs while parsing xls file:" + fileName, e);
			} finally
			{
				if (is != null)
				{
					try
					{
						is.close();
					} catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		patchUpAndCheck();
	}

	private void loadConfig(URL cfgPath)
	{
		Element root = JdomUtils.getRootElemet(cfgPath);
		List<Element> fileElements = root.getChildren();
		for (Element fileElement : fileElements)
		{
			String fileName = fileElement.getAttributeValue("NAME");
			String parserClassName = fileElement.getAttributeValue("parser");
			List<Element> sheetElements = fileElement.getChildren();
			Class<?>[] fileSheetClasses = new Class<?>[sheetElements.size()];
			
			String dependences = fileElement.getAttributeValue("DEPENDENCES");
			String[] depands = null;
			if(!StringUtils.isEmpty(dependences))
				depands = dependences.split(",");
			
			for (int i = 0; i < sheetElements.size(); i++)
			{
				Element sheet = sheetElements.get(i);
				String className = sheet.getAttributeValue("CLASS");
				
				if (StringUtils.isEmpty(className))
				{
					fileSheetClasses[i] = null;
					continue;
				}
				try
				{
					Class<?> clazz = Class.forName(className);
					fileSheetClasses[i] = clazz;
				} catch (Exception e)
				{
					throw new ConfigException(e);
				}
			}
			TemplateConfig templateConfig = new TemplateConfig(fileName,
					fileSheetClasses,depands);
			
			if (parserClassName != null
					&& (parserClassName = parserClassName.trim()).length() > 0)
			{
				templateConfig.setParserClassName(parserClassName);
			}
			templateConfigs.put(fileName, templateConfig);
			
		}
		
		this.doSort();
	}
	
	private void doSort()
	{
		boolean check = this.checkDependRelation();
		if (!check)
			throw new RuntimeException("template data depaendRelation error");
		
		DepandenceSorter sorter = new DepandenceSorter();
		for (TemplateConfig info : templateConfigs.values())
			sorter.addElement(info.getFileName(),
					info.getDependence());

		List<String> sortedList = sorter.sort();
		Map<String,TemplateConfig> sortedMap = new LinkedHashMap<String, TemplateConfig>();
		for(String name:sortedList)
			sortedMap.put(name, templateConfigs.get(name));
		
		templateConfigs.clear();
		templateConfigs = null;
		templateConfigs = sortedMap;
	}
	/**
	 * �������е�xml��Ϣ��,���м��������ϵ.
	 * 
	 * @return false Ϊ��������ϵ���Ϸ�. true ��������.
	 */
	public boolean checkDependRelation()
	{
		Collection<TemplateConfig> tmpBlockInfos = templateConfigs.values();
		if (tmpBlockInfos != null)
		{
			for (TemplateConfig tmpBlockInfo : tmpBlockInfos)
			{
				if (!DependenceScaner.checkValidation(tmpBlockInfo.getFileName(),
						this))
				{
					throw new RuntimeException("dependence of " + tmpBlockInfo.getFileName()
							+ " is invalid");
				}
			}
		}
		return true;
	}

	public <T extends TemplateObject> T get(int id, Class<T> clazz)
	{
		Map<Integer, TemplateObject> map = templateObjects.get(clazz);
		return (T) map.get(id);
	}

	@Override
	public <T extends TemplateObject> Map<Integer, T> getAll(Class<T> clazz)
	{
		return (Map<Integer, T>) templateObjects.get(clazz);
	}

	public Map<String, TemplateConfig> getTemplateCfgs()
	{
		return templateConfigs;
	}

	@Override
	public <T extends TemplateObject> void add(T t)
	{
		Map<Integer, TemplateObject> objs = templateObjects.get(t.getClass());
		if (objs == null || objs.containsKey(t.getId()))
			return;

		objs.put(t.getId(), t);
	}

	@Override
	public <T extends TemplateObject> Map<Integer, T> removeAll(Class<T> class1)
	{
		return (Map<Integer, T>) templateObjects.remove(class1);
	}

	@Override
	public <T extends TemplateObject> boolean isTemplateExist(int id,
			Class<T> class1)
	{
		Map<Integer, TemplateObject> map = templateObjects.get(class1);
		if (null != map)
		{
			return null == map.get(id) ? false : true;
		}
		return false;
	}

	public boolean reload(String fileName, int index)
	{
		if (templateConfigs != null)
		{
			for (TemplateConfig cfg : templateConfigs.values())
			{
				if (fileName.equals(cfg.getFileName()))
				{
					String xlsPath = resourceFolder + File.separator + fileName;
					TemplateFileParser tmplFileParser = this
							.getTemplateParser(cfg);
					if (cfg.getClasses().length == 0)
					{
						try
						{
							InputStream is = null;
							is = new FileInputStream(xlsPath);
							tmplFileParser.parseXlsFile(null, templateObjects,
									is);
						} catch (Exception e)
						{
							throw new ConfigException(
									"errors occurs while parsing xls file:"
											+ fileName, e);
						}
					} else
					{
						tmplFileParser.parseXlsFile(xlsPath, index,
								cfg.getClasses()[index], templateObjects);
					}
					patchUpAndCheck();
					return true;
				}
			}
		}
		return false;
	}

	private void patchUpAndCheck()
	{
		boolean hasError = false;
		Collection<Map<Integer, TemplateObject>> tplObjMaps = templateObjects
				.values();
		for (Map<Integer, TemplateObject> tplObjMap : tplObjMaps)
		{
			Collection<TemplateObject> templates = tplObjMap.values();
			for (TemplateObject templateObject : templates)
			{
				try
				{
					templateObject.patchUp();
				} catch (Exception e)
				{
					e.printStackTrace();
					hasError = true;
				}
			}
		}

		for (Map<Integer, TemplateObject> tplObjMap : tplObjMaps)
		{
			Collection<TemplateObject> templates = tplObjMap.values();
			for (TemplateObject templateObject : templates)
			{
				try
				{
					templateObject.check();
				} catch (Exception e)
				{
					e.printStackTrace();
					hasError = true;
				}
			}
		}
		if (hasError)
			System.exit(1);
	}

	private TemplateFileParser getTemplateParser(TemplateConfig cfg)
	{
		if (cfg.getParserClassName() != null
				&& cfg.getParserClassName().length() > 0)
		{
			try
			{
				Class<?> clazz = Class.forName(cfg.getParserClassName());
				Constructor<?> constructor = clazz.getConstructor();
				return (TemplateFileParser) constructor.newInstance();
			} catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		} else
			return objectsParser;
	}

	// **********************************************
	private static TemplateService instance = new TemplateService(ServerConfig.CONFIG_FILE_PATH);

	public static TemplateService getInstance()
	{
		return instance;
	}

	@Override
	public IDependence getItem(String name)
	{
		return templateConfigs.get(name);
	}
	
}