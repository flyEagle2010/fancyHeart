package com.doteyplay.utils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class RandomCode
{

	final int key = 43;
	final String error_format_int = "format of color is not rgb.sample \"212|232|0\"";
	final String error_color_input = "format of color(num|num|num): num in 0-255";

	/** ��ʽ��������* */

	public String manage(String temp)
	{
		String returnStr = "";
		temp = encrypt(temp);
		byte[] by = temp.getBytes();
		for (int i = 0; i < by.length; i++)
		{
			returnStr = returnStr + (byte) by[i] + "|";
		}
		return returnStr;
	}

	/** ��ʽ���������* */

	public byte[] disManage(String temp)
	{
		int len = 0, index = 0, i = 0, first = 0;
		while ((i = temp.indexOf("|", first)) > -1)
		{
			len++;
			first = i + 1;
		}

		byte[] by = new byte[len];

		first = 0;

		while ((i = temp.indexOf("|", first)) > -1)
		{

			by[index] = Byte.parseByte(temp.substring(first, i));

			index++;

			first = i + 1;

		}

		return by;

	}

	/** ��������λ�ĸ�����* */

	public String getRandom()
	{
		int i1 = (int) (java.lang.Math.random() * 10);
		int i2 = (int) (java.lang.Math.random() * 10);
		int i3 = (int) (java.lang.Math.random() * 10);
		int i4 = (int) (java.lang.Math.random() * 10);
		return String.valueOf(i1) + String.valueOf(i2) + String.valueOf(i3) + String.valueOf(i4);
	}

	/** ����1����λ����* */
	public String encrypt(String randomStr)
	{

		String para = random() + randomStr.substring(0, 1) + random() + random()
				+ randomStr.substring(1, 2);
		para = para + random() + randomStr.substring(2);
		return para;
		// return jiaMi(para);
	}

	/** �õ������0-9֮��* */

	private String random()
	{

		String temp = String.valueOf((int) (java.lang.Math.random() * 10));

		return temp;
	}

	/** ����2�����ܴ��?�˷��������Լ��޸�* */

	public String jiaMi(String str)
	{
		byte[] by = str.getBytes();
		ByteArrayInputStream in = new ByteArrayInputStream(by);
		int ch;
		int index = 0;
		byte[] temp = new byte[in.available()];
		while ((ch = in.read()) != -1)
		{
			temp[index] = (byte) (ch - key);
			index++;
		}
		ByteArrayInputStream ins = new ByteArrayInputStream(temp);
		DataInputStream inss = new DataInputStream(ins);
		try
		{
			return inss.readLine();
		}
		catch (Exception e)
		{
			return "";
		}
	}

	/** �Ӹ��������õ���ȷ������* */
	public String discrypt(String para)
	{
		// String para = jieMi(disManage(temp));
		// String para = disManage(temp);
		return para.substring(1, 2) + para.substring(4, 5) + para.substring(6, 8);

	}

	/** ���ܴ���* */
	public String jieMi(byte[] by)
	{
		ByteArrayInputStream in = new ByteArrayInputStream(by);
		int ch;
		int index = 0;
		byte[] temp = new byte[in.available()];
		while ((ch = in.read()) != -1)
		{
			temp[index] = (byte) (ch + key);
			index++;
		}
		ByteArrayInputStream ins = new ByteArrayInputStream(temp);
		DataInputStream inss = new DataInputStream(ins);
		try
		{
			return inss.readLine();
		}
		catch (Exception e)
		{
			return "";
		}
	}

	/** �ֽ�rgb��ʽ����ɫ num|num|num* */
	public int[] masterData(String temp)
	{
		int index_len = 0, index = 0, next_index = 0;
		int[] return_arr = new int[3];
		boolean break_error = false;
		if (getMax(temp, "|") == 2)
		{
			while ((index_len = temp.indexOf("|", next_index)) > -1)
			{
				if (getInt(temp.substring(next_index, index_len)) == 256)
				{
					break_error = true;
				}
				else
				{
					return_arr[index] = getInt(temp.substring(next_index, index_len));
					next_index = index_len + 1;
					index++;
				}
				if (break_error)
					break;
			}
			if (break_error)
			{
				return null;
			}
			else
			{
				return_arr[index] = getInt(temp.substring(next_index));
				return return_arr;
			}
		}
		else
		{
			System.out.println(error_format_int + ":" + temp);
			return null;
		}
	}

	private int getMax(String temp, String temp2)
	{
		int index = 0, index_len = 0, index_next = 0;
		while ((index = temp.indexOf(temp2, index_next)) > -1)
		{
			index_len++;
			index_next = index + 1;
		}
		return index_len;
	}

	private int getInt(String temp)
	{

		try
		{
			return Integer.parseInt(temp);
		}
		catch (Exception e)
		{
			System.out.println(error_color_input + ":" + temp);
			return 256;
		}
	}

	public static void main(String args[]) throws Exception
	{
		RandomCode pic = new RandomCode();
		String aa = pic.getRandom();
		String bb = pic.manage(aa);
		String jiami = pic.encrypt(aa);
		String jiemi = pic.discrypt(jiami);
		// String para = pic.jieMi(pic.disManage(bb));
		// String ee = para.substring(1,2)+para.substring(4,5)+ para.substring(6,8);
		// String cc = pic.discrypt(bb);
		System.out.println("aa=" + aa);
		System.out.println("jiami=" + jiami);
		// System.out.println("cc="+cc);
		System.out.println("jiemi=" + jiemi);
		// System.out.println("ee="+ee);

	}

}
