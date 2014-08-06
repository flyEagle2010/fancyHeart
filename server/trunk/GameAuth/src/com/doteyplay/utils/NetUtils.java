package com.doteyplay.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.doteyplay.utils.StrUtils;

public class NetUtils
{
	public static String HttpPost(String connUrl, String content)
	{
		HttpURLConnection httpConnection = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		StringBuffer tmpContents = new StringBuffer();
		try
		{
			URL url = new URL(connUrl);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestProperty("content-type", "text/html");
			httpConnection.setRequestMethod("POST");
			httpConnection.setConnectTimeout(30000);
			httpConnection.setReadTimeout(30000);
			httpConnection.setUseCaches(false);
			httpConnection.setDoOutput(true);
			httpConnection.setDoInput(true);

			if (!StrUtils.empty(content))
			{
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpConnection.getOutputStream(),
						"UTF-8"));
				out.write(content);
				out.flush();
				out.close();
			}

			char[] tmpBuffer = new char[1024];
			isr = new InputStreamReader(httpConnection.getInputStream(), "UTF-8");
			br = new BufferedReader(isr);
			int tmpSize = 0;
			do
			{
				tmpSize = br.read(tmpBuffer);
				if (tmpSize > 0)
					tmpContents.append(tmpBuffer, 0, tmpSize);
			}
			while (tmpSize > 0);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				}
				catch (IOException e)
				{
				}
				br = null;
			}
			if (isr != null)
			{
				try
				{
					isr.close();
				}
				catch (IOException e)
				{
				}
				isr = null;
			}
			if (httpConnection != null)
			{
				try
				{
					httpConnection.disconnect();
				}
				catch (Exception e)
				{
				}
				httpConnection = null;
			}
		}

		return tmpContents.toString();
	}
}
