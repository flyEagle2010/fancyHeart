package com.doteyplay.game.domain.common;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.Serializable;

import com.doteyplay.game.domain.gamedatabean.GameDataBean;
import com.doteyplay.game.gamedata.data.IGameData;

public class AbstractGameObject<T extends IGameData> implements Serializable
{
	/**
	 * 游戏对象Id
	 */
	protected int id;
	/**
	 * 游戏对象类型
	 */
	protected int dataType;
	/**
	 * 游戏对象名称
	 */
	protected String name;

	/**
	 * 开放标识
	 */
	protected byte openFlag;
	/**
	 * 初始化标识
	 */
	private boolean initialized;
	/**
	 * 游戏数据对象
	 */
	private T gameData;

	protected AbstractGameObject(T gameData)
	{
		this.gameData = gameData;
	}

	public void init(GameDataBean bean)
	{
		this.id = bean.getResourceId();
		this.dataType = bean.getResourceType();
		this.name = bean.getName();
		this.openFlag = bean.getOpenFlag();

		try
		{
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(
					bean.getServerData()));

			this.gameData.load(in);
			in.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		this.initialized = true;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getDataType()
	{
		return dataType;
	}

	public void setDataType(int dataType)
	{
		this.dataType = dataType;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public byte getOpenFlag()
	{
		return openFlag;
	}

	public void setOpenFlag(byte openFlag)
	{
		this.openFlag = openFlag;
	}

	public boolean isInitialized()
	{
		return initialized;
	}

	public void setInitialized(boolean initialized)
	{
		this.initialized = initialized;
	}

	public T getGameData()
	{
		return gameData;
	}

	public void setGameData(T gameData)
	{
		this.gameData = gameData;
	}
}
