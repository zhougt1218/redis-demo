package com.sdyy.redis.redisdemo.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PreDestroy;

import com.sdyy.redis.redisdemo.service.ICacheService;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service(value = "redisService")
public class RedisService implements ICacheService<JedisPool> {



	private static JedisPool pool;

	@Override
	public void setCacheManager(JedisPool manager) {
		this.pool = manager;
		System.out.println(pool+"++++++++++++++++++++++++==");
	}

	@Override
	public JedisPool getCacheManager() {
		return this.pool;
	}

	@Override
	public String getString(final String key) {
		Object o = new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				return jedis.get(key);
			}
		}.doSth();
		if (o != null) {
			return o.toString();
		}
		return null;
	}

	public byte[] getByteArray(final String key) {
		Object o = new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				return jedis.get(key.getBytes());
			}
		}.doSth();
		if (o != null) {
			return (byte[]) o;
		}
		return null;
	}

	@Override
	public Map getMap(final String key) {
		Object o = new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				// return jedis.mget(key);
				// Changed by yangy
				return jedis.hgetAll(key);
			}
		}.doSth();
		if (o != null) {
			return (Map) o;
		}
		return null;
	}

	@Override
	public Set getSet(final String key) {
		Object o = new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				return jedis.sunion(key);
			}
		}.doSth();
		if (o != null) {
			return (Set) o;
		}
		return null;
	}

	public List getList(final String key) {
		Object o = new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				List<String> list = new ArrayList<String>();
				Long len = jedis.llen(key);
				if (len == 0) {
					return null;
				}
				for (long i = 0; i < len; i++) {
					list.add(jedis.lindex(key, i));
				}
				return list;
			}
		}.doSth();
		if (o != null) {
			return (List) o;
		}
		return null;
	}

	public String putByteArray(final String key, final byte[] value) {
		return new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				return jedis.set(key.getBytes(), value);
				// return null;
			}
		}.doSth().toString();
	}

	public String putExpireByteArray(final String key, final byte[] value, final int seconds) {
		return new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				String str = jedis.set(key.getBytes(), value);
				jedis.expire(key.getBytes(), seconds);
				return str;
			}
		}.doSth().toString();
	}

	@Override
	public String putString(final String key, final String value) {
		return new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				return jedis.set(key, value);
				// return null;
			}
		}.doSth().toString();
	}

	@Override
	public String putExpireString(final String key, final String value, final int seconds) {
		return new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				Object obj = jedis.set(key, value);
				jedis.expire(key, seconds);
				return obj;
				// return null;
			}
		}.doSth().toString();
	}

	@Override
	public long putList(final String key, final List value) {
		Object o = new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				int lsize = value.size();
				String[] arr = new String[lsize];
				for (int i = 0; i < lsize; i++) {
					arr[i] = value.get(i).toString();
				}
				return jedis.lpush(key, arr);
			}
		}.doSth();
		return (Long) o;
	}

	@Override
	public long putExpireList(final String key, final List value, final int seconds) {
		Object o = new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				int lsize = value.size();
				String[] arr = new String[lsize];
				for (int i = 0; i < lsize; i++) {
					arr[i] = value.get(i).toString();
				}
				Object obj = jedis.lpush(key, arr);
				jedis.expire(key, seconds);
				return obj;
			}
		}.doSth();
		return (Long) o;
	}

	@Override
	public String putMap(final String key, final Map value) {
		return new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				return jedis.hmset(key, value);
			}
		}.doSth().toString();
	}

	@Override
	public String putExpireMap(final String key, final Map value, final int seconds) {
		return new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				Object obj = jedis.hmset(key, value);
				jedis.expire(key, seconds);
				return obj;
			}
		}.doSth().toString();
	}

	@Override
	public long putSet(final String key, final String... value) {
		return (Long) new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				return jedis.sadd(key, value);
			}
		}.doSth();
	}

	@Override
	public long expire(final String key, final int seconds) {
		return (Long) new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				return jedis.expire(key, seconds);
			}
		}.doSth();

	}

	@Override
	public void remove(final String key) {
		new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				return jedis.del(key);
			}
		}.doSth();

	}

	@Override
	public void removeByReg(final String key) {
		new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				Set<String> set = jedis.keys(key + "*");
				Iterator<String> it = set.iterator();
				while (it.hasNext()) {
					String key = it.next();
					jedis.del(key);
				}
				return 0;
			}
		}.doSth();

	}

	/**
	 * 
	 * @Title: getWhithCacheName
	 * @author：刘宇祥
	 * @date：2017年1月11日下午6:15:27
	 * @Description: 使用的是Redis的Map
	 * @param mapName
	 * @param key
	 * @return 返回一个字符串
	 */
	@Override
	public String getStringValueFromMap(final String mapName, final String key) {
		Object obj = new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				return jedis.hget(mapName, key);
			}

		}.doSth();
		return obj == null ? null : obj.toString();
	}

	/**
	 * 
	 * @Title: putWhithCacheName
	 * @author：刘宇祥
	 * @date：2017年1月11日下午6:10:33
	 * @Description: 使用的是Redis的Map
	 * @param
	 * @param key
	 * @param value
	 */
	@Override
	public String putStringValueOfMap(final String mapName, final String key, final String value) {
		Object obj = new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				jedis.hset(mapName, key, value);
				return null;
			}

		}.doSth();
		return obj == null ? null : obj.toString();
	}

	@Override
	public long removeKeyOfMap(final String key, final String mapKey) {
		return (Long) new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				return jedis.hdel(key, mapKey);
			}
		}.doSth();
	}

	@Override
	public void MapIncreBy(final String key, final String mapKey, final long value) {
		new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				return jedis.hincrBy(key, mapKey, value);
			}
		}.doSth();
	}

	@Override
	public void StringIncreBy(final String key, final long value) {
		new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				return jedis.incrBy(key, value);
			}
		}.doSth();
	}

	@Override
	public Set getAllKeys(final String pattern) {
		Object o = new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				return jedis.keys(pattern);
			}
		}.doSth();
		if (o != null) {
			return (Set) o;
		}
		return null;
	}

	abstract class RedisAutoCloseTemplate {
		public Object doSth() {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return doSthWithJedis(jedis);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}

		}

		public abstract Object doSthWithJedis(Jedis jedis);

	}

	@Override
	public boolean isKeyExist(final String key) {
		Object o = new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				return jedis.exists(key);
			}
		}.doSth();
		boolean result = ((Boolean) o).booleanValue();
		return result;
	}

	@Override
	public String getValueTypeByKey(final String key) {
		Object o = new RedisAutoCloseTemplate() {
			@Override
			public Object doSthWithJedis(Jedis jedis) {
				return jedis.type(key);
			}
		}.doSth();
		String type = o.toString();
		return type;
	}

	@PreDestroy
	public void dostory() {
		if (pool != null) {
			pool.destroy();
		}
	}

}