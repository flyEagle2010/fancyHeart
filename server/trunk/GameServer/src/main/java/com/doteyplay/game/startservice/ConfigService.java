package com.doteyplay.game.startservice;

import java.io.File;

import org.apache.log4j.Logger;

import com.doteyplay.core.classloader.ClassOverride;
import com.doteyplay.core.classloader.Test;
import com.doteyplay.core.dbcs.DBCS;
import com.doteyplay.core.resourcebundle.WordResource;
import com.doteyplay.core.server.ServerStatus;
import com.doteyplay.core.server.service.IServerService;
import com.doteyplay.core.server.service.IServerServiceException;
import com.doteyplay.game.config.ConfigLoader;
import com.doteyplay.game.config.ServerConfig;
import com.doteyplay.game.config.template.TextDataTemplate;
import com.doteyplay.game.util.FileUtil;
import com.doteyplay.game.util.excel.TemplateService;

public class ConfigService implements IServerService {
	private static final Logger logger = Logger.getLogger(ConfigService.class);
	private static ConfigService service = new ConfigService();

	private ConfigService() {
	}

	public static ConfigService getInstance() {
		return service;
	}

	@Override
	public void onDown() throws IServerServiceException {
	}

	public void onReady(String serverConfigFile) throws IServerServiceException {
		if (logger.isInfoEnabled())
			logger.info("配置服务信息准备");

		// 新的持久层加载
		DBCS.initialize("/db/");

		// 加载配置项
		ConfigLoader.initConfig(serverConfigFile);
		
		// 加载多语言项
		WordResource.init(ServerConfig.RESOURCE_BUNDLE_DEFAULT, ServerConfig.RESOURCE_BUNDLE_FILE);
		
//		//加载消息配置
//		try
//		{
//			MessageRegistry.getInstance().loadConfig(MessageCommands.MESSAGE_NUM);
//		} catch (FileNotFoundException e)
//		{
//			logger.error("配置文件未找到",e);
//
//		}

		// 变更服务器状态
		ServerStatus.initStatus(ServerConfig.MAX_PLAYERS);
	}

	@Override
	public void onStart() throws IServerServiceException {
	}

	@Override
	public void onReady() throws IServerServiceException {
	}
}
