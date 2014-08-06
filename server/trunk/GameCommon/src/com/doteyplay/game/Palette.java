package com.doteyplay.game;

public class Palette
{
	private Palette()
	{
	}
	
	//实际效果参见: http://en.wikipedia.org/wiki/Web_colors

	public final static byte Black = 0;//黑色
	public final static byte Gray = 1;//灰色
	public final static byte Silver = 2;//银色
	public final static byte White = 3;//白色 1
	
	public final static byte Red = 4;//红色 1
	public final static byte Maroon = 5;//棕色
	public final static byte Purple = 6;//紫色
	public final static byte Fuchsia = 7; //粉
	public final static byte Green = 8; //深绿
	public final static byte Lime = 9; //绿
	public final static byte Olive = 10; //橄榄
	public final static byte Yellow = 11; //黄 1
	public final static byte Blue = 12; //蓝
	public final static byte Navy = 13; //海军蓝
	public final static byte Teal = 14; //藏青
	public final static byte Aqua = 15; //青 1
	
	public final static byte SkyBlue = 16;
	public final static byte DeepSkyBlue = 17;
	public final static byte DodgerBlue = 18;
	public final static byte RoyalBlue = 19;
	
	public final static byte SpringGreen = 20;
	public final static byte LimeGreen = 21;
	public final static byte GreenYellow = 22;
	
	public final static byte Tomato = 23;
	public final static byte OrangeRed = 24;
	public final static byte Orange = 25;
	
	public final static byte Gold = 26;
	public final static byte Goldenrod = 27;
	public final static byte Khaki = 28;
	public final static byte LightYellow = 29;// 1
	
	public final static byte LightBlue = 30;
	public final static byte LightSkyBlue = 31;
	
	private final static int[] COLORS = 
	{ 
		0x000000, //黑色
		0x808080, //灰色
		0xC0C0C0, //银色
		0xFFFFFF, //白色
		
		0xFF0000, //红色
		0x800000, //棕色
		0x800080, //紫色
		0xFF00FF, //粉
		0x008000, //深绿
		0x00FF00, //绿
		0x808000, //橄榄
		0xFFFF00, //黄
		0x0000FF, //蓝
		0x000080, //海军蓝
		0x008080, //藏青
		0x00FFFF, //青
		
		//蓝色组
		0x87CEEB,
		0x00BFFF,
		0x1E90FF,
		0x4169E1,
		
		//绿色组
		0x00FF7F,
		0x32CD32,
		0xADFF2F,
		
		//红色组
		0xFF6347,
		0xFF4500,
		0xFFA500,
		
		//黄色组
		0xFFD700,
		0xDAA520,
		0xF0E68C,
		0xFFFF8C,
		
		//淡蓝组
		0xADD8E6,
		0x87CEFA,
	};
	
	@Deprecated
	public final static int getColor(byte index)
	{
		return COLORS[index];
	}
}
