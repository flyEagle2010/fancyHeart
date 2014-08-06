package com.doteplay.editor.persistence;

import java.io.Reader;
import java.util.Properties;

import com.ibatis.common.resources.Resources;
import com.ibatis.dao.client.DaoManager;
import com.ibatis.dao.client.DaoManagerBuilder;

public class DaoConfig {
	
	private static DaoManager daoManager_game_data;
	private static final String resource_game_data = "com/doteplay/editor/persistence/gamedata-dao.xml";
	
	private DaoConfig(){
		
	}
	
	public static boolean initialize(Properties dbProps) {
		try {
			daoManager_game_data = newDaoManager(dbProps, resource_game_data);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	

	public static DaoManager getDaoManager_GameData() {
		return daoManager_game_data;
	}

	public static DaoManager newDaoManager(Properties props, String resource) {
		try {
			Reader reader = Resources.getResourceAsReader(resource);
			return DaoManagerBuilder.buildDaoManager(reader, props);
		} catch (Exception e) {
			throw new RuntimeException("无法初始化DAO配置.", e);
		}
	}
	
}
