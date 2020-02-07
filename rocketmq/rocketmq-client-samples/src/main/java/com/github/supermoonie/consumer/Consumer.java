package com.github.supermoonie.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;

import java.util.concurrent.TimeUnit;

/**
 * @author supermoonie
 * @since 2020-01-23
 */
public class Consumer {

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group-1");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe("topic-1", "tag-1");
        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            list.forEach(System.out::println);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        TimeUnit.SECONDS.sleep(5);
        consumer.shutdown();
    }
}
