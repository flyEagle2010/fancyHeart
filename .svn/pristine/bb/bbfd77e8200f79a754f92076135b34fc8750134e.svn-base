package com.doteyplay.game.constants;

/**
 * 施放法术的方式
 */
public enum CastType
{
	/**
	 * 普通施放方式
	 */
	NORMAL,
	/**
	 * 效果触发技能施放方式
	 */
	TRIGGER,
	/**
	 * BUFF类施放
	 */
	BUFF, ;
	private static final boolean[] installBuff = { true, true, false };
	private static final boolean[] activateSourceEvent = { true, false, false };
	private static final boolean[] activateTargetEvent = { true, true, true };

	/**
	 * 指示此施放方式是否为目标安装buff(如果有的话)
	 */
	public boolean installBuff()
	{
		return installBuff[ordinal()];
	}

//	/**
//	 * 指示此施放方式是否触发源事件
//	 */
//	public boolean activateSourceEvent()
//	{
//		return activateSourceEvent[ordinal()];
//	}
//
//	/**
//	 * 指示此施放方式是否触发目标事件
//	 */
//	public boolean activateTargetEvent()
//	{
//		return activateTargetEvent[ordinal()];
//	}
}
