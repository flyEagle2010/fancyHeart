package com.doteyplay.game;

public class CommonConstants
{
	public static final byte FALSE = 0;
	public static final byte TRUE = 1;
	
	//主角固定id 女
	public static final int ROLE_SPRITEID_WOMEN = 0;
	//主角固定id 男
	public static final int ROLE_SPRITEID_MEN = 1;
	
	public static final int MAX_AREA_NUM = 10000;
	public static final long PER_AREA_ID_DELTA = Long.MAX_VALUE / MAX_AREA_NUM;
	
	//最大金币数量
	public static final int MAX_MONEY = Integer.MAX_VALUE;
	public static final int MAX_ENERGY = Integer.MAX_VALUE;
	//最大等级
	public static final int ROLE_MAX_LEVEL = 99;
	public static int[] ROLE_LEVEL_EXP = new int[ROLE_MAX_LEVEL];
	public static int[] NPC_LEVEL_EXP = new int[ROLE_MAX_LEVEL];
	
	
	/**
	 * 用户未登录
	 */
	public final static byte USER_STATE_UNLOGIN = 0;
	/**
	 * 用户已登录
	 */
	public final static byte USER_STATE_LOGEDIN = 1;
	
	//普通用户
	public final static byte USER_TYPE_DEFAULT = 0;
	//gm用户
	public final static byte USER_TYPE_GM = 1;
	/**
	 * 文本替换标示
	 */
	public static final String TEXT_REPLACE_REGULAR_DEFAULT = "$";
	/**
	 * 列表分割符
	 */
	public static final String COMMON_LIST_TO_STRING_SPLIT_FLAG = ",";

	public static final int DECK_NUM = 30;

	public static final int MAX_CARD_NUM = 200;
	public static final int MAX_SAME_CARD_IN_DECK = 2;

	public static final int ADDTYPE_VALUE = 0;
	public static final int ADDTYPE_RATE = 1;
	
	public static final int SCENE_OBJECT_TYPE_SPRITE = 0;

	public static final int SPRITE_TYPE_HERO = 0;
	public static final int SPRITE_TYPE_PET = 1;
	public static final int SPRITE_TYPE_NPC = 2;
	
    

}



