package com.github.supermoonie.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @author supermoonie
 * @since 2020-02-07
 */
@Service
@RocketMQMessageListener(topic = "topic-1", consumerGroup = "string_consumer")
@Slf4j
public class StringConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        log.info("接收到消息：{}", s);
    }
}
