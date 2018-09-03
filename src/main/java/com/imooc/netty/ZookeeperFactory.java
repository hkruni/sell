package com.imooc.netty;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by hukai on 2018/8/21.
 */
public class ZookeeperFactory {

    public static CuratorFramework client = null;

    public static CuratorFramework create() {
        if (client == null) {
            client = CuratorFrameworkFactory.builder()
                    .connectString("112.35.29.127:2181")
                    .sessionTimeoutMs(5000)
                    .retryPolicy(new ExponentialBackoffRetry(1000,3))
                    .build();
            client.start();
        }

        return client;
    }

}
