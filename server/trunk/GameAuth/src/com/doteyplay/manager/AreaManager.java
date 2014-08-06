package com.doteyplay.manager;

import java.util.List;

import com.doteyplay.bean.AreaBean;
import com.doteyplay.core.dbcs.DBCS;
import com.doteyplay.dao.IAreaBeanDao;

public class AreaManager
{
	private List<AreaBean> areaList = null;
	
	private AreaManager()
	{
	}

	public void init()
	{
		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while(true)
				{
					IAreaBeanDao dao = DBCS.getExector(IAreaBeanDao.class);
					areaList = dao.selectAllAreaBean();
					try
					{
						Thread.sleep(60 * 1000);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}	
				}
			}
		});
		t.start();
	}
	
	public List<AreaBean> getAreaList()
	{
		return areaList;
	}



	// /////////////////////////////////////////////////////////////////////////
	private final static AreaManager instance = new AreaManager();

	public final static AreaManager getInstance()
	{
		return instance;
	}
}
