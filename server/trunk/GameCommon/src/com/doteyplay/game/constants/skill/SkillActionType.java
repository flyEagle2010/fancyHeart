package com.doteyplay.game.constants.skill;

/**
 * 主被动
 * @author AllenGao
 *
 */
public enum SkillActionType
{	
	INITIATIVE()
	{
		@Override
		public String toString()
		{
			return "主动";
		}
	},
	PASSIVE()
	{
		@Override
		public String toString()
		{
			return "被动";
		}
	},
}
