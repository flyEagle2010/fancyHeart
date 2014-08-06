package com.doteyplay.core.dbcs.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibatis.common.resources.Resources;

/**
 * 带标记文本读取器
 * 
 * @author 
 * 
 */
public abstract class AbstractTagFileReader
{	
	public abstract String onTag(String tag);
	
	public abstract String getContent();
	
	public String read(String path) throws IOException
	{
		Reader reader = Resources.getResourceAsReader(path);
		BufferedReader bReader = new BufferedReader(reader);
		
		StringBuilder xmlStr = new StringBuilder();
		while(true)
		{
			String line = bReader.readLine();
			if(line == null)
				break;
			xmlStr.append(line).append("\r\n");
		}
		
		bReader.close();
		bReader = null;
		
		return filter(xmlStr.toString());
	}
	
	private String filter(String souStr)
	{
		if (souStr != null && souStr.length() > 0)
		{
			int tmpCkPos = 0;
			int tmpFdPos = 0;
			String tmpCkStr = null;
			StringBuffer tmpBuffer = new StringBuffer(souStr.length());
			Pattern sourcePattern = Pattern.compile("#(.+?)#");
			Matcher matcher = sourcePattern.matcher(souStr);
			
			while (matcher != null && matcher.find())
			{
				tmpFdPos = matcher.start();
				if (tmpFdPos > tmpCkPos)
					tmpBuffer.append(souStr.substring(tmpCkPos, tmpFdPos));
				
				tmpCkStr = matcher.group(1);
				tmpBuffer.append(onTag(tmpCkStr));
				tmpCkPos = matcher.end();
			}
			
			if (tmpCkPos < souStr.length())
				tmpBuffer.append(souStr.substring(tmpCkPos, souStr.length()));
//			System.out.println(tmpBuffer.toString());
			return tmpBuffer.toString();
		}
		return souStr;
	}
}
