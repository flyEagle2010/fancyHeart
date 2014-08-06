package com.doteyplay.game.service.event;

public enum EventConst {
	
	//未定义
	SERVER_LOCATION_DEFIND(true),
	// 角色相关
	ROLE_LEVEL_UP_EVENT(true);
	
	private boolean _isSingleton;
	
	private EventConst(boolean issingleton)
	{
		this._isSingleton = issingleton;
	}
	
	public boolean isSingleton()
	{
		return this._isSingleton;
	}
	
	public static boolean isSingleton(int id)
	{
		if(0< id && id < values().length)
			return values()[id]._isSingleton;
		else
			throw new IllegalArgumentException("undefind event id = "+ id);
	}
}

