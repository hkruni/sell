package com.imooc.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    @KafkaListener(topics = {"topic1"})
    public void listen(ConsumerRecord<?, ?> record) {
        System.out.println("kafka的key: " + record.key());
        System.out.println("kafka的value: " + record.value().toString());
    }
}