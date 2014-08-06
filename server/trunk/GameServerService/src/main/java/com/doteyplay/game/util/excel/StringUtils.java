package com.doteyplay.game.util.excel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class StringUtils
{
	private static class BaseReflectionToStringBuilder extends
			ReflectionToStringBuilder
	{

		private final Map excludes;

		protected boolean accept(Field field)
		{
			boolean _accepted = true;
			if (excludes != null)
				_accepted = excludes.get(field.getName()) == null;
			return super.accept(field) && _accepted;
		}

		public BaseReflectionToStringBuilder(Object object,
				ToStringStyle style, Map excludes)
		{
			super(object, style);
			this.excludes = excludes;
		}
	}

	public StringUtils()
	{
	}

	public static String removeSpaces(String str)
	{
		return removeRegex(str, "[\\ ]");
	}

	public static String removeNonNumeric(String str)
	{
		return removeRegex(str, "[\\D]");
	}

	public static String removeNumeric(String str)
	{
		return removeRegex(str, "[\\d]");
	}

	private static String removeRegex(String str, String regex)
	{
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.replaceAll("");
	}

	public static String trim(String str)
	{
		if (str == null)
			str = "";
		else
			str = str.trim();
		if (str.length() == 0)
			return str;
		if (str.charAt(0) == '"')
			str = str.substring(1);
		if (str.charAt(str.length() - 1) == '"')
			str = str.substring(0, str.length() - 1);
		return str;
	}

	public static String[] getStringList(String str)
	{
		str = trim(str);
		if (str.endsWith(","))
			str = str.substring(0, str.length() - 1);
		String sep = ",";
		if (str.indexOf(':') >= 0)
			sep = ":";
		return str.split(sep);
	}

	public static String[] getStringList(String str, String sep)
	{
		str = trim(str);
		return str.split(sep);
	}

	public static int[] getIntArray(String str, String sep)
	{
		String prop[] = getStringList(str, sep);
		List tmp = new ArrayList();
		for (int i = 0; i < prop.length; i++)
			try
			{
				int r = Integer.parseInt(prop[i]);
				tmp.add(Integer.valueOf(r));
			} catch (Exception e)
			{
				throw new RuntimeException(e);
			}

		int ints[] = new int[tmp.size()];
		for (int i = 0; i < tmp.size(); i++)
			ints[i] = ((Integer) tmp.get(i)).intValue();

		return ints;
	}

	public static List getIntList(String str, String sep)
	{
		List tmp = new ArrayList();
		if (str == null || str.trim().equals(""))
			return tmp;
		String prop[] = getStringList(str, sep);
		for (int i = 0; i < prop.length; i++)
			try
			{
				int r = Integer.parseInt(prop[i]);
				tmp.add(Integer.valueOf(r));
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		return tmp;
	}

	public static String join(String strs[], String sep)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append(strs[0]);
		for (int i = 1; i < strs.length; i++)
			buffer.append(sep).append(strs[i]);

		return buffer.toString();
	}

	public static String join(List ints, String sep)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(ints.get(0));
		for (int i = 1; i < ints.size(); i++)
			sb.append(sep).append(ints.get(i));

		return sb.toString();
	}

	public static double[] getDoubleList(String str)
	{
		String prop[] = getStringList(str);
		double ds[] = new double[prop.length];
		for (int i = 0; i < ds.length; i++)
			ds[i] = Double.parseDouble(prop[i]);

		return ds;
	}

	public static List getListBySplit(String str, String split)
	{
		List list = new ArrayList();
		if (str == null || str.trim().equalsIgnoreCase(""))
			return null;
		String strs[] = str.split(split);
		String arr$[] = strs;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			String temp = arr$[i$];
			if (temp != null && !temp.trim().equalsIgnoreCase(""))
				list.add(temp.trim());
		}

		return list;
	}

	public static int[] getIntList(String str)
	{
		String prop[] = getStringList(str);
		List tmp = new ArrayList();
		for (int i = 0; i < prop.length; i++)
			try
			{
				String sInt = prop[i].trim();
				if (sInt.length() < 20)
				{
					int r = Integer.parseInt(prop[i].trim());
					tmp.add(Integer.valueOf(r));
				}
			} catch (Exception e)
			{
			}

		int ints[] = new int[tmp.size()];
		for (int i = 0; i < tmp.size(); i++)
			ints[i] = ((Integer) tmp.get(i)).intValue();

		return ints;
	}

	public static String toWrapString(Object obj, String content)
	{
		if (obj == null)
			return "null";
		else
			return (new StringBuilder()).append(obj.getClass().getName())
					.append("@").append(obj.hashCode()).append("[\r\n")
					.append(content).append("\r\n]").toString();
	}

	public static BitSet bitSetFromString(String str)
	{
		if (str == null)
			return new BitSet();
		if (str.startsWith("{"))
			str = str.substring(1);
		if (str.endsWith("}"))
			str = str.substring(0, str.length() - 1);
		int ints[] = getIntList(str);
		BitSet bs = new BitSet();
		int arr$[] = ints;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			int i = arr$[i$];
			bs.set(i);
		}

		return bs;
	}

	public static boolean hasExcludeChar(String str)
	{
		if (str != null)
		{
			char chs[] = str.toCharArray();
			for (int i = 0; i < chs.length; i++)
				if (Character.getType(chs[i]) == 18)
					return true;

		}
		return false;
	}

	public static String replaceSql(String str)
	{
		if (str != null)
			return str.replaceAll("'", "¡¯").replaceAll("<", "&lt;")
					.replaceAll(">", "&gt;").replaceAll("\"", "&quot;");
		else
			return "";
	}

	public static boolean isEquals(String s1, String s2)
	{
		if (s1 != null)
			return s1.equals(s2);
		return s2 == null;
	}

	public static String obj2String(Object obj, Map excludes)
	{
		BaseReflectionToStringBuilder _builder = new BaseReflectionToStringBuilder(
				obj, ToStringStyle.SHORT_PREFIX_STYLE, excludes);
		return _builder.toString();
	}

	public static boolean isDigit(String text)
	{
		String reg = "[-]*[\\d]+[\\.\\d+]*";
		Pattern pat = Pattern.compile(reg);
		Matcher mat = pat.matcher(text);
		return mat.matches();
	}

	public static boolean isChiness(String text)
	{
		String reg = "[\\w]*[\\u4e00-\\u9fa5]+[\\w]*";
		Pattern pat = Pattern.compile(reg);
		Matcher mat = pat.matcher(text);
		boolean result = mat.matches();
		return result;
	}

	public static boolean isChineseChar(char cha)
	{
		String reg = "[\\u4e00-\\u9fa5]";
		Pattern pat = Pattern.compile(reg);
		String text = Character.toString(cha);
		Matcher mat = pat.matcher(text);
		boolean result = mat.matches();
		return result;
	}

	public static boolean isLetterAndDigit(String cha)
	{
		String reg = "[\\w]+";
		Pattern pat = Pattern.compile(reg);
		Matcher mat = pat.matcher(cha);
		boolean result = mat.matches();
		return result;
	}

	public static int getChineseCount(String test)
	{
		int count = 0;
		boolean tempResult = false;
		for (int i = 0; i < test.length(); i++)
		{
			char cha = test.charAt(i);
			tempResult = isChinese(cha);
			if (tempResult)
				count++;
		}

		return count;
	}

	public static int getLetterAndDigitCount(String text)
	{
		int count = 0;
		boolean tempResult = false;
		for (int i = 0; i < text.length(); i++)
		{
			tempResult = isLetterAndDigit(text);
			if (tempResult)
				count++;
		}

		return count;
	}

	public static boolean isEmpty(String str)
	{
		return str == null || str.trim().length() == 0;
	}

	public static String upperCaseFirstCharOnly(String s)
	{
		if (s == null || s.length() < 1)
			return s;
		else
			return (new StringBuilder())
					.append(s.substring(0, 1).toUpperCase())
					.append(s.substring(1).toLowerCase()).toString();
	}

	public static boolean isChinese(char c)
	{
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION;
	}

	public static boolean isValid(String content)
	{
		return content != null ? !content.equals("") : false;
	}

	public static int atLeastOneValidIdx(String array[])
	{
		if (array == null || array.length == 0)
			return -1;
		int i = 0;
		String arr$[] = array;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			String s = arr$[i$];
			if (isValid(s))
				return i;
			i++;
		}

		return -1;
	}
}
