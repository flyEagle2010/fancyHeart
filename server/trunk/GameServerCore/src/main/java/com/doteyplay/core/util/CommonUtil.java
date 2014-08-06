package com.doteyplay.core.util;

import com.doteyplay.constant.Constants;



/**
 * 有用的数学工具
 * 
 */
public final class CommonUtil
{
	private CommonUtil()
	{
	}
	 
	/**
	 * 快速计算从(0,0)到(x,y)的距离，相对误差为3.5%   
	 */
	public static int fastDistance2D(int x, int y)   
	{   
		x = Math.abs(x);   
		y = Math.abs(y);   
		int mn = Math.min(x,y);   
		return (x+y-(mn>>1)-(mn>>2)+(mn>>4));
	}

	/**
	 * 计算两点之间的距离
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static int distance(int x1, int y1, int x2, int y2)
	{
		int dx = x1 - x2;
		int dy = y1 - y2;
		return CommonUtil.sqrt(dx * dx + dy * dy);
	}
	
	public static boolean isInDistance(float distance, int x1, int y1, float z1, int x2, int y2, float z2)
	{
		int dx = x1 - x2;
		int dy = y1 - y2;
		float dz = z1 - z2;
		return (dx * dx + dy * dy + dz * dz)<=distance*distance;
	}

	/**
	 * 快速整数平方根, 比java.lnag.Math.sqrt(x)快3倍
	 * 当x小于289时,采用sqrt289(x),此时比java.lnag.Math.sqrt(x)快5倍
	 * @param x
	 * @return
	 */
	public static int sqrt(int x)
	{
		if(x < 289)
			return SquareRoot.fastSqrt(x);
		else
			return SquareRoot.sqrt(x);
	}
	
	/**
	 * 快速整数平方根, x 必须小于289, 比java.lnag.Math.sqrt(x)快5倍
	 * @param x
	 * @return
	 */
	public static int sqrt289(int x)
	{
		return SquareRoot.fastSqrt(x);
	}
	
	
	public static byte boolean2Byte(boolean value)
	{
		return value ? Constants.TRUE : Constants.FALSE;
	}
	
	public static boolean byte2Boolean(byte value)
	{
		if(value == Constants.TRUE)
			return true;
		else if(value == Constants.FALSE)
			return false;
		else
			throw new IllegalArgumentException("无效的BOOLEAN预定义值:" + value);
	}
	
}
