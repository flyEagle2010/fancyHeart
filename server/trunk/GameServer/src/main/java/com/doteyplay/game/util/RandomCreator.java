package com.doteyplay.game.util;

import com.doteyplay.game.CommonConstants;

public class RandomCreator
{
	private final static long multiplier = 0x5DEECE66DL;
	private final static long addend = 0xBL;
	private final static long mask = (1L << 48) - 1;
	private final static int INT_MAX_VALUE = 0x7fffffff;

	private long seed;

	public RandomCreator(long seed)
	{
		this.seed = (seed ^ multiplier) & mask;
	}

	/**
	 * 在0-n的范围内随机一个int
	 * @param n
	 * @return
	 */
	public int nextInt(int n)
	{
		if (n <= 0)
			throw new IllegalArgumentException("n must be positive");

		if ((n & -n) == n) // i.e., n is a power of 2
			return (int) ((n * (long) next(31)) >> 31);

		long bits, val;
		do
		{
			bits = next(31);
			val = bits % n;
		} while (bits - val + (n - 1) < 0);
		return (int) val;
	}

	/**
	 * 在int最大值范围内随机一个int
	 * @return
	 */
	public int nextInt()
	{
		int n = INT_MAX_VALUE;
		return this.nextInt(n);
	}

	protected int next(int bits)
	{
		long nextseed;
		nextseed = (this.seed * multiplier + addend) & mask;
		if (this.seed != nextseed)
			this.seed = nextseed;
		return (int) (nextseed >>> (48 - bits));
	}


	private int[] createRandomDeckArray()
	{
		int[] array = new int[CommonConstants.DECK_NUM];
		for (int i = 0; i < CommonConstants.DECK_NUM; i++)
		{
			array[i] = this.nextInt(CommonConstants.DECK_NUM);
		}
		return array;
	}
}
