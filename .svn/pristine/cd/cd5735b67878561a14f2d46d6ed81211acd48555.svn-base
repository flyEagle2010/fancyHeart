package com.doteyplay.utils;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;

import sun.net.www.protocol.file.FileURLConnection;
import sun.net.www.protocol.jar.JarURLConnection;

public class PackageScaner
{
	public static String[] scanNamespaceFiles(String namespace, String fileext)
	{
		String respath = namespace.replace('.', '/');
		respath = respath.replace('.', '/');

		List<String> tmpNameList = new ArrayList<String>();
		try
		{
			URL url = null;
			if (!respath.startsWith("/"))
				url = PackageScaner.class.getResource("/" + respath);
			else
				url = PackageScaner.class.getResource(respath);
			URLConnection tmpURLConnection = url.openConnection();
			String tmpItemName;
			System.out.println(tmpURLConnection.getClass().getName());
			if (tmpURLConnection instanceof JarURLConnection)
			{
				JarURLConnection tmpJarURLConnection = (JarURLConnection) tmpURLConnection;
				int nslength = respath.length();
				int tmpPos;
				String tmpPath;
				Enumeration<JarEntry> entrys = tmpJarURLConnection.getJarFile().entries();
				while (entrys.hasMoreElements())
				{
					JarEntry tmpJarEntry = entrys.nextElement();
					if (!tmpJarEntry.isDirectory())
					{
						tmpItemName = tmpJarEntry.getName();
						if (tmpItemName.indexOf('$') < 0
								&& (fileext == null || tmpItemName.endsWith(fileext)))
						{
							tmpPos = tmpItemName.lastIndexOf('/');
							if (tmpPos > 0)
							{
								tmpPath = tmpItemName.substring(0, tmpPos);
								if (respath.equals(tmpPath))
								{
									tmpNameList.add(tmpItemName.substring(tmpPos+1));
								}
							}
						}
					}
				}
			}
			else if (tmpURLConnection instanceof FileURLConnection)
			{
				File file = new File(url.getFile());
				if (file.exists() && file.isDirectory())
				{
					File[] fileArray = file.listFiles();
					for (File f : fileArray)
					{
						tmpItemName = f.getName();
						if (!f.isDirectory() && (fileext == null || tmpItemName.endsWith(fileext)))
						{
							tmpNameList.add(tmpItemName);
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (tmpNameList.size() > 0)
		{
			String[] r = new String[tmpNameList.size()];
			tmpNameList.toArray(r);
			tmpNameList.clear();
			return r;
		}
		return null;
	}

	public static void main(String[] args)
	{
		String[] files = scanNamespaceFiles("com.ibatis.sqlmap.client", ".class");
		for (int i = 0; files != null && i < files.length; i++)
		{
			System.out.println(files[i]);
		}
		System.out.println("**********************");
		files = scanNamespaceFiles("com.t4game.mmorpg.dreamgame.gameserver.action.activity", ".class");
		for (int i = 0; files != null && i < files.length; i++)
		{
			System.out.println(files[i]);
		}

	}
}
