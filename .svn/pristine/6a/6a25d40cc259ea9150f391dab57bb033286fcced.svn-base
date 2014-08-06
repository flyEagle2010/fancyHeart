package com.doteyplay.utils;

import java.io.Serializable;

public class KvMap implements Serializable
{
	private static final long serialVersionUID = -7789756358189744712L;

	private final static int CAPCITY_INCREASING = 8;

	private String _dataName;
	private String[] _keyBuffer;
	private String[] _valueBuffer;
	private int _capcity;
	private int _length;

	public KvMap()
	{
		reset();
	}

	private void reset()
	{
		this._capcity = 0;
		this._length = 0;
		this._keyBuffer = null;
		this._valueBuffer = null;
		this._dataName = "data";
	}

	public KvMap setDataName(String dataname)
	{
		if (dataname != null && dataname.length() > 0)
			this._dataName = dataname;
		return this;
	}

	public String getDataName()
	{
		return _dataName;
	}

	public int size()
	{
		return _length;
	}

	public String getKey(int idx)
	{
		if (idx >= 0 && idx < _length)
			return _keyBuffer[idx];
		else
			return null;
	}

	// �����ֻ��Ч�ʣ����������
	public String[] getKeys()
	{
		return this._keyBuffer;
	}

	public String[] getValues()
	{
		return this._valueBuffer;
	}

	public String getValue(int idx)
	{
		if (idx >= 0 && idx < _length)
			return _valueBuffer[idx];
		else
			return null;
	}

	public void setValue(int idx, String value)
	{
		if (idx >= 0 && idx < _length)
			_valueBuffer[idx] = (value != null) ? value : "";
	}

	public boolean keyExist(String key)
	{
		if (key == null || key.length() <= 0)
			return false;

		boolean r = false;
		int i = 0;
		while (!r && i < _length)
		{
			if (this._keyBuffer[i].equals(key))
				r = true;
			i++;
		}
		return r;
	}

	public String getValue(String key)
	{
		if (key == null || key.length() <= 0)
			return null;

		String r = null;
		int i = 0;
		while (r == null && i < _length)
		{
			if (this._keyBuffer[i].equals(key))
				r = this._valueBuffer[i];
			i++;
		}
		return r;
	}

	public void setValue(String key, String value)
	{
		if (key == null || key.length() <= 0)
			return;

		boolean cnt = false;
		int i = 0;
		while (cnt && i < _length)
		{
			if (this._keyBuffer[i].equals(key))
			{
				cnt = false;
				this._valueBuffer[i] = (value != null) ? value : "";
			}
			i++;
		}
	}

	public int getValueOfInt(String key, int dftvalue)
	{
		String tmpV = this.getValue(key);
		if (tmpV == null || tmpV.length() <= 0)
			return 0;

		int r = 0;
		try
		{
			r = Integer.valueOf(tmpV);
		}
		catch (Exception e)
		{
			r = dftvalue;
			e.printStackTrace();
		}
		return r;
	}

	public float getValueOfFloat(String key, float dftvalue)
	{
		String tmpV = this.getValue(key);
		if (tmpV == null || tmpV.length() <= 0)
			return 0;

		float r = (float) 0.0;
		try
		{
			r = Float.valueOf(tmpV);
		}
		catch (Exception e)
		{
			r = dftvalue;
			e.printStackTrace();
		}
		return r;
	}

	public double getValueOfDouble(String key, double dftvalue)
	{
		String tmpV = this.getValue(key);
		if (tmpV == null || tmpV.length() <= 0)
			return 0;

		double r = 0.0;
		try
		{
			r = Double.valueOf(tmpV);
		}
		catch (Exception e)
		{
			r = dftvalue;
			e.printStackTrace();
		}
		return r;
	}

	private void fixCapcity(int increment)
	{
		if (increment > 0 && increment + _length >= _capcity)
		{
			int newBufferSize = ((int) ((increment + _length - _capcity) / CAPCITY_INCREASING) + 1)
					* CAPCITY_INCREASING;
			_capcity += newBufferSize;
			String[] currentBuffer = _keyBuffer;
			_keyBuffer = new String[_capcity];
			if (currentBuffer != null)
				System.arraycopy(currentBuffer, 0, _keyBuffer, 0, currentBuffer.length);
			currentBuffer = _valueBuffer;
			_valueBuffer = new String[_capcity];
			if (currentBuffer != null)
				System.arraycopy(currentBuffer, 0, _valueBuffer, 0, currentBuffer.length);
			currentBuffer = null;
		}
	}

	public KvMap addKey(String key)
	{
		return addKeyValue(key, null);
	}

	public KvMap addKeyValue(String key, String value)
	{
		if (key == null || key.length() <= 0)
			return this;

		fixCapcity(1);
		this._keyBuffer[this._length] = (key != null) ? key : "";
		this._valueBuffer[this._length] = (value != null) ? value : "";
		this._length++;
		return this;
	}

	@Override
	public String toString()
	{
		StringBuilder buffer = new StringBuilder(this._dataName);
		for (int i = 0; i < this._length; i++)
		{
			buffer.append(" ").append(this._keyBuffer[i]).append("=\"");
			encodeChars(this._valueBuffer[i], buffer);
			buffer.append("\"");
		}
		return buffer.toString();
	}

