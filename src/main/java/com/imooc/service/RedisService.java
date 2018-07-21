package com.imooc.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 事务
     */
    public void setMultiValues() {
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {

                while (true){
                    redisConnection.watch("name".getBytes());
                    redisConnection.multi();
                    redisConnection.set("key1".getBytes(),"value1".getBytes());
                    redisConnection.set("key2".getBytes(),"value2".getBytes());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    redisConnection.set("key3".getBytes(),"value3".getBytes());
                    List list = redisConnection.exec();
                    if(list != null)
                        return list;
                }

            }
        });
    }





}
