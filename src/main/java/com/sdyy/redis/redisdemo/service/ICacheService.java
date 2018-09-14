package com.sdyy.redis.redisdemo.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @ClassName: ICacheService
 * @Description: 仅为之后方便替换缓存
 * @author: 刘宇祥
 * @date: 2017年1月12日下午2:27:08
 * @param <T>
 */
public interface ICacheService<T> {
	public static final String DEFAULT_CACHE = "sysCache";
	public static final String DICT_CACHE = "dictCache";

	public void setCacheManager(T manager);

	public T getCacheManager();

	/**
	 * 
	 * @Title: getByteArray
	 * @author：liuyx
	 * @date：2017年2月9日下午2:36:41
	 * @Description: 获取字节数组
	 * @param key
	 * @return
	 */
	public byte[] getByteArray(final String key);

	/**
	 * 
	 * @Title: putByteArray
	 * @author：liuyx
	 * @date：2017年2月9日下午2:37:00
	 * @Description: 存放字节数组
	 * @param key
	 * @param value
	 * @return
	 */
	public String putByteArray(final String key, final byte[] value);

	/**
	 * 
	 * @Title: putExpireByteArray
	 * @author：liuyx
	 * @date：2017年2月9日下午2:37:04
	 * @Description: 存放字节数组，定时
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	public String putExpireByteArray(final String key, final byte[] value, final int seconds);

	/**
	 * 
	 * @Title: getString
	 * @author：刘宇祥
	 * @date：2017年1月12日上午11:26:19
	 * @Description: 获取String类型
	 * @param key
	 * @return
	 */
	public String getString(String key);

	/**
	 * 
	 * @Title: getMap
	 * @author：刘宇祥
	 * @date：2017年1月12日上午11:26:19
	 * @Description: 获取Map类型
	 * @param key
	 * @return
	 */
	public Map getMap(String key);

	/**
	 * 
	 * @Title: getSet
	 * @author：刘宇祥
	 * @date：2017年1月12日上午11:26:19
	 * @Description: 获取Set类型
	 * @param key
	 * @return
	 */
	public Set getSet(String key);

	/**
	 * 
	 * @Title: getList
	 * @author：刘宇祥
	 * @date：2017年1月12日上午11:26:19
	 * @Description: 获取List类型
	 * @param key
	 * @return
	 */
	public List getList(String key);

	public String putString(String key, String value);

	public long putList(String key, List value);

	public String putMap(String key, Map value);

	/*public String putObject(String key,Object object);*/

	public long putSet(String key, String... value);

	public String putExpireString(String key, String value, int seconds);

	public long putExpireList(String key, List value, int seconds);

	public String putExpireMap(String key, Map value, int seconds);

	public long expire(String key, int seconds);

	public void remove(String key);

	public void removeByReg(String key);

	public String getStringValueFromMap(String key, String mapKey);

	public String putStringValueOfMap(String key, String mapKey, String value);

	public long removeKeyOfMap(String key, String mapKey);

	/**
	 * 
	 * @Title: Map increment by param
	 * @author：yangyang
	 * @date：2017年1月17日上午12:26:19
	 * @Description: map中的变量自增根据传入的参数
	 */
	public void MapIncreBy(String key, String mapKey, long value);

	/**
	 * 
	 * @Title: String increment by param
	 * @author：yangyang
	 * @date：2017年1月17日上午12:26:30
	 * @Description: string中的变量自增根据传入的参数
	 */
	public void StringIncreBy(String key, long value);

	/**
	 * 
	 * @Title: getAllKeys
	 * @author：yangyang
	 * @date：2017年1月17日上午12:27:00
	 * @Description: 获取redis中的所有keys
	 * @param matching
	 * the glob-style pattern as space separated strings
	 */
	public Set getAllKeys(String pattern);

	/**
	 * 
	 * @Title: isKeyExist
	 * @author：yangyang
	 * @Description: 判断指定key是否存在
	 */
	public boolean isKeyExist(String key);

	/**
	 * 
	 * @Title: getValueTypeByKey
	 * @author：yangyang
	 * @Description: 判断指定key的值的类型
	 */
	public String getValueTypeByKey(String key);

}
