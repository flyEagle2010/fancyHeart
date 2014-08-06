package com.doteyplay.game.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Random;

import org.apache.log4j.Logger;
/**
 * 
 */
public final class MathUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MathUtil.class);

	private MathUtil() {
	}

	/**
	 * 取浮点数的整数部分
	 * 
	 * @param f
	 * @return
	 */
	public static int float2Int(float f) {
		BigDecimal b = BigDecimal.valueOf(f);
		return b.intValue();
	}

	/**
	 * 从指定范围的中抽取不重复的数字
	 * 
	 * @param limit
	 * @param num
	 * @return
	 */
	public static int[] random(int limit, int num) {
		if (limit < num)
			return null;
		else {
			int[] end = new int[num];
			Random random = new Random();
			StringBuffer temp = new StringBuffer();
			for (int i = 0; i < num; i++) {
				int ran = random.nextInt(limit);
				while (temp.indexOf(ran + ":") > -1) {
					ran = random.nextInt(limit);
				}
				temp.append(ran).append(":");
				end[i] = ran;
			}
			return end;
		}
	}

	public static String ZEROS = "000000";

	/**
	 * 获取浮点数字符串
	 * 
	 * @param a
	 *            数据
	 * @param n
	 *            小数点位数
	 * @return
	 */
	public static String getFloatString(int a, int n) {
		if (n == 0)
			return String.valueOf(a);
		// int b=TENS[n];
		// int b1=a/b;
		// int b2=a%b;
		// String.valueOf(b1)+"."

		String s = String.valueOf(a);
		int size = s.length();
		if (size > n) {
			return s.substring(0, size - n) + "." + s.substring(size - n);
		} else {
			return "0." + ZEROS.substring(0, n - size) + s;
		}
	}

	/**
	 * 该函数计算从(x1,y1)到(x2,y2)的距离，相对误差为3.5% 
	 */
	public static int fastDistance2D(int x1, int y1, int x2, int y2) {
		return fastDistance2D(Math.abs(x1-x2), Math.abs(y1-y2));
	}
	
	/**
	 * 该函数计算从(0,0)到(x,y)的距离，相对误差为3.5% 请保证x,y为正数
	 */
	public static int fastDistance2D(int x, int y) {
		int mn = Math.min(x, y);
		return (x + y - (mn >> 1) - (mn >> 2) + (mn >> 4));
	}

	/**
	 * 该函数计算(0,0,0)到(x,y,z)的距离，相对误差为8% 请保证fx, fy, fz为正数
	 */
	float fastDistance3D(float fx, float fy, float fz) {
		int temp;
		int x, y, z;
		x = (int) (fx * 1024);
		y = (int) (fy * 1024);
		z = (int) (fz * 1024);
		// 排序
		if (y < x) {
			temp = y;
			y = x;
			x = temp;
		}
		if (z < y) {
			temp = z;
			z = y;
			y = temp;
		}
		if (y < x) {
			temp = y;
			y = x;
			x = temp;
		}

		int dist = (z + 11 * (y >> 5) + (x >> 2));
		return ((float) (dist >> 10));
	}

	public static void main(String[] args) {
		t2();
//		int x = 30;
//		int y = 40;
//		System.out.println(FastDistance2D(x, y));
//		System.out.println(Math.sqrt(x * x + y * y));
//
//		System.out.println(CarmSqrt((float) (x * x + y * y)));
//		System.out.println(Math.sqrt(x * x + y * y));

	}

	static float CarmSqrt(float x) {
		int intPart;
		float floatPart;
		int intPart2;
		float floatPart2;

		floatPart = x;
		floatPart2 = x;
		intPart = bytesToInt(floatToByte(x));
		intPart2 = intPart;
		intPart = 0x1FBCF800 + (intPart >> 1);
		intPart2 = 0x5f3759df - (intPart2 >> 1);

		floatPart = byteToFloat(intToByte(intPart));
		floatPart2 = byteToFloat(intToByte(intPart2));
		return 0.5f * (floatPart + (x * floatPart2));
	}

	// float转byte[]
	public static byte[] floatToByte(float v) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		byte[] ret = new byte[4];
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(v);
		bb.get(ret);
		return ret;
	}

	// byte[]转float
	public static float byteToFloat(byte[] v) {
		ByteBuffer bb = ByteBuffer.wrap(v);
		FloatBuffer fb = bb.asFloatBuffer();
		return fb.get();
	}

	public static byte[] intToByte(int i) {
		byte[] abyte0 = new byte[4];
		abyte0[0] = (byte) (0xff & i);
		abyte0[1] = (byte) ((0xff00 & i) >> 8);
		abyte0[2] = (byte) ((0xff0000 & i) >> 16);
		abyte0[3] = (byte) ((0xff000000 & i) >> 24);
		return abyte0;
	}

	public static int bytesToInt(byte[] bytes) {
		int addr = bytes[0] & 0xFF;
		addr |= ((bytes[1] << 8) & 0xFF00);
		addr |= ((bytes[2] << 16) & 0xFF0000);
		addr |= ((bytes[3] << 24) & 0xFF000000);
		return addr;
	}
	
	public static void t2(){
			try{
				int x = 30;
				int y = 40;
				float fn = x*x + y*y;
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				DataOutputStream out = new DataOutputStream(bout);
				out.writeFloat(fn);
				byte[] v = bout.toByteArray();
				DataInputStream in = new DataInputStream(new ByteArrayInputStream(
						v));
				int n = in.readInt();
				System.out.println(n);
				System.out.println(bytesToInt(floatToByte(fn)));
				System.out.println(CarmSqrt(fn, n));
				System.out.println(Math.sqrt(fn));
				System.out.println(fastDistance2D(x, y));
			}catch(Exception e){
				logger.error("",e);

			}
		}
	   static float CarmSqrt(float x, int y1) throws Exception{
	        int intPart;
	        float floatPart;
	        int intPart2;
	        float floatPart2;

		floatPart = x;
		floatPart2 = x;
		intPart = (int)y1;
		intPart2 = (int)y1;
		intPart = 0x1FBCF800 + (intPart >> 1);
		intPart2 = 0x5f3759df - (intPart2 >> 1);
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(bout);
		out.writeInt(intPart);
		out.writeInt(intPart2);
		byte[] v = bout.toByteArray();
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(
				v));
		floatPart = in.readFloat();
		floatPart2 = in.readFloat();
		return 0.5f*(floatPart + (x * floatPart2));
	}
	// static float CarmSqrt(float x){
	// union{
	// int intPart;
	// float floatPart;
	// } convertor;
	// union{
	// int intPart;
	// float floatPart;
	// } convertor2;
	//
	// convertor.floatPart = x;
	// convertor2.floatPart = x;
	// convertor.intPart = 0x1FBCF800 + (convertor.intPart >> 1);
	// convertor2.intPart = 0x5f3759df - (convertor2.intPart >> 1);
	// return 0.5f*(convertor.floatPart + (x * convertor2.floatPart));
	// }

}
