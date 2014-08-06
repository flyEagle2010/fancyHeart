package com.doteyplay.game.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
public final class GameUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GameUtil.class);

	private GameUtil() {
	}

	public static String rootPath = System.getProperty("user.dir");
	public static String imgPath = System.getProperty("user.dir")
			+ File.separatorChar + "img" + File.separatorChar;
	public static String dataPath = System.getProperty("user.dir")
			+ File.separatorChar + "data" + File.separatorChar;

	public static WordFilter WordFilter_Chat = WordFilter.newChatFilter();
	public static WordFilter WordFilter_Name = WordFilter.newNameFilter();

	private static Random random = new Random(System.currentTimeMillis());

	@Deprecated
	public static int[] BYTEMASK = { 0x80, 0x40, 0x20, 0x10, 0x08, 0x04, 0x02,
			0x01 };
	public static byte BLOCK_NUM = 14;
	// public static final byte BLOCK_NONE = 0;
	// public static final byte BLOCK_1000 = 1;
	// public static final byte BLOCK_0100 = 2;
	// public static final byte BLOCK_0010 = 3;
	// public static final byte BLOCK_0001 = 4;
	// public static final byte BLOCK_0111 = 5;
	// public static final byte BLOCK_1011 = 6;
	// public static final byte BLOCK_1101 = 7;
	// public static final byte BLOCK_1110 = 8;
	// public static final byte BLOCK_1100 = 9;
	// public static final byte BLOCK_0110 = 10;
	// public static final byte BLOCK_0011 = 11;
	// public static final byte BLOCK_1001 = 12;
	public static final byte BLOCK_FULL = 13;
	// @Deprecated
	// public static int[][] HITMASK = {
	// { 0xff, 0xfe, 0xfc, 0xf8, 0xf0, 0xe0, 0xc0, 0x80, 0x00, 0x00, 0x00,
	// 0x00, 0x00, 0x00, 0x00, 0x00 },
	// { 0xff, 0x7f, 0x3f, 0x1f, 0x0f, 0x07, 0x03, 0x01, 0x00, 0x00, 0x00,
	// 0x00, 0x00, 0x00, 0x00, 0x00 },
	// { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x03, 0x07,
	// 0x0f, 0x1f, 0x3f, 0x7f, 0xff },
	// { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x80, 0xc0, 0xe0,
	// 0xf0, 0xf8, 0xfc, 0xfe, 0xff },
	//
	// { 0x01, 0x03, 0x07, 0x0f, 0x1f, 0x3f, 0x7f, 0xff, 0xff, 0xff, 0xff,
	// 0xff, 0xff, 0xff, 0xff, 0xff },
	// { 0x80, 0xc0, 0xe0, 0xf0, 0xf8, 0xfc, 0xfe, 0xff, 0xff, 0xff, 0xff,
	// 0xff, 0xff, 0xff, 0xff, 0xff },
	// { 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xfe, 0xfc,
	// 0xf8, 0xf0, 0xe0, 0xc0, 0x80 },
	// { 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0x7f, 0x3f,
	// 0x1f, 0x0f, 0x07, 0x03, 0x01 },
	//
	// { 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0x00, 0x00, 0x00,
	// 0x00, 0x00, 0x00, 0x00, 0x00 },
	// { 0x0f, 0x0f, 0x0f, 0x0f, 0x0f, 0x0f, 0x0f, 0x0f, 0x0f, 0x0f, 0x0f,
	// 0x0f, 0x0f, 0x0f, 0x0f, 0x0f },
	// { 0xff, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xff, 0xff,
	// 0xff, 0xff, 0xff, 0xff, 0xff },
	// { 0xf0, 0xf0, 0xf0, 0xf0, 0xf0, 0xf0, 0xf0, 0xf0, 0xf0, 0xf0, 0xf0,
	// 0xf0, 0xf0, 0xf0, 0xf0, 0xf0 } };

	// private static void generateHitMask(int bitNum) {
	// int i;
	// int bitNum2 = bitNum >> 1;
	// int bitNum4 = bitNum2 >> 1;
	// int full = 1 << bitNum2;
	// int full2 = 1 << bitNum4;
	//
	// BYTEMASK = new int[bitNum2];
	// for (i = 0; i < bitNum2; i++) {
	// BYTEMASK[i] = 1 << (bitNum2 - i - 1);
	// }
	//
	// HITMASK = new int[BLOCK_NUM - 2][bitNum];
	//
	// // 第一种
	// for (i = 0; i < bitNum2; i++) {
	// HITMASK[0][i] = full - (1 << i);
	// }
	// // 第二种
	// for (i = 0; i < bitNum2; i++) {
	// HITMASK[1][i] = (1 << (bitNum2 - i)) - 1;
	// }
	// // 第三种
	// for (i = 0; i < bitNum2; i++) {
	// HITMASK[2][bitNum2 + i] = (1 << (i + 1)) - 1;
	// }
	// // 第四种
	// for (i = 0; i < bitNum2; i++) {
	// HITMASK[3][bitNum2 + i] = full - (1 << (bitNum2 - i - 1));
	// }
	// // 第五种
	// for (i = 0; i < bitNum; i++) {
	// if (i < bitNum2)
	// HITMASK[4][i] = (1 << (i + 1)) - 1;
	// else
	// HITMASK[4][i] = full - 1;
	// }
	// // 第六种
	// for (i = 0; i < bitNum; i++) {
	// if (i < bitNum2)
	// HITMASK[5][i] = full - (1 << (bitNum2 - i - 1));
	// else
	// HITMASK[5][i] = full - 1;
	// }
	// // 第七种
	// for (i = 0; i < bitNum; i++) {
	// if (i < bitNum2)
	// HITMASK[6][bitNum2 + i] = full - (1 << i);
	// else
	// HITMASK[6][i - bitNum2] = full - 1;
	// }
	// // 第八种
	// for (i = 0; i < bitNum; i++) {
	// if (i < bitNum2)
	// HITMASK[7][bitNum2 + i] = (1 << (bitNum2 - i)) - 1;
	// else
	// HITMASK[7][i - bitNum2] = full - 1;
	// }
	// // 第九种
	// for (i = 0; i < bitNum2; i++) {
	// HITMASK[8][i] = full - 1;
	// }
	// // 第十种
	// for (i = 0; i < bitNum; i++) {
	// HITMASK[9][i] = full2 - 1;
	// }
	// // 第十一种
	// for (i = 0; i < bitNum2; i++) {
	// HITMASK[10][bitNum2 + i] = full - 1;
	// }
	// // 第十二种
	// for (i = 0; i < bitNum; i++) {
	// HITMASK[11][i] = full - full2;
	// }
	//
	// // for(i=0;i<HITMASK.length;i++)
	// // {
	// // for(j=0;j<bitNum;j++)
	// // {
	// // System.out.print("0x"+Integer.toHexString(HITMASK[i][j])+" , ");
	// // }
	// // System.out.println();
	// // }
	// }
	//
	// static {
	// generateHitMask(BaseConstants.GRID_WIDTH);
	// }
	// public static byte[][][] HITMASK={
	// {
	// {1,1,1,1,1,1,1,1},
	// {1,1,1,1,1,1,1,0},
	// {1,1,1,1,1,1,0,0},
	// {1,1,1,1,1,0,0,0},
	// {1,1,1,1,0,0,0,0},
	// {1,1,1,0,0,0,0,0},
	// {1,1,0,0,0,0,0,0},
	// {1,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0}
	// },
	// {
	// {1,1,1,1,1,1,1,1},
	// {0,1,1,1,1,1,1,1},
	// {0,0,1,1,1,1,1,1},
	// {0,0,0,1,1,1,1,1},
	// {0,0,0,0,1,1,1,1},
	// {0,0,0,0,0,1,1,1},
	// {0,0,0,0,0,0,1,1},
	// {0,0,0,0,0,0,0,1},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0}
	// },
	// {
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,1},
	// {0,0,0,0,0,0,1,1},
	// {0,0,0,0,0,1,1,1},
	// {0,0,0,0,1,1,1,1},
	// {0,0,0,1,1,1,1,1},
	// {0,0,1,1,1,1,1,1},
	// {0,1,1,1,1,1,1,1},
	// {1,1,1,1,1,1,1,1}
	// },
	// {
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {0,0,0,0,0,0,0,0},
	// {1,0,0,0,0,0,0,0},
	// {1,1,0,0,0,0,0,0},
	// {1,1,1,0,0,0,0,0},
	// {1,1,1,1,0,0,0,0},
	// {1,1,1,1,1,0,0,0},
	// {1,1,1,1,1,1,0,0},
	// {1,1,1,1,1,1,1,0},
	// {1,1,1,1,1,1,1,1}
	// },
	// {
	// {0,0,0,0,0,0,0,1},
	// {0,0,0,0,0,0,1,1},
	// {0,0,0,0,0,1,1,1},
	// {0,0,0,0,1,1,1,1},
	// {0,0,0,1,1,1,1,1},
	// {0,0,1,1,1,1,1,1},
	// {0,1,1,1,1,1,1,1},
	// {1,1,1,1,1,1,1,1},
	// {1,1,1,1,1,1,1,1},
	// {1,1,1,1,1,1,1,1},
	// {1,1,1,1,1,1,1,1},
	// {1,1,1,1,1,1,1,1},
	// {1,1,1,1,1,1,1,1},
	// {1,1,1,1,1,1,1,1},
	// {1,1,1,1,1,1,1,1},
	// {1,1,1,1,1,1,1,1}
	// },
	// {
	// {1,0,0,0,0,0,0,0},
	// {1,1,0,0,0,0,0,0},
	// {1,1,1,0,0,0,0,0},
	// {1,1,1,1,0,0,0,0},
	// {1,1,1,1,1,0,0,0},
	// {1,1,1,1,1,1,0,0},
	// {1,1,1,1,1,1,1,0},
	// {1,1,1,1,1,1,1,1},
	// {1,1,1,1,1,1,1,1},
	// {1,1,1,1,1,1,1,1},
	// {1,1,1,1,1,1,1,1},
	// {1,1,1,1,1,1,1,1},
	// {1,1,1,1,1,1,1,1},
	// {1,1,1,1,1,1,1,1},
	// {1,1,1,1,1,1,1,1},
	// {1,1,1,1,1,1,1,1}
	// },
	// };
	public static final int FP_LEVEL = 256;
	public static final int FP_LEVELB = 8;
	public static final int FP_ANGLE225_Y = (int) (FP_LEVEL / Math.sqrt(3));
	public static final int FP_ANGLE225_X = (int) (FP_LEVEL * 2 / Math.sqrt(3));

	// /**
	// * 场景帧调度任务执行时间间隔，单位为毫秒
	// */
	// public static final int milliSecondPerFrame = 100;
	// /**
	// * 每秒帧数
	// */
	// public static final int FRAMES_PER_SECOND = 1000 / milliSecondPerFrame;

	// /**
	// * 机型版本号
	// */
	// 00 NokiaN73 240*320
	// 01 NokiaN7370 240*320
	// 02 NokiaE62 320*240
	// 03 MotorolaV8 240*320
	// 04 MotorolaE2 240*320
	// 05 MotorolaE6 240*320
	// 06 SonyEricssonK790 240*320
	// 07 SonyEricssonW958 240*320
	// 08 DopodS1 240*320
	// 09 MotorolaE680 240*320
	// 10 SonyEricssonK700 176*208
	// 11 Nokia7610 176*208
	// 12 NokiaX800 176*208
	// 13 NokiaN5500 208*208
	// 14 MotorolaE398 176*208
	// 15 MotorolaL7 176*208
	// 16 MotorolaK1 176*208
	// 17 SonyEricssonK506 128*160
	// 18 SonyEricssonK300 128*128
	// 19 MotorolaL6 128*149
	// 20 SonyEricssonW810 176*220
	public static final byte CLIENT_NokiaN73 = 0;
	public static final byte CLIENT_NokiaN7370 = 1;
	public static final byte CLIENT_NokiaE62 = 2;
	public static final byte CLIENT_MotorolaV8 = 3;
	public static final byte CLIENT_MotorolaE2 = 3;
	public static final byte CLIENT_MotorolaE6 = 3;
	public static final byte CLIENT_SonyEricssonK790 = 3;
	public static final byte CLIENT_SonyEricssonW958 = 3;
	public static final byte CLIENT_DopodS1 = 3;
	// public static final byte CLIENT_MotorolaV8=3;
	// public static final byte CLIENT_MotorolaV8=3;
	// public static final byte CLIENT_MotorolaV8=3;
	// public static final byte CLIENT_MotorolaV8=3;
	// public static final byte CLIENT_MotorolaV8=3;
	// public static final byte CLIENT_MotorolaV8=3;
	// public static final byte CLIENT_MotorolaV8=3;
	// public static final byte CLIENT_MotorolaV8=3;
	// public static final byte CLIENT_MotorolaV8=3;

	public static final byte CLIENT_SCREENTYPE_240X320 = 0;
	public static final byte CLIENT_SCREENTYPE_176X208 = 1;
	public static final byte CLIENT_SCREENTYPE_208X208 = 2;
	public static final byte CLIENT_SCREENTYPE_320X240 = 3;
	/**
	 * 客户端屏幕类型参数 0:屏幕宽 1:屏幕高 2:场景窗口宽(世界坐标) 3:场景窗口高(世界坐标)
	 */
	public static final short[][] CLIENT_SCREEN_PROPERTY = {
			{ 240, 320, 240, 294 }, { 176, 208, 176 * 4 / 3, 183 * 4 / 3 },
			{ 208, 208, 208 * 4 / 3, 182 * 4 / 3 }, { 320, 240, 320, 214 } };

	public static final byte CLIENT_RESOURCETYPE_240 = 0;
	public static final byte CLIENT_RESOURCETYPE_176 = 1;
	public static final byte CLIENT_RESOURCETYPE_800 = 2;
	public static final byte CLIENT_RESOURCETYPE_NUM = 3;
	/**
	 * 客户端终端属性 [屏幕类型,资源类型]
	 */
	public static final byte[][] CLIENT_PROPERTY = {
			// CLIENT_NOKIA_N73
			{ 0, 0 },
			// CLIENT_MOTOROLA_A1200
			{ 0, 0 },
			// CLIENT_NOKIA_7610
			{ 1, 1 },
			// CLIENT_SE_W800
			{ 1, 1 } };

	public static final int randomInt(int max) {
		if (max == 0)
			return 0;
		return random.nextInt(max);
	}

	/**
	 * 产生一个from -> to之间的整数， 包括from，但不包括to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public static final int random(int from, int to) {
		if (from >= to)
			return from;

		return random.nextInt(to - from) + from;
	}

	public static final boolean isRandomIn(int max, int value) {
		return randomInt(max) < value;
	}

	public static final Object loadClassByName(String cn) {
		try {
			Object b = Class.forName(cn).newInstance();
			return b;
		} catch (Exception e) {
			logger.error("加载类失败",e);
		}
		return null;
	}

	public static final boolean isInRadius(int dx, int dy, int sx, int sy,
			float radius) {
		int x = dx - sx;
		int y = dy - sy;

		return (x * x + y * y) <= (radius * radius);
		// int dl = (int) Math.sqrt(x * x + y * y);
		// if (dl <= radius)
		// return true;
		// return false;
	}

	public static final boolean isInRadiusFast(int dx, int dy, int sx, int sy,
			int radius) {
		int x = dx - sx;
		int y = dy - sy;

		return (Math.abs(x) + Math.abs(y)) <= radius;
	}
	
	
	public static final int randomIndex(int[] percents)
	{
		List<Integer> lst = new ArrayList<Integer>();
		for(int p : percents)
			lst.add(p);
		return randomIndex(lst);
	}

	
	public static final int randomIndex(List<Integer> percentList)
	{
		if(percentList == null ||percentList.size() == 0)
			return -1;
		
		int total = 0;
		for(int percent:percentList)
			total += percent;
		
		int tmpResult = random.nextInt(total);
		
		int tmpAdded = 0;
		int tmpIndex = -1;
		for(int percent:percentList)
		{
			++tmpIndex;
			tmpAdded+= percent;
			if(tmpResult < tmpAdded)
				return tmpIndex;
		}
		
		return -1;
	}

	// /**
	// * 据根方向向量计算面向方向
	// *
	// * @param x
	// * 方向向量X分量
	// * @param y
	// * 方向向量Y分量
	// * @return
	// */
	// public static byte getFaceDirection(int x, int y) {
	// if(x==0 && y==0)
	// return -1;
	//
	// return getFaceDirection((float)x, (float)y);
	// int absX = Math.abs(x);
	// int absY = Math.abs(y);
	//
	// // 对角线方向
	// if (absY == absX) {
	// if (y > 0) {
	// if (x > 0)
	// return BaseConstants.DIR_DOWNRIGHT;
	// else
	// return BaseConstants.DIR_DOWNLEFT;
	// } else {
	// if (x > 0)
	// return BaseConstants.DIR_UPRIGHT;
	// else
	// return BaseConstants.DIR_UPLEFT;
	// }
	//
	// }
	//
	// // 偏向Y轴
	// if (absY > absX) {
	// // 计算Y / X以判断偏向Y坐标轴还是偏向对角线
	// boolean isYAxis = (absX == 0)
	// || ((float) absY / (float) absX) > 2;
	//
	// if (y > 0) {
	// if (isYAxis) // 更偏向Y轴
	// return BaseConstants.DIR_DOWN;
	// else // 更偏向对角线
	// {
	// if (x > 0)
	// return BaseConstants.DIR_DOWNRIGHT;
	// else
	// return BaseConstants.DIR_DOWNLEFT;
	// }
	// } else {
	// if (isYAxis) // 更偏向Y轴
	// return BaseConstants.DIR_UP;
	// else // 更偏向对角线
	// {
	// if (x > 0)
	// return BaseConstants.DIR_UPRIGHT;
	// else
	// return BaseConstants.DIR_UPLEFT;
	// }
	// }
	// } else // 偏向X轴
	// {
	// // 计算X / Y以判断偏向X坐标轴还是偏向对角线
	// boolean isXAxis = (absY == 0)
	// || ((float) absX / (float) absY) > 2f;
	//
	// if (x > 0) {
	// if (isXAxis)
	// return BaseConstants.DIR_RIGHT;
	// else {
	// if (y > 0)
	// return BaseConstants.DIR_DOWNRIGHT;
	// else
	// return BaseConstants.DIR_UPRIGHT;
	// }
	// } else {
	// if (isXAxis)
	// return BaseConstants.DIR_LEFT;
	// else {
	// if (y > 0)
	// return BaseConstants.DIR_DOWNLEFT;
	// else
	// return BaseConstants.DIR_UPLEFT;
	// }
	// }
	// }
	// }

	// /**
	// * 获取面对面的方向
	// *
	// * @param dir
	// * @return
	// */
	// public static byte getReverseDirection(byte dir) {
	// return BaseConstants.ReverseDirs[dir];
	// }

	/**
	 * 在0~1000内随机概率
	 * 
	 * @param prob
	 * @return
	 */
	public static final boolean probability(int prob) {
		if (prob >= 10000)
			return true;
		if (prob == 0)
			return false;
		if (random.nextInt(10000) <= prob)
			return true;
		return false;
	}

	// public static final int random(int total)
	// {
	// return random.nextInt(total);
	// }
	/**
	 * 在指定范围内随机概率
	 * 
	 * @param prob
	 * @param total
	 * @return
	 */
	public static final boolean probability(int prob, int total) {
		if (prob >= total)
			return true;
		if (prob == 0)
			return false;
		if (random.nextInt(total) <= prob)
			return true;
		return false;
	}



	/**
	 * 获取分页
	 * 
	 * @param <T>
	 *            集合元素类型
	 * @param all
	 *            集合
	 * @param rowsPerPage
	 *            每页行数
	 * @param pageIndex
	 *            页索引(从0开始)
	 * @return
	 */
	public static <T> List<T> getPage(List<T> all, int rowsPerPage,
			int pageIndex) {
		final int pages = getPages(all, rowsPerPage);
		if (pages == 0)
			return Collections.emptyList();

		if (pages == 1)
			return all;

		final int begin = rowsPerPage * pageIndex;
		final int end = begin + rowsPerPage;
		return all.subList(begin, end);
	}

	/**
	 * 获取分页
	 * 
	 * @param <T>
	 *            集合元素类型
	 * @param all
	 *            集合
	 * @param pages
	 *            总页数
	 * @param rowsPerPage
	 *            每页行数
	 * @param pageIndex
	 *            页索引(从0开始)
	 * @return
	 */
	public static <T> List<T> getPage(List<T> all, int pages, int rowsPerPage,
			int pageIndex) {
		if (pages == 0)
			return Collections.emptyList();

		if (pages == 1)
			return all;

		final int begin = rowsPerPage * pageIndex;
		final int end = begin + rowsPerPage;
		return all.subList(begin, end);
	}

	/**
	 * 获取总页数
	 * 
	 * @param all
	 * @param rowsPerPage
	 *            每页行数
	 * @return
	 */
	public static int getPages(List<?> all, int rowsPerPage) {
		if (rowsPerPage <= 0)
			return 0;

		final int size = all.size();
		if (size == 0)
			return 0;

		int pages = size / rowsPerPage;
		if (size % rowsPerPage > 0)
			pages++;
		return pages;
	}

	public static int setBitValue(int data, int num, boolean value) {
		if (value) {
			return data | (1 << num);
		} else {
			return data & (~(1 << num));
		}
	}

	public static int getBitValue(int data, int num) {
		return (data >> num) & 1;
	}

	public static boolean getBitBooleanValue(int data, int num) {
		int i = getBitValue(data, num);
		return i == 1 ? true : false;
	}

	public static String toBitStr(int i) {
		String s = Integer.toBinaryString(i);
		int fillZeros = 32 - s.length();
		StringBuffer sz = new StringBuffer();
		for (int j = 0; j < fillZeros; j++) {
			sz.append("0");
		}
		sz.append(s);
		return sz.toString();
	}

	public static boolean calculateProbability(int probability) {
		return calculateProbability(10000, probability);
	}

	public static boolean calculateProbability(int max, int probability) {
		int randomNum = randomInt(max);
		if (randomNum <= probability) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据概率表计算获得索引
	 * 
	 * @param probabilityTable
	 * <br>
	 *            按照表中概率加总计算概率(概率表中的总概率相加最好是10000)<br>
	 *            例如:<code>int[] { 1000, 3000, 2000, 4000 };</code>
	 * 
	 * @return 表中概率的索引(-1:计算出错)
	 */
	public static int getProbabilityIndexByTable(int[] table) {

		// 概率加总
		int sumProb = 0;
		for (int i = 0; i < table.length; i++) {
			sumProb += table[i];
		}

		// 随机概率
		int probability = randomInt(sumProb);

		for (int i = 0; i < table.length; i++) {

			// 计算概率区间
			int min = 0;
			for (int j = 0; j < i; j++) {
				min += table[j];
			}
			int max = 0;
			for (int j = 0; j <= i; j++) {
				max += table[j];
			}

			// float prob = (float)(max - min) / sumProb;
			// System.out.println("index :" + i + " [" + min + " - " + max +
			// "] probability:" + probability);

			if (probability >= min && probability < max) {
			}
			return i;
		}
		return -1;
	}
}
