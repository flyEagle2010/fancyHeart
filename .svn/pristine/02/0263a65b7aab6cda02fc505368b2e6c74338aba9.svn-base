package com.doteyplay.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

public class AsstUtils
{
	private static Random random = new Random(System.currentTimeMillis());

	/**
	 * �ַ��Ƿ�Ϊ�ջ�null
	 * 
	 * @param param
	 * @return
	 */
	public static boolean empty(String param)
	{
		return param == null || param.trim().length() < 1;
	}

	/**
	 * ���ؿմ�
	 * 
	 * @param param
	 * @return
	 */
	public static String nvl(String param)
	{
		return param != null ? param : "";
	}

	public static int parseint(String param)
	{
		return parseint(param, 0);
	}

	/**
	 * ��һ���ַ�ת��Ϊint,�ṩһ��ʧ��ʱ��ȱʡֵ
	 * 
	 * @param param
	 * @param dftValue
	 * @return
	 */
	public static int parseint(String param, int dftValue)
	{
		int i = dftValue;
		try
		{
			if (!empty(param))
				i = Integer.parseInt(param);
		}
		catch (Exception exception)
		{
		}
		return i;
	}

	public static byte parsebyte(String param)
	{
		return parsebyte(param, (byte) 0);
	}

	public static Date getDate(int hour, int minute, int second, int milliSecond)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.MILLISECOND, milliSecond);
		Date date = new Date(cal.getTimeInMillis());
		try
		{
			return format.parse(format.format(date));

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ��һ���ַ�ת��Ϊbyte,�ṩһ��ʧ��ʱ��ȱʡֵ
	 * 
	 * @param param
	 * @param dftValue
	 * @return
	 */
	public static byte parsebyte(String param, byte dftValue)
	{
		byte i = dftValue;
		try
		{
			if (!empty(param))
				i = Byte.parseByte(param);
		}
		catch (Exception exception)
		{
		}
		return i;
	}

	public static short parseshort(String param)
	{
		return parseshort(param, (short) 0);
	}

	/**
	 * ��һ���ַ�ת��Ϊbyte,�ṩһ��ʧ��ʱ��ȱʡֵ
	 * 
	 * @param param
	 * @param dftValue
	 * @return
	 */
	public static short parseshort(String param, short dftValue)
	{
		short i = dftValue;
		try
		{
			if (!empty(param))
				i = Short.parseShort(param);
		}
		catch (Exception exception)
		{
		}
		return i;
	}

	public static double parsedouble(String param)
	{
		return parsedouble(param, 0.0);
	}

	public static double parsedouble(String param, double dftValue)
	{
		double df = dftValue;
		try
		{
			if (!empty(param))
				df = Double.parseDouble(param);
		}
		catch (Exception exception)
		{
		}
		return df;
	}

	public static float parsefloat(String param)
	{
		return parsefloat(param, (float) 0.0);
	}

	public static float parsefloat(String param, float dftValue)
	{
		float df = dftValue;
		try
		{
			if (!empty(param))
				df = Float.parseFloat(param);
		}
		catch (Exception exception)
		{
		}
		return df;
	}

	public static long parselong(String param, long dftValue)
	{
		long l = dftValue;
		try
		{
			if (!empty(param))
				l = Long.parseLong(param);
		}
		catch (Exception exception)
		{
		}
		return l;
	}

	public static long parselong(String param)
	{
		long l = 0L;
		try
		{
			if (!empty(param))
				l = Long.parseLong(param);
		}
		catch (Exception exception)
		{
		}
		return l;
	}

	public static boolean parseboolean(String param)
	{
		return parseboolean(param, false);
	}

	public static boolean parseboolean(String param, boolean dftvalue)
	{
		if (empty(param))
			return dftvalue;
		switch (param.charAt(0))
		{
			case 49: // '1'
			case 84: // 'T'
			case 89: // 'Y'
			case 116: // 't'
			case 121: // 'y'
				return true;
			default:
				return false;
		}
	}

	public static String doubleToStr(double value, String format)
	{
		if (format == null || format.length() == 0)
			format = "#0.00";
		DecimalFormat sdf = new DecimalFormat(format);
		return sdf.format(value);
	}

	public static String doubleToStr(double value, int length)
	{
		if (length < 0)
			length = 2;
		if (length > 10)
			length = 10;

		if (length <= 0)
			return doubleToStr(value, "#0");
		else
		{
			StringBuffer tmpFormat = new StringBuffer("#0.");
			for (int i = 0; i < length; i++)
				tmpFormat.append("0");
			return doubleToStr(value, tmpFormat.toString());
		}
	}

	public static int fastSearch(int[] a, int value)
	{
		if (a != null)
		{
			int low = 0;
			int high = a.length - 1;
			int mid;

			while (low <= high)
			{
				mid = low >> 1 + high >> 1;
				if (a[mid] == value)
					return mid;
				else if (a[mid] > value)
					high = mid - 1;
				else
					low = mid + 1;
			}
		}
		return -1;
	}

	public static String[] simpleSplit(String str, String separatorChars)
	{
		if (str == null || str.length() == 0)
			return new String[0];
		if (separatorChars == null || separatorChars.length() == 0)
			return new String[] { str };

		List list = new ArrayList();
		char[] tmpSpChars = separatorChars.toCharArray();
		int len = str.length();
		int i = 0;
		int j = 0;
		int start = 0;
		char currentChar = 0;
		boolean match = false;
		boolean found = false;
		while (i < len)
		{
			currentChar = str.charAt(i);
			found = false;
			for (j = 0; (!found) && j < tmpSpChars.length; j++)
			{
				found = (currentChar == tmpSpChars[j]);
			}
			if (found)
			{
				if (match)
				{
					list.add(str.substring(start, i));
					match = false;
				}
				start = ++i;
			}
			else
			{
				match = true;
				i++;
			}
		}
		if (match)
			list.add(str.substring(start, i));
		return (String[]) list.toArray(new String[list.size()]);
	}

	public static final int randomInt(int max)
	{
		if (max == 0)
			return 0;
		return random.nextInt(max);
	}

	public static final int random(int from, int to)
	{
		if (from >= to)
			return from;

		return random.nextInt(to - from) + from;
	}

	public static String dateToStr(Date datetime, String pattern)
	{
		if (datetime != null)
		{
			SimpleDateFormat sdf = null;
			if (pattern == null || pattern.length() == 0)
				sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			else
				sdf = new SimpleDateFormat(pattern);
			return sdf.format(datetime);
		}
		else
			return "";
	}
	
	public static Date parseDateTime(String dateStr, String pattern) {
        return parseDateTime(dateStr, pattern, new Date(System.currentTimeMillis()));
    }

    public static Date parseDateTime(String dateStr, String pattern, Date dftDate) {
        Date result = dftDate;
        if (dateStr == null)
            return result;

        try {
        	SimpleDateFormat formatter = new SimpleDateFormat((pattern != null) ? pattern : "yyyy-MM-dd hh:mm:ss");
            result = formatter.parse(dateStr);
        } catch (Exception e) {
            result = dftDate;
        }
        return result;
    }

    
    public static int getIntParam(HttpServletRequest request,String paramName, int defValue)
    {
    	try
    	{
    		int value = Integer.parseInt(request.getParameter(paramName));
    		return value;
    	}
    	catch (Exception e) 
    	{
    		return defValue;
		}
    }
}
