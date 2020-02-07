package com.github.supermoonie.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;

import java.util.concurrent.TimeUnit;

/**
 * @author supermoonie
 * @since 2020-01-25
 */
public class OrderConsumer {

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group-1");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe("topic-1", "tag-1");
        consumer.registerMessageListener((MessageListenerOrderly) (list, consumeOrderlyContext) -> {
            list.forEach(msg -> System.out.println(new String(msg.getBody())));
            return ConsumeOrderlyStatus.SUCCESS;
        });
        consumer.start();
        TimeUnit.SECONDS.sleep(5);
        consumer.shutdown();
    }
}
