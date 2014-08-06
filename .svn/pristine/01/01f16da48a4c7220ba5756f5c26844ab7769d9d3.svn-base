package com.doteyplay.game.domain.gamebean;

import java.util.Date;

import com.google.gson.Gson;

public class UserBean extends BaseBean
{
	public static byte USER_STATE_UNLOGIN = 0;
	public static byte USER_STATE_LOGEDIN = 1;

	private long id;
	protected String name;
	private String password;

	/**
	 * 用户类型:0:普通用户,1:GM,2:封号
	 */
	private byte userType;
	private String channel;
	private byte state;
	private long lastLoginTime;
	private long createTime;
	private String lastLoginIp;

	private boolean over18 = false;
	private int key;

	private int lastAreaId;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public byte getUserType()
	{
		return userType;
	}

	public void setUserType(byte userType)
	{
		this.userType = userType;
	}

	public String getChannel()
	{
		return channel;
	}

	public void setChannel(String channel)
	{
		this.channel = channel;
	}

	public byte getState()
	{
		return state;
	}

	public void setState(byte state)
	{
		this.state = state;
	}

	public String getLastLoginIp()
	{
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp)
	{
		this.lastLoginIp = lastLoginIp;
	}

	public boolean isOver18()
	{
		return over18;
	}

	public void setOver18(boolean over18)
	{
		this.over18 = over18;
	}

	public int getKey()
	{
		return key;
	}

	public void setKey(int key)
	{
		this.key = key;
	}

	public int getLastAreaId()
	{
		return lastAreaId;
	}

	public void setLastAreaId(int lastAreaId)
	{
		this.lastAreaId = lastAreaId;
	}

	public long getLastLoginTime()
	{
		return lastLoginTime;
	}

	public void setLastLoginTime(long lastLoginTime)
	{
		this.lastLoginTime = lastLoginTime;
	}

	public long getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(long createTime)
	{
		this.createTime = createTime;
	}
}
