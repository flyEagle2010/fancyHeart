package com.doteyplay.game.util.excel;

import java.util.ArrayList;
import java.util.List;

public interface IndexedEnum
{
	public static class IndexedEnumUtil
	{

		private static final int WORNNING_MAX_INDEX = 1000;

		public static List toIndexes(IndexedEnum enums[])
		{
			int maxIndex = 0x80000000;
			int curIdx = 0;
			IndexedEnum arr$[] = enums;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				IndexedEnum enm = arr$[i$];
				curIdx = enm.getIndex();
				if (curIdx > maxIndex)
					maxIndex = curIdx;
			}

			List instances = new ArrayList(maxIndex + 1);
			for (int i = 0; i < maxIndex + 1; i++)
				instances.add(null);

			for (int i$ = 0; i$ < len$; i$++)
			{
				IndexedEnum enm = arr$[i$];
				curIdx = enm.getIndex();
				instances.set(curIdx, enm);
			}

			return instances;
		}

		public IndexedEnumUtil()
		{
		}
	}

	public abstract int getIndex();
}
