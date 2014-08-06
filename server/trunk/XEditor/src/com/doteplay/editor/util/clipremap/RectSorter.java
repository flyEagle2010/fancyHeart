package com.doteplay.editor.util.clipremap;

import java.util.AbstractList;
import java.util.Collections;
import java.util.Comparator;

public final class RectSorter
{
	public static final int SORT_BY_ID = -1;
	public static final int SORT_BY_WIDTH = 0;
	public static final int SORT_BY_HEIGHT = 1;
	public static final int SORT_BY_AREA = 2;
	public static final int SORT_BY_SQUARENESS = 3;

	private RectSorter()
	{
		
	}

	public static final void sort(final AbstractList<Rect> list, final int type)
	{
		switch (type)
		{
			case SORT_BY_ID:
				Collections.sort(list, new SorterID());
				break;

			case SORT_BY_WIDTH:
				Collections.sort(list, new SorterWidth());
				break;

			case SORT_BY_HEIGHT:
				Collections.sort(list, new SorterHeight());
				break;

			case SORT_BY_AREA:
				Collections.sort(list, new SorterArea());
				break;

			case SORT_BY_SQUARENESS:
				Collections.sort(list, new SorterSquareness());
				break;
		}
	}

	private static final class SorterID implements Comparator<Rect>
	{
		@Override
		public int compare(final Rect r0, final Rect r1) //…˝√›≈≈¡–
		{
			if (r0.id < r1.id)
				return -1;
			if (r0.id > r1.id)
				return 1;
			return 0;
		}
	}

	private static final class SorterWidth implements Comparator<Rect>
	{
		@Override
		public int compare(final Rect r0, final Rect r1) //Ωµ√›≈≈¡–
		{
			if (r0.w < r1.w)
				return 1;
			if (r0.w > r1.w)
				return -1;
			if (r0.h < r1.h)
				return 1;
			if (r0.h > r1.h)
				return -1;
			return 0;
		}
	}

	private static final class SorterHeight implements Comparator<Rect>
	{
		@Override
		public int compare(final Rect r0, final Rect r1) //Ωµ√›≈≈¡–
		{
			if (r0.h < r1.h)
				return 1;
			if (r0.h > r1.h)
				return -1;
			if (r0.w < r1.w)
				return 1;
			if (r0.w > r1.w)
				return -1;
			return 0;
		}
	}

	private static final class SorterArea implements Comparator<Rect>
	{
		@Override
		public int compare(final Rect r0, final Rect r1) //Ωµ√›≈≈¡–
		{
			int area_0 = r0.w * r0.h;
			int area_1 = r1.w * r1.h;
			if (area_0 < area_1)
				return 1;
			if (area_0 > area_1)
				return -1;
			if (r0.h < r1.h)
				return 1;
			if (r0.h > r1.h)
				return -1;
			if (r0.w < r1.w)
				return 1;
			if (r0.w > r1.w)
				return -1;
			return 0;
		}
	}

	private static final class SorterSquareness implements Comparator<Rect>
	{
		@Override
		public int compare(final Rect r0, final Rect r1) //Ωµ√›≈≈¡–
		{
			int xsq = Math.abs(r0.w - r0.h);
			int ysq = Math.abs(r1.w - r1.h);
			if (xsq < ysq)
				return -1;
			if (xsq > ysq)
				return 1;
			int area_0 = r0.w * r0.h;
			int area_1 = r1.w * r1.h;
			if (area_0 < area_1)
				return 1;
			if (area_0 > area_1)
				return -1;
			if (r0.h < r1.h)
				return 1;
			if (r0.h > r1.h)
				return -1;
			if (r0.w < r1.w)
				return 1;
			if (r0.w > r1.w)
				return -1;
			return 0;
		}
	}
}