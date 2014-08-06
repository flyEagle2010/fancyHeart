package com.doteyplay.core.monitor.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.doteyplay.core.monitor.domain.ClipInfo;
import com.doteyplay.core.monitor.domain.Sample;

public class SampleManager
{
	private static final Logger logger = Logger.getLogger(SampleManager.class);

	/**
	 * 缓存样本队列 key为methodname
	 */
	private static Map<String, Sample> cache = new ConcurrentHashMap<String, Sample>();
	private static String excelName = "monitor.csv";

	private OutputStreamWriter pw;

	private SampleManager()
	{
		try
		{
			File f = new File(excelName);
			if (f.exists())
				f.delete();

			if (!f.exists())
				f.createNewFile();

			pw = new OutputStreamWriter(new FileOutputStream(f));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void release()
	{
		cache.clear();
	}

	/**
	 * 接收分析切片
	 */
	public boolean analy(ClipInfo info)
	{
		Sample sample = cache.get(info.getMethodName());
		if (sample == null)
		{
			sample = new Sample();
			sample.setPortalId(info.getPortalId());
			sample.setMethodName(info.getMethodName());
			cache.put(info.getMethodName(), sample);
		}
		sample.addUp(info);
		return true;
	}

	public void putOutInfo(int count)
	{
		List<Sample> list = new ArrayList<Sample>(cache.values());
		Collections.sort(list, new Comparator<Sample>()
		{
			@Override
			public int compare(Sample o1, Sample o2)
			{
				return o1.getTotalCost() > o2.getTotalCost() ? 1 : -1;
			}
		});

		try
		{
			pw.write(getCsvTitle() + "\r\n");
			for (Sample sample : list)
			{
				pw.write(sample.csv() + "\r\n");
			}
			pw.flush();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				pw.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	}

	private String getCsvTitle()
	{
		return Sample.csvTitle();
	}

	// //////////////////////////////////////////////////////
	private static SampleManager instance = new SampleManager();

	public static SampleManager getInstance()
	{
		return instance;
	}
}
