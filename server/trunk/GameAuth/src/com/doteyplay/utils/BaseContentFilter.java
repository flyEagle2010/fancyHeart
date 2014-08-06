package com.doteyplay.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.StringTokenizer;

public class BaseContentFilter
{
	public class TagsNeedFilter
	{
		public char tagsLower[][];
		public char tagsUpper[][];
		public char attrsLower[][];
		public char attrsUpper[][];
		public int tagsLength;
		public int attrsLength;

		public TagsNeedFilter()
		{
			tagsLength = 0;
			attrsLength = 0;
		}
	}

	private TagsNeedFilter parseTags(String tagtext)
	{
		TagsNeedFilter result = new TagsNeedFilter();
		int tagCount = 0;
		ArrayList tagsList = new ArrayList();
		ArrayList attrsList = new ArrayList();
		for (Enumeration e = new StringTokenizer(tagtext, ","); e.hasMoreElements();)
		{
			tagCount++;
			String pair = (String) e.nextElement();
			if (pair.indexOf("=") >= 0)
			{
				String tagToAdd = pair.substring(0, pair.indexOf("="));
				String attrToAdd = pair.substring(pair.indexOf("=") + 1);
				if (tagToAdd.length() > result.tagsLength)
					result.tagsLength = tagToAdd.length();
				if (attrToAdd.length() > result.attrsLength)
					result.attrsLength = attrToAdd.length();
				tagsList.add(tagToAdd);
				attrsList.add(attrToAdd);
			}
		}

		result.tagsLower = new char[tagCount][];
		result.tagsUpper = new char[tagCount][];
		result.attrsLower = new char[tagCount][];
		result.attrsUpper = new char[tagCount][];
		for (int i = 0; i < tagCount; i++)
		{
			result.tagsLower[i] = ((String) tagsList.get(i)).toLowerCase().toCharArray();
			result.tagsUpper[i] = ((String) tagsList.get(i)).toUpperCase().toCharArray();
			result.attrsLower[i] = ((String) attrsList.get(i)).toLowerCase().toCharArray();
			result.attrsUpper[i] = ((String) attrsList.get(i)).toUpperCase().toCharArray();
		}
		return result;
	}

	private TagsNeedFilter tags;
	private int state;
	private int nodeMatchLength;
	private int attrToMatch;
	private boolean isUrlQuoted;
	private boolean nodeMatch[];

	public BaseContentFilter()
	{
		this("a=href,go=href,form=action");
	}

	public BaseContentFilter(String taglist)
	{
		tags = parseTags((!StrUtils.empty(taglist)) ? taglist
				: "a=href,go=href,form=action");
		nodeMatch = new boolean[tags.tagsLower.length];
		recycleNodeMatch();
		recycleState();
	}

	private void recycleNodeMatch()
	{
		nodeMatchLength = 0;
		for (int i = 0; i < nodeMatch.length; i++)
			nodeMatch[i] = true;
	}

	private void recycleState()
	{
		state = 0;
		nodeMatchLength = 0;
		isUrlQuoted = false;
		attrToMatch = 0;
	}

	protected String correctUrl(String urlstring, String refurl)
	{
		try
		{
			return (new URL((new URL(refurl)), urlstring)).toString();
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
			return urlstring;
		}
	}

	public String parse(String soucontent, String refurl)
	{
		if (StrUtils.empty(soucontent) || StrUtils.empty(refurl))
			return soucontent;

		StringBuffer tmpBuffer = new StringBuffer();

		char[] urlBuffer = new char[512];
		int urlCursor = 0;

		char[] cbuf = soucontent.toCharArray();
		int bufOff = 0;
		int bufLen = 0;
		for (int curr = 0; curr < cbuf.length; curr++)
		{
			if (state == 0)
			{
				if (cbuf[curr] == '<')
					state = 1;
				bufLen++;
			}
			else if (state == 1)
			{
				if (cbuf[curr] == '/' || cbuf[curr] == '>' || cbuf[curr] == '<')
				{
					recycleNodeMatch();
					state = 0;
				}
				else if (!Character.isWhitespace(cbuf[curr]) && nodeMatchLength < tags.tagsLength)
				{
					nodeMatchLength++;
					for (int i = 0; i < nodeMatch.length; i++)
						if (nodeMatch[i]
								&& (nodeMatchLength > tags.tagsLower[i].length || cbuf[curr] != tags.tagsLower[i][nodeMatchLength - 1]
										&& cbuf[curr] != tags.tagsUpper[i][nodeMatchLength - 1]))
							nodeMatch[i] = false;

				}
				else
				{
					int matchedNode = -1;
					for (int i = 0; i < nodeMatch.length; i++)
					{
						if (!nodeMatch[i] || tags.tagsLower[i].length != nodeMatchLength)
							continue;
						matchedNode = i;
						break;
					}

					if (matchedNode != -1)
					{
						state = 2;
						attrToMatch = matchedNode;
					}
					else
					{
						state = 0;
					}
					recycleNodeMatch();
				}
				bufLen++;
			}
			else if (state == 2)
			{
				if (cbuf[curr] == '/' || cbuf[curr] == '>')
				{
					recycleNodeMatch();
					state = 0;
				}
				else if (cbuf[curr] == '=')
				{
					if (nodeMatch[attrToMatch] && tags.attrsLower[attrToMatch].length == nodeMatchLength)
						state = 3;
					recycleNodeMatch();
				}
				else if (!Character.isWhitespace(cbuf[curr]) && nodeMatchLength < tags.attrsLength)
				{
					nodeMatchLength++;
					if (nodeMatch[attrToMatch]
							&& (nodeMatchLength > tags.attrsLower[attrToMatch].length || cbuf[curr] != tags.attrsLower[attrToMatch][nodeMatchLength - 1]
									&& cbuf[curr] != tags.attrsUpper[attrToMatch][nodeMatchLength - 1]))
						nodeMatch[attrToMatch] = false;
				}
				else if (Character.isWhitespace(cbuf[curr]))
				{
					recycleNodeMatch();
					nodeMatchLength = 0;
				}
				bufLen++;
			}
			else if (state == 3)
			{
				if (cbuf[curr] == '\'' || cbuf[curr] == '"' || cbuf[curr] == '>' || cbuf[curr] == '<'
						|| Character.isWhitespace(cbuf[curr]) && !isUrlQuoted)
				{
					if (urlCursor > 0 || isUrlQuoted)
					{
						tmpBuffer.append(cbuf, bufOff, bufLen);
						bufOff = curr;
						bufLen = 0;
						if (urlCursor > 0)
							tmpBuffer.append(this.correctUrl(new String(urlBuffer, 0, urlCursor), refurl));
						urlCursor = 0;
						recycleState();
					}
					else if (cbuf[curr] == '\'' || cbuf[curr] == '"')
						isUrlQuoted = true;
					bufLen++;
				}
				else
				{
					if (urlCursor < urlBuffer.length)
					{
						urlBuffer[urlCursor] = cbuf[curr];
						urlCursor++;
					}
				}
			}
		}
		if (bufLen > 0)
			tmpBuffer.append(cbuf, bufOff, bufLen);
		return tmpBuffer.toString();
	}
}
