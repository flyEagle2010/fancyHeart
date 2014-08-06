package com.doteyplay.game.constants;

/**
 * 技能效果触发事件枚举
 */
public enum ActivateEvent
{
	/**
	 * 不触发 0
	 */
	NONE()
	{
		@Override
		public String toString()
		{
			return "无";
		}
	},
	AUTO()
	{
		@Override
		public String toString()
		{
			return "自动";
		}
	},
	/**
	 * 入场时1
	 */
	ON_ENTER()
	{
		@Override
		public String toString()
		{
			return "入场时";
		}
	},
	ON_DIED()
	{
		@Override
		public String toString()
		{
			return "死亡";
		}
	},
	ON_KILL()
	{
		@Override
		public String toString()
		{
			return "杀死";
		}
	},
	ON_SKILL()
	{
		@Override
		public String toString()
		{
			return "主动技能";
		}
	},
	/**
	 * 攻击时(未必命中造成伤害)
	 */
	ON_ATTACK()
	{
		@Override
		public String toString()
		{
			return "攻击";
		}
	},

	/**
	 * 被攻击时(未必命中造成伤害)
	 */
	ON_BE_ATTACKED()
	{
		@Override
		public String toString()
		{
			return "被攻击";
		}
	},

	/**
	 * 产生伤害时
	 */
	ON_DAMAGE()
	{
		@Override
		public String toString()
		{
			return "产生伤害";
		}
	},

	/**
	 * 收到伤害时
	 */
	ON_BE_DAMAGE()
	{
		@Override
		public String toString()
		{
			return "受到伤害";
		}
	},

	;

	public static ActivateEvent getByValue(int value)
	{
		return ActivateEvent.values()[value];
	}
}