	public KvMap assignData(String script)
	{
		reset();
		if (script != null)
		{
			String tmpPrty = null;
			StringBuilder tmpDecodeBuffer = new StringBuilder();

			int strLen = script.length();
			char ch = 0;
			int lastpos = 0;
			int status = 0;
			int cursor = 0;
			for (; cursor < strLen; cursor++)
			{
				ch = script.charAt(cursor);
				if (ch == 32)
				{
					// �ո�
					if (status == 0)
					{
						if (cursor > lastpos)
							this.setDataName(script.substring(lastpos, cursor));
						status = 1;
					}
					else if (status == 5)
					{
						status = 1;
					}
				}
				else if (ch == 61)
				{
					// =
					if (status == 2)
					{
						if (cursor > lastpos)
							tmpPrty = script.substring(lastpos, cursor);
						status = 3;
					}
				}
				else if (ch == 34)
				{
					// "
					if (status == 3)
					{
						status = 4;
						lastpos = cursor;
					}
					else if (status == 4)
					{
						status = 5;
						if (cursor > lastpos + 1)
						{
							tmpDecodeBuffer.setLength(0);
							decodeChars(script.substring(lastpos + 1, cursor), tmpDecodeBuffer);
							if (tmpPrty != null && tmpPrty.length() > 0)
								this.addKeyValue(tmpPrty, tmpDecodeBuffer.toString());
						}
						else if (cursor == lastpos + 1)
						{
							if (tmpPrty != null && tmpPrty.length() > 0)
								this.addKey(tmpPrty);
						}
						tmpPrty = null;
					}
				}
				else
				{
					if (status == 1)
					{
						status = 2;
						lastpos = cursor;
					}
					else if (status < 0)
					{
						status = 0;
						lastpos = cursor;
					}
				}
			}
			if (status == 0 && lastpos == 0 && cursor > lastpos)
				this.setDataName(script.substring(lastpos, cursor));
		}
		return this;
	}

	public final static KvMap create()
	{
		return new KvMap();
	}

	public final static KvMap create(String dataname)
	{
		return new KvMap().setDataName(dataname);
	}

	private final static void encodeChars(String pvalue, StringBuilder abuffer)
	{
		if (pvalue != null && abuffer != null)
		{
			int tmpLen = pvalue.length();
			char tmpChar;
			for (int i = 0; i < tmpLen; i++)
			{
				tmpChar = pvalue.charAt(i);
				switch (tmpChar)
				{
					case '\r':
						abuffer.append("#13;");
						break;
					case '\n':
						abuffer.append("#10;");
						break;
					case '"':
						abuffer.append("#34;");
						break;
					default:
						abuffer.append(tmpChar);
						break;
				}
			}
		}
	}

	private final static void decodeChars(String pvalue, StringBuilder abuffer)
	{
		if (pvalue == null || abuffer == null)
			return;

		int lastpos = 0;
		int status = 0;
		int tmpLen = pvalue.length();
		char ch;
		int i, j;
		for (i = 0; i < tmpLen; i++)
		{
			ch = pvalue.charAt(i);
			if (ch == 35)
			{
				// #
				if (status == 1)
				{
					for (j = lastpos; j < i; j++)
						abuffer.append(pvalue, lastpos, i);
				}
				lastpos = i;
				status = 1;
			}
			else if (ch == 59)
			{
				// ;
				if (status == 1)
				{
					if (i == lastpos + 3)
					{
						if (pvalue.charAt(lastpos + 1) == 49 && pvalue.charAt(lastpos + 2) == 51)
						{
							abuffer.append("\r");
							status = 0;
						}
						else if (pvalue.charAt(lastpos + 1) == 49 && pvalue.charAt(lastpos + 2) == 48)
						{
							abuffer.append("\n");
							status = 0;
						}
						else if (pvalue.charAt(lastpos + 1) == 51 && pvalue.charAt(lastpos + 2) == 52)
						{
							abuffer.append("\"");
							status = 0;
						}
					}
					if (status == 1)
					{
						status = 0;
						abuffer.append(pvalue, lastpos, i);
					}
				}
				else
					abuffer.append(ch);
			}
			else
			{
				if (status == 0)
					abuffer.append(ch);
			}
		}
		if (status == 1)
		{
			for (j = lastpos; j < tmpLen; j++)
				abuffer.append(pvalue, lastpos, tmpLen);
		}
	}

	public final static KvMap createFromData(String script)
	{
		return new KvMap().assignData(script);
	}

	public static void main(String[] args)
	{
		KvMap tmpFM = KvMap.create();
		tmpFM.addKeyValue("aaa", "o\"g o=\roooo\n999");

		tmpFM.addKeyValue("bbb", "urehfdaifier&73649(&*&&%^KKJIUI");
		tmpFM.addKeyValue("ccc", null);

		tmpFM.addKeyValue(null, "ooo");
		tmpFM.addKeyValue(null, "");

		tmpFM.addKeyValue("", "");
		tmpFM.addKeyValue("", null);
		tmpFM.addKeyValue(null, "");

		tmpFM.addKeyValue(null, null);

		System.out.println(tmpFM.toString());

		KvMap tmpFM2 = KvMap.createFromData(tmpFM.toString());
		System.out.println(tmpFM2.toString());

		KvMap tmpFM3 = KvMap.createFromData("uuu");
		System.out.println(tmpFM3.toString());
	}
}
