package com.imooc.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


import java.lang.reflect.Method;


/**
 *注意：RedisCacheConfig这里也可以不用继承 ：CachingConfigurerSupport，也就是直接一个普通的Class就好了；
 *这里主要我们之后要重新实现 key的生成策略，只要这里修改KeyGenerator，其它位置不用修改就生效了。
 *普通使用普通类的方式的话，那么在使用@Cacheable的时候还需要指定KeyGenerator的名称;这样编码的时候比较麻烦。
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

    /*定义缓存数据 key 生成策略的bean
    包名+类名+方法名+所有参数
    */
    @Bean
    public KeyGenerator wiselyKeyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                System.out.println("generate :" + sb.toString());
                return sb.toString();
            }
        };

    }

    /*
    要启用spring缓存支持,需创建一个 CacheManager的bean，CacheManager 接口有很多实现，这里Redis的集成，
    用 RedisCacheManager这个实现类，我们需要将应用连接到它并使用某种“语言”进行交互，因此我们还需要一个连接工厂
    以及一个 Spring 和 Redis 对话要用的 RedisTemplate，这些都是 Redis 缓存所必需的配置，把它们都放在自定义
    的 CachingConfigurerSupport 中
     */
    @Bean
    public CacheManager cacheManager(
            @SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
//      cacheManager.setDefaultExpiration(60);//设置缓存保留时间（seconds）
        return cacheManager;
    }

    //1.项目启动时此方法先被注册成bean被spring管理
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        RedisSerializer<String> redisStringSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);


        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        template.setEnableTransactionSupport(true);
        template.setConnectionFactory(factory);
        //key序列化方式
        template.setKeySerializer(redisStringSerializer);
        //value序列化
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //hash 的key序列化
        template.setHashKeySerializer(redisStringSerializer);
        //hash 的value序列化
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        return template;
    }
}