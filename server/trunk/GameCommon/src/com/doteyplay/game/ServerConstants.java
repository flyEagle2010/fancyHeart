package com.doteyplay.game;

public class ServerConstants
{
	/**
	 * 正常
	 */
	public static final int DELETE_FLAG_NORMAL = 0;
	/**
	 * 已删除
	 */
	public static final int DELETE_FLAG_DELETED = 1;
	/**
	 * 限时封停
	 */
	public static final int DELETE_FLAG_LIMIT_LOCK = 2;
	/**
	 * 永久封停
	 */
	public static final int DELETE_FLAG_LOCKED = 3;
	
	
	/**
	 * 状态类型
	 */
	public static final byte LOGIN_SUCCESS = 0;// 成功
	public static final byte LOGIN_NON_USER = 1;// 用户不存在
	public static final byte LOGIN_ERROR_PASS = 2;// 密码错误
	public static final byte LOGIN_NON_ROLE = 3;// 角色不存在
	public static final byte LOGIN_ONLINE = 4;// 已有角色登陆
	public static final byte LOGIN_NON_NEW = 5;// 客户端版本号太低

	public static final byte LOGIN_SERVER_FULL = 6; // 服务器玩家人数达到上限
	public static final byte LOGIN_SERVER_CLOSED = 7;// 服务器已关闭
	public static final byte LOGIN_ROLE_LOCKED = 8;// 角色永久封停
	public static final byte LOGIN_ROLE_LIMIT_LOCK = 9;// 角色限时封停
	public static final byte LOGIN_ALERT_MSG = 10;// 其它提示信息
	
}
