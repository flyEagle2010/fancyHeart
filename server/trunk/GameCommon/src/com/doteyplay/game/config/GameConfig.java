package com.doteyplay.game.config;

import java.lang.reflect.Field;

import com.doteyplay.game.FieldAnnotation;

/**
 * 游戏相关配置信息，从game-config.xml读取
 * 
 */
public class GameConfig
{
	@FieldAnnotation(name = "防沉迷开关")
	public static boolean FCM;

	public static String info()
	{
		StringBuffer str = new StringBuffer();
		Field[] fields = GameConfig.class.getDeclaredFields();
		for (Field f : fields)
		{
			try
			{
				str.append(((FieldAnnotation) f.getAnnotations()[0]).name())
						.append(":")
						.append(String.valueOf(f.get(GameConfig.class)))
						.append("\n");
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return str.toString();
	}
}
