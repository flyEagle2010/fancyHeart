package com.doteyplay.game.domain.gamebean;

public class ServerTaskBean extends BaseBean
{
	private int id;
	private long nextUpdateTime;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public long getNextUpdateTime()
	{
		return nextUpdateTime;
	}

	public void setNextUpdateTime(long nextUpdateTime)
	{
		this.nextUpdateTime = nextUpdateTime;
	}
}
