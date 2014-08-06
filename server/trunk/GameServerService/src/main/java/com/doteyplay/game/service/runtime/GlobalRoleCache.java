package com.doteyplay.game.service.runtime;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.doteyplay.game.domain.role.Role;

public class GlobalRoleCache
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(GlobalRoleCache.class);
	/**
	 * 游戏中用户MAP
	 */
	private Map<Long, Role> roleMap = new ConcurrentHashMap<Long, Role>();
	
	/**
	 * 获取指定ID的用户对象
	 * 
	 * @param RoleId
	 * @return
	 */
	public final Role getRoleById(long roleId)
	{
		return roleMap.get(roleId);
	}

	/**
	 * 移除指定ID的用户对象
	 * 
	 * @param RoleId
	 * @return
	 */
	public final Role removeRoleById(long roleId)
	{
		return roleMap.remove(roleId);
	}

	/**
	 * 获取全部在线角色
	 * 
	 * @return
	 */
	public final Map<Long, Role> getAllRole()
	{
		return Collections.unmodifiableMap(roleMap);
	}

	public final void putRoleMap(long roleId, Role role)
	{
		roleMap.put(roleId, role);
	}

	// *******************************************
	private final static GlobalRoleCache instance = new GlobalRoleCache();

	public static GlobalRoleCache getInstance()
	{
		return instance;
	}
}
