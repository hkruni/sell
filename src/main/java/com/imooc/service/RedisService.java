package com.imooc.service;


import com.imooc.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JedisPool jedisPool;



//    public String acquireLockWithTimeout(String lockName, long acquireTimeout, long lockTimeout) {
//
//        String identifier = UUID.randomUUID().toString();
//        String lockKey = "lock:" + lockName;
//
//        int lockExpire = (int)(lockTimeout / 1000);
//        long end = System.currentTimeMillis() + acquireTimeout;
//
//        while (System.currentTimeMillis() < end) {
//            if(redisTemplate.opsForValue().setIfAbsent(lockKey,identifier) == true) {
//                redisTemplate.expire(lockKey,lockExpire, TimeUnit.SECONDS);
//                return identifier;
//            }
//            if (redisTemplate.getExpire(lockKey) == -1) {
//                redisTemplate.expire(lockKey,lockExpire, TimeUnit.SECONDS);
//            }
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public boolean releaseLock( String lockName, String identifier) {
//        String lockKey = "lock:" + lockName;
//
//        while (true){
//            redisTemplate.watch(lockKey);
//            if (identifier.equals(redisTemplate.opsForValue().get(lockKey))){
//                redisTemplate.multi();
//                redisTemplate.delete(lockKey);
//                List<Object> results = redisTemplate.exec();
//                if (results == null){
//                    continue;
//                }
//                return true;
//            }
//
//            redisTemplate.unwatch();
//            break;
//        }
//
//        return false;
//    }
    public String acquireLockWithTimeout(String lockName, long acquireTimeout, long lockTimeout) {

        String identifier = UUID.randomUUID().toString();
        String lockKey = "lock:" + lockName;

        int lockExpire = (int)(lockTimeout / 1000);
        long end = System.currentTimeMillis() + acquireTimeout;

        Jedis jedis = jedisPool.getResource();

        try {
            while (System.currentTimeMillis() < end) {
                if(jedis.setnx(lockKey,identifier) == 1) {
                    jedis.expire(lockKey,lockExpire);
                    return identifier;
                }
                if (jedis.ttl(lockKey) == -1) {
                    jedis.expire(lockKey,lockExpire);
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }finally {
            jedis.close();
        }

        return null;
    }

    public boolean releaseLock( String lockName, String identifier) {
        String lockKey = "lock:" + lockName;
        Jedis jedis = jedisPool.getResource();
        System.out.println("jedis:" + jedis);
        try {
            while (true){
                jedis.watch(lockKey);
                if (identifier.equals(jedis.get(lockKey))){
                    Transaction t = jedis.multi();
                    t.del(lockKey);
                    List<Object> results = t.exec();
                    if (results == null){
                        continue;
                    }
                    return true;
                }
                break;
            }
        }finally {
            jedis.close();
        }


        return false;
    }

    public <T>boolean set(String prefix,String key,T value) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            String value1 = beanToString(value);

            if (value1 == null || value1.length() == 0) {
                return false;
            }
            jedis.set(key,value1);
            return true;

        }finally {
            if (jedis != null) {
                jedis.close();
            }

        }

    }

    public <T>boolean rpush(String prefix,String key,T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String value1 = beanToString(value);
            if (value1 == null || value1.length() == 0) {
                return false;
            }
            jedis.rpush(prefix + key,value1);
            return true;
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    public <T>T pop(String prefix,String key,int direction,Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String  value = (direction == 0) ? jedis.lpop(prefix + key) : jedis.rpop(prefix + key);
            T t = stringToBean(value,clazz);
            return t;
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public <T>List<T> range(String prefix,String key,long start,long end,Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<String> list = jedis.lrange(prefix + key,start,end);
            List<T> ll = new ArrayList<T>();
            for (String s : list) {
                ll.add(stringToBean(s,clazz));
            }

            return ll;
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void zadd(String prefix,String key,double score,String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.zadd(prefix + key,score,member);
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public long zcount(String prefix,String key,double min ,double max) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zcount(prefix + key,min,max);
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    public static <T> String beanToString(T value) {
        if(value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return ""+value;
        }else if(clazz == String.class) {
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return ""+value;
        }else {
            return JsonUtil.obj2String(value);
        }
    }

    public static <T> T stringToBean(String value,Class<T> c) {
        if(value == null) {
            return null;
        }
        T o = JsonUtil.string2Obj(value,c);
        return o;

    }






    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        System.out.println(time / 1000);
    }



}
