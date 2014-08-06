package com.doteyplay.game.constants.sprite;

public enum SpritePositionType
{
	//前
	POSITION_TYPE_F()
	{
		@Override
		public String toString()
		{
			return "前排";
		}
	},
	//中
	POSITION_TYPE_M()
	{
		@Override
		public String toString()
		{
			return "中排";
		}
	},
	//后
	POSITION_TYPE_A()
	{
		@Override
		public String toString()
		{
			return "后排";
		}
	},
}
