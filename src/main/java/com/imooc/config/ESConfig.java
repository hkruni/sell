package com.imooc.config;


import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class ESConfig {

    @Value("${elasticsearch.host}")
    private String esHost;

    @Value("${elasticsearch.port}")
    private int esPort;

    @Value("${elasticsearch.cluster.name}")
    private String esName;

    @Bean
    public TransportClient esClient() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", this.esName)//指定集群名称
                //.put("client.transport.sniff", true)//嗅探集群状态,监听集群中加入的新节点
                .build();

        //设置客户端连接的集群节点
        InetSocketTransportAddress master = new InetSocketTransportAddress(
                InetAddress.getByName(esHost), esPort
        );

        //创建客户端
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(master);

        return client;
    }

}
