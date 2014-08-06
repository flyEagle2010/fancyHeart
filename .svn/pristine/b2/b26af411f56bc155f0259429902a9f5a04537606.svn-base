package com.doteyplay.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.doteyplay.CommonConfig;
import com.doteyplay.bean.UserBean;
import com.doteyplay.core.dbcs.DBCS;
import com.doteyplay.dao.IUserBeanDao;
import com.google.gson.Gson;

public class NosqlCached
{
	private final static String INIT_FLAG = "INIT_FLAG";

	private JedisPool pool;
	private Gson gson = new Gson();

	private NosqlCached()
	{
	}

	public void init()
	{
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(100);
		config.setMaxIdle(20);
		config.setMaxWait((long) 1000 * 100);
		config.setTestOnBorrow(false);

		pool = new JedisPool(config, CommonConfig.REDIS_IP,
				CommonConfig.REDIS_PORT);

		Jedis jedis = null;
		try
		{
			jedis = pool.getResource();
			if (jedis.get(INIT_FLAG) == null)
			{
				jedis.set(INIT_FLAG, INIT_FLAG);
				
				this.initUserCached(jedis);
			}
		} catch (Exception e)
		{
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally
		{
			// 返还到连接池
			pool.returnResource(jedis);
		}

	}

	private void initUserCached(Jedis jedis)
	{
		IUserBeanDao dao = DBCS.getExector(IUserBeanDao.class);
		List<UserBean> userBeanList = dao.selectAllUserBean();

		for (UserBean bean : userBeanList)
			this.put(CacheType.USER, bean.getName(), gson.toJson(bean));

		userBeanList.clear();
		userBeanList = null;
	}

	public <T> List<T> getObjectList(CacheType type, String... key)
	{
		Jedis jedis = null;
		try
		{
			jedis = pool.getResource();
			List<T> list = new ArrayList<T>();
			List<String> jsonLst = jedis.hmget(type.toString(), key);
			for (String json : jsonLst)
			{
				T t = (T) gson.fromJson(json, type.clz);
				list.add(t);
			}

			return list;
		} catch (Exception e)
		{
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
			return null;
		} finally
		{
			// 返还到连接池
			pool.returnResource(jedis);
		}

	}

	public <T> T getObject(CacheType type, String key)
	{
		List<T> list = getObjectList(type, key);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	public <T> void put(CacheType type, String key, T t)
	{
		Jedis jedis = null;
		try
		{
			jedis = pool.getResource();
			
			jedis.hset(type.toString(), key, gson.toJson(t));

		} catch (Exception e)
		{
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally
		{
			// 返还到连接池
			pool.returnResource(jedis);
		}
	}
	
	public <T> void remove(CacheType type, String key)
	{
		Jedis jedis = null;
		try
		{
			jedis = pool.getResource();
			
			jedis.hdel(type.toString(), key);

		} catch (Exception e)
		{
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally
		{
			// 返还到连接池
			pool.returnResource(jedis);
		}
	}

	// /////////////////////////////////////////////////////////////////////////
	private final static NosqlCached instance = new NosqlCached();

	public final static NosqlCached getInstance()
	{
		return instance;
	}

	public static enum CacheType
	{
		USER(UserBean.class),
		SESSION_KEY(String.class), 
		;

		private Class clz;

		private CacheType(Class clz)
		{
			this.clz = clz;
		}
	}
	
}
