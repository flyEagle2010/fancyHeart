package com.doteyplay.manager;

import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.doteyplay.CommonConfig;
import com.doteyplay.manager.NosqlCached.CacheType;

/**
 * @className:TestRedis.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年11月24日 下午5:02:58
 */
public class TestRedis {

	public static void main(String[] args) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(100);
		config.setMaxIdle(20);
		config.setMaxWait((long) 1000 * 100);
		config.setTestOnBorrow(false);

		JedisPool pool = new JedisPool(config, "192.168.200.102",
				CommonConfig.REDIS_PORT);
		
		Jedis jedis = pool.getResource();
		
		jedis.hset(CacheType.UUID.toString(), "kkkkkk", "");
		jedis.hset(CacheType.UUID.toString(), "kkkkk", "");
		jedis.hset(CacheType.UUID.toString(), "kkkk", "");
		jedis.hset(CacheType.UUID.toString(), "kkk", "");
		jedis.hset(CacheType.UUID.toString(), "kk", "");
		
		String value = jedis.hget(CacheType.UUID.toString(), "kk");
		System.out.println("结果:"+value);
		value =jedis.hget(CacheType.UUID.toString(), "kkk");
		System.out.println("结果:"+value);
		value =jedis.hget(CacheType.UUID.toString(), "kkkk");
		System.out.println("结果:"+value);
		value =jedis.hget(CacheType.UUID.toString(), "kkkkk");
		System.out.println("结果:"+value);
		value =jedis.hget(CacheType.UUID.toString(), "kkkkkk");
		System.out.println("结果:"+value);
		Map<String, String> hgetAll = jedis.hgetAll(CacheType.UUID.toString());
		
		
		
		pool.returnResource(jedis);

	}
}
