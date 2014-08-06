package com.doteyplay.utils;

public class BytesUtils
{
	private static int bytesIndexOf(byte[] source, byte[] target)
	{
		return bytesIndexOf(source, 0, source.length, target, 0, target.length, 0);
	}

	private static int bytesIndexOf(byte[] source, byte[] target, int fromIndex)
	{
		return bytesIndexOf(source, 0, source.length, target, 0, target.length, fromIndex);
	}

	private static int bytesIndexOf(byte[] source, int sourceOffset, int sourceCount, byte[] target,
			int targetOffset, int targetCount, int fromIndex)
	{
		if (fromIndex >= sourceCount)
		{
			return (targetCount == 0 ? sourceCount : -1);
		}
		if (fromIndex < 0)
		{
			fromIndex = 0;
		}
		if (targetCount == 0)
		{
			return fromIndex;
		}

		byte first = target[targetOffset];
		int max = sourceOffset + (sourceCount - targetCount);

		for (int i = sourceOffset + fromIndex; i <= max; i++)
		{
			/* Look for first character. */
			if (source[i] != first)
			{
				while (++i <= max && source[i] != first)
					;
			}

			/* Found first character, now look at the rest of v2 */
			if (i <= max)
			{
				int j = i + 1;
				int end = j + targetCount - 1;
				for (int k = targetOffset + 1; j < end && source[j] == target[k]; j++, k++)
					;

				if (j == end)
				{
					/* Found whole string. */
					return i - sourceOffset;
				}
			}
		}
		return -1;
	}

	private static int bytesLastIndexOf(byte[] source, byte[] target)
	{
		return bytesLastIndexOf(source, 0, source.length, target, 0, target.length, source.length);
	}

	private static int bytesLastIndexOf(byte[] source, byte[] target, int fromIndex)
	{
		return bytesLastIndexOf(source, 0, source.length, target, 0, target.length, fromIndex);
	}

	private static int bytesLastIndexOf(byte[] source, int sourceOffset, int sourceCount,
			byte[] target, int targetOffset, int targetCount, int fromIndex)
	{
		/*
		 * Check arguments; return immediately where possible. For consistency,
		 * don't check for null str.
		 */
		int rightIndex = sourceCount - targetCount;
		if (fromIndex < 0)
		{
			return -1;
		}
		if (fromIndex > rightIndex)
		{
			fromIndex = rightIndex;
		}
		/* Empty string always matches. */
		if (targetCount == 0)
		{
			return fromIndex;
		}

		int strLastIndex = targetOffset + targetCount - 1;
		byte strLastChar = target[strLastIndex];
		int min = sourceOffset + targetCount - 1;
		int i = min + fromIndex;

		startSearchForLastChar: while (true)
		{
			while (i >= min && source[i] != strLastChar)
			{
				i--;
			}
			if (i < min)
			{
				return -1;
			}
			int j = i - 1;
			int start = j - (targetCount - 1);
			int k = strLastIndex - 1;

			while (j > start)
			{
				if (source[j--] != target[k--])
				{
					i--;
					continue startSearchForLastChar;
				}
			}
			return start - sourceOffset + 1;
		}
	}
}
