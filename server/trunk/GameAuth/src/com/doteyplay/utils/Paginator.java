package com.doteyplay.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Paginator
{
	public static final int DFT_PGSIZE = 5;
	private int pageNO;
	private int pageNext;
	private int pagePrev;
	private int pageCount;
	private int pageSize;
	private int count;
	private int start;
	private int end;
	private List list;
	private String[] fieldNames;
	private List namedList;

	public Paginator()
	{
		pageNO = 0;
		pageNext = -1;
		pagePrev = -1;
		pageCount = -1;
		pageSize = -1;
		count = 0;
		start = -1;
		end = -1;
		list = null;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public List getList()
	{
		return list;
	}

	public void setList(List list)
	{
		this.list = list;
	}

	public int getPageCount()
	{
		return pageCount;
	}

	public void setPageCount(int pageCount)
	{
		this.pageCount = pageCount;
	}

	public int getPagePrev()
	{
		return pagePrev;
	}

	public void setPagePrev(int pagePrev)
	{
		this.pagePrev = pagePrev;
	}

	public int getPageNext()
	{
		return pageNext;
	}

	public void setPageNext(int pageNext)
	{
		this.pageNext = pageNext;
	}

	public int getPageNO()
	{
		return pageNO;
	}

	public void setPageNO(int pageNO)
	{
		this.pageNO = pageNO;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public boolean hasMorePage()
	{
		return (pageNext > pageNO);
	}

	public int getEnd()
	{
		return end;
	}

	public void setEnd(int end)
	{
		this.end = end;
	}

	public int getStart()
	{
		return start;
	}

	public void setStart(int start)
	{
		this.start = start;
	}

	public String[] getFieldNames()
	{
		return fieldNames;
	}

	public void setFieldNames(String[] fieldNames)
	{
		this.fieldNames = fieldNames;
	}

	public void parseNamedList()
	{
		if (this.list != null && this.namedList == null && this.fieldNames != null
				&& this.fieldNames.length > 0)
		{
			this.namedList = new ArrayList();
			for (int i = 0; i < this.list.size(); i++)
			{
				Properties tmpProperties = new Properties();
				Object tmpObj = this.list.get(i);
				if (tmpObj instanceof String[])
				{
					String[] tmpAryObj = (String[]) tmpObj;
					for (int j = 0; j < this.fieldNames.length; j++)
						tmpProperties.setProperty(this.fieldNames[j], (tmpAryObj[j] != null) ? tmpAryObj[j]
								: "");
				}
			}
		}
	}

	public List getNamedList()
	{
		return namedList;
	}

	public void clear()
	{
		if (this.namedList != null)
			this.namedList.clear();

		if (this.list != null)
			this.list.clear();
	}

}
