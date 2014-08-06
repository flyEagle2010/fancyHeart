package com.doteyplay.core.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.doteyplay.core.bhns.ISimpleService;

public class AbstractExtServiceContainer<B extends ISimpleService, A extends IBaseAffiliatedService>
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(AbstractExtServiceContainer.class);

	protected B mainService;

	private Map<Integer, A> extService = new HashMap<Integer, A>();

	public AbstractExtServiceContainer(B bservice)
	{
		this.mainService = bservice;
	}

	public ISimpleService getExtService(int portalId)
	{
		if (extService.containsKey(portalId))
		{
			return (ISimpleService) extService.get(portalId);
		} else
		{
			return null;
		}
	}

	public void initService(int portalId)
	{
		if (extService.containsKey(portalId))
		{
			if (!extService.get(portalId).initialize())
			{
				logger.error("扩展服务初始化失败, portalId=" + portalId);
			}
		} else
		{
			logger.error("不存在的扩展服务, portalId=" + portalId);
		}
	}

	public void release()
	{
		for (A s : extService.values())
		{
			s.release();
		}
	}
}
