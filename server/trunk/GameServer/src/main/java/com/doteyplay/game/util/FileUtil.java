package com.doteyplay.game.util;

import java.net.URL;

public class FileUtil
{
	public static URL getConfigURL(String fileName)
	{
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		return classLoader.getResource(fileName);
	}
	
	public static String getConfigPath(String fileName)
	{
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		return classLoader.getResource(fileName).getPath();
	}
}
