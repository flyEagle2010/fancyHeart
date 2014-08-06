package com.doteyplay.game.util;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.UnmodifiableClassException;

import org.apache.log4j.Logger;

import com.doteyplay.core.classloader.ClassOverride;

public class ClassReloadManager
{

	private static final Logger logger = Logger
			.getLogger(ClassReloadManager.class);

	private static ClassReloadManager instance = new ClassReloadManager();

	public static ClassReloadManager getInstance()
	{
		return instance;
	}

	/**
	 * 私有的构造方法
	 */
	private ClassReloadManager()
	{

	}

	/**
	 * 默认目录
	 */
	private static final String DEFAULT_RELOD_FILE_PATH = "";
	/**
	 * 是否开放
	 */
	private static boolean OPEN = true;

	public String reloadClass(String classPath, String filePath)
	{
		String msg = "class reload 成功！";
		if (!OPEN)
		{
			msg = "该功能暂未开放使用！";

		} else if (classPath == null || classPath.trim().equals(""))
		{
			msg = "class类路径不允许为空！";
		} else
		{
			filePath = filePath == null ? DEFAULT_RELOD_FILE_PATH : filePath;
			try
			{

				Class<?> reloadClass = Class.forName(classPath);

				ClassOverride.reload(reloadClass, new File(filePath));

			} catch (ClassNotFoundException e)
			{
				logger.error("class类路径错误", e);
				msg = "class类路径错误！";
			} catch (IOException e)
			{
				logger.error("未找到需替换的 class 文件 或 jar包", e);
				msg = "未找到需替换的 class 文件 或 jar包！";
			} catch (UnmodifiableClassException e)
			{
				logger.error("class reload 失败", e);
				msg = "class reload 失败";
			} catch (NullPointerException e)
			{
				logger.error(
						"可能是游戏启动没设置 jvm 参数： -javaagent:xxx\\classReloader.jar",
						e);
				msg = "可能是游戏启动没设置 jvm 参数： -javaagent:xxx\\classReloader.jar";
			} catch (UnsupportedOperationException e)
			{
				logger.error("目前暂不支持添加，删除方法，全局变量操作！");
				msg = "目前暂不支持添加，删除方法，全局变量操作！";
			} catch (Exception e)
			{
				logger.error("未知错误请重试", e);
				msg = "未知错误请重试！";
			}
		}
		return msg;
	}
	
}
