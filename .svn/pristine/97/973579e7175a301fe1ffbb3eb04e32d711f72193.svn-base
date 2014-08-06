package com.doteyplay.game.config;

import org.apache.log4j.Logger;

import com.doteyplay.core.bhns.BOServiceConfig;
import com.doteyplay.core.configloader.KeyValueXMLReflection;
import com.doteyplay.game.BOConst;

public class ConfigLoader
{
	/**
	 * 日志对象
	 */
	private static final Logger logger = Logger.getLogger(ConfigLoader.class);
	/**
	 * 服务器配置信息文件
	 */
	public static final String SERVER_CONFIG_FILE = "server-config.xml";
	/**
	 * 游戏配置信息文件
	 */
	public static final String GAME_CONFIG_FILE = "game-config.xml";

	/**
	 * 初始化系统相关的配置信息
	 */
	public static void initConfig(String serverConfigFile)
	{
		if (serverConfigFile != null && !serverConfigFile.equals(""))
			KeyValueXMLReflection.reflect(ServerConfig.class, serverConfigFile);
		else
			KeyValueXMLReflection.reflect(ServerConfig.class,
					SERVER_CONFIG_FILE);
		KeyValueXMLReflection.reflect(GameConfig.class, GAME_CONFIG_FILE);

		if (logger.isInfoEnabled())
			printConfigInfo();

		// 加载数据配置，使用默认配置文件.使用默认值
//		DataStore.initialize(null);
		// 加载基础服务.使用默认配置文件

	}

	/**
	 * 打印出服务器启动的配置信息
	 */
	private static void printConfigInfo()
	{
		if (!logger.isInfoEnabled())
		{
			return;
		}
		logger.info("===========ServerConfig=========================");
		logger.info(ServerConfig.info());
		logger.info("===========GameConfig=========================");
		logger.info(GameConfig.info());
	}
}
