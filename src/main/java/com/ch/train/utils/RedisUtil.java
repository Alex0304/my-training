package com.ch.train.utils;

import com.ch.train.entity.ProcessMsg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.SafeEncoder;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RedisUtil {
    private static final Logger log = LogManager.getLogger(RedisUtil.class);

    private static JedisPool pool = null;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(128);
        config.setMaxIdle(80);
        config.setMaxWaitMillis(2001);
        config.setTestOnBorrow(true);        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；

        String password = "dxm!123@321#";

        if (StringUtils.isNotEmpty(password)) {
            pool = new JedisPool(config, "192.168.1.201", 8530, 2000, password);
        } else {
            pool = new JedisPool(config, "192.168.1.201", 8530, 2000);
        }
    }

    /**
     * 把key存入redis中
     *
     * @param key     k
     * @param value   v
     * @param seconds 过期时间（秒）
     * @return boolean
     */
    public static boolean set(byte[] key, byte[] value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key, value);
            if (seconds > 0) {
                jedis.expire(key, seconds);
            }
        } catch (Exception e) {
            log.error("Redis operation with error: ", e);
            return false;
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return true;
    }

    public static byte[] get(byte[] key) {
        byte[] value = null;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            log.error("Redis operation with error: ", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return value;
    }


    /**
     * 向缓存中设置对象
     *
     * @param key key
     * @param obj value
     * @return boolean
     */
    public static boolean set(String key, Object obj, Integer seconds) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            ObjectMapper mapper = new ObjectMapper();
            String value = mapper.writeValueAsString(obj);
            jedis.set(SafeEncoder.encode(key), SafeEncoder.encode(value));
            if (seconds != null) {
                jedis.expire(key, seconds);
            }
            return true;
        } catch (Exception e) {
            log.error("redis set value error: ", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return false;
    }

    /**
     * 向缓存中设置对象
     *
     * @param key        key
     * @param obj        value
     * @param isCompress 是否启用压缩
     * @return boolean
     */
    public static boolean set(String key, Object obj, Integer seconds, Boolean isCompress) {
        if (isCompress) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String value = mapper.writeValueAsString(obj);
                value = GZipUtils.compressStr(value);
                return set(key, value, seconds);
            } catch (JsonProcessingException e) {
                log.error("redis set value error: ", e);
            }
        } else {
            return set(key, obj, seconds);
        }
        return false;
    }

    /**
     * 向缓存中设置对象
     *
     * @param key   key
     * @param value value
     * @return boolean
     */
    public static boolean set(String key, String value, Integer seconds) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(SafeEncoder.encode(key), SafeEncoder.encode(value));
            if (seconds != null) {
                jedis.expire(key, seconds);
            }
            return true;
        } catch (Exception e) {
            log.error("redis set value error: ", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return false;
    }

    /**
     * 移除缓存中设置对象
     *
     * @param keys 被删除的KEYS
     * @return Long 被删除个数
     */
    public static Long del(String... keys) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.del(keys);
        } catch (Exception e) {
            log.error("redis delete value error: ", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 根据key 获取对象
     *
     * @param key key
     * @return T
     */
    public static <T> T get(String key, Class<T> clazz) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            String v = jedis.get(key);
            if (StringUtils.isNotEmpty(v)) {
                v = GZipUtils.decompressStr(v);
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(v, clazz);
            }
        } catch (Exception e) {
            log.error("redis get value error, key: [" + key + "], info: ", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 根据key值得到String类型的返回值
     *
     * @param key key
     * @return String
     */
    public static String get(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            String v = jedis.get(key);
            if (StringUtils.isNotEmpty(v)) {
                return GZipUtils.decompressStr(v);
            }
        } catch (Exception e) {
            log.error("redis get value error: ", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return null;
    }

    public static Boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            log.error("redis get value error: ", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * redis的list操作：
     * 把元素插入到列表的尾部
     *
     * @param key     KEY
     * @param strings 要插入的值，变参
     * @return 返回插入后list的大小
     */
    public static Long rpush(String key, String... strings) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.rpush(key, strings);
        } catch (Exception e) {
            log.error("往redis中rpush对象出错:", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * redis的list操作：
     * 移除并返回列表 key 的头元素
     *
     * @param key key
     * @return String
     */
    public static String lpop(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpop(key);
        } catch (Exception e) {
            log.error("从redis中lpop对象出错:", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * redis的list操作：
     * 根据开始与结束下标取list中的值
     *
     * @param key   KEY
     * @param start 开始下标
     * @param end   结束下标
     * @return List<String>
     */
    public static List<String> lrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            log.error("从redis中lrange对象出错:", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * redis的list操作：
     * 取列表的长度
     *
     * @param key key
     * @return Long
     */
    public static Long llen(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.llen(key);
        } catch (Exception e) {
            log.error("从redis中llen对象出错:", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * redis的list操作：
     * 根据值移除list中的元素
     *
     * @param key   KEY
     * @param count ：
     *              count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
     *              count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
     *              count = 0 : 移除表中所有与 value 相等的值。
     * @param value 要删除的值
     * @return 返回被移除的个数
     */
    public static Long lrem(String key, long count, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lrem(key, count, value);
        } catch (Exception e) {
            log.error("从redis中lrem对象出错:", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return null;
    }

    public static boolean setLong(String key, Long value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return "OK".equals(jedis.set(key, String.valueOf(value)));
        } catch (Exception e) {
            log.error("保存浮点数出错:", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return false;
    }

    public static Long getLong(String key) {
        String result = get(key);
        return result == null ? null : Long.valueOf(result);
    }

    public static Long incr(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.incr(key);
        } catch (Exception e) {
            log.error("从redis中lrem对象出错:", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return null;
    }

    public static Long incrBy(String key, Long increment) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.incrBy(key, increment);
        } catch (Exception e) {
            log.error("从redis中lrem对象出错:", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return null;
    }

    public static Long hashSet(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hset(key, field, value);
        } catch (Exception e) {
            log.error("从redis设置hash对象键值出错:", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return -1L;
    }

    public static Long hashSetLong(String key, String field, Long value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hset(key, field, String.valueOf(value));
        } catch (Exception e) {
            log.error("从redis设置hash对象键值出错:", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return -1L;
    }

    public static Long hashIncrBy(String key, String field, Long increment) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hincrBy(key, field, increment);
        } catch (Exception e) {
            log.error("从redis设置hash对象键值出错:", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return -1L;
    }

    public static Map<String, String> hashGetAll(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hgetAll(key);
        } catch (Exception e) {
            log.error("从redis设置hash对象键值出错:", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return null;
    }

    public static Map<String, Long> hashGetAllLong(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hgetAll(key).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> Long.valueOf(entry.getValue())));
        } catch (Exception e) {
            log.error("redis get hash all error: ", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return null;
    }

    public static Set<String> hashKeys(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hkeys(key);
        } catch (Exception e) {
            log.error("redis get hash keys error: ", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return null;
    }

    public static Long hashDelAll(String key, String... fields) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hdel(key, fields);
        } catch (Exception e) {
            log.error("redis delete hash value error: ", e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return null;
    }

    public static void setProcessMsg(String key, ProcessMsg pm) {
        if (StringUtils.isNotEmpty(key)) {
            set(key, pm, 60 * 60 * 24);
        }
    }

    public static void setProcessMsg(String key, ProcessMsg pm, Integer time) {
        if (StringUtils.isNotEmpty(key)) {
            set(key, pm, time);
        }
    }

    public static ProcessMsg getProcessMsg(String key) {
        return get(key, ProcessMsg.class);
    }
}