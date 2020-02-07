package com.github.supermoonie.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;

/**
 * @author supermoonie
 * @since 2020-01-27
 */
public class TransactionConsumer {

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group-1");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe("topic-1", "*");
        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            list.forEach(msg -> System.out.println(new String(msg.getBody())));
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
    }
}
