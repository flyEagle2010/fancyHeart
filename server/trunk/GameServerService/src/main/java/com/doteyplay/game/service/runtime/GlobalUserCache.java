package com.doteyplay.game.service.runtime;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.doteyplay.game.domain.user.User;

@SuppressWarnings("deprecation")
public class GlobalUserCache
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(GlobalUserCache.class);
	/**
	 * 游戏中用户MAP
	 */
	private Map<Long, User> userMap = new ConcurrentHashMap<Long, User>();

	/**
	 * 获取指定ID的用户对象
	 * 
	 * @param userId
	 * @return
	 */
	public final User getUserById(long userId)
	{
		return userMap.get(userId);
	}

	/**
	 * 移除指定ID的用户对象
	 * 
	 * @param userId
	 * @return
	 */
	public final User removeUserById(long userId)
	{
		return userMap.remove(userId);
	}
	
	/**
	 * 获取全部在线角色
	 * 
	 * @return
	 */
	public final Map<Long, User> getAllUser()
	{
		return Collections.unmodifiableMap(userMap);
	}

	public final void putUserMap(long userId, User user)
	{
		userMap.put(userId, user);
	}

	// **************************************
	private final static GlobalUserCache instance = new GlobalUserCache();

	public static GlobalUserCache getInstance()
	{
		return instance;
	}
}
