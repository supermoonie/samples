package com.github.supermoonie.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * @author supermoonie
 * @since 2020-01-27
 */
public class MessageSelectorProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("group-1");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        for (int i = 0; i < 10; i ++) {
            Message message = new Message("topic-1", "tag-1", "Hello RocketMQ!".getBytes(StandardCharsets.UTF_8));
            message.putUserProperty("age", String.valueOf(i));
            message.putUserProperty("name", "foo");
            SendResult result = producer.send(message);
            System.out.println(result);
        }
        producer.shutdown();
    }
}
