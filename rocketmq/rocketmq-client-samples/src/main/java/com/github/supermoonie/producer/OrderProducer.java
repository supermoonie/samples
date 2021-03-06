package com.github.supermoonie.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * @author supermoonie
 * @since 2020-01-25
 */
public class OrderProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("group-1");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        for (int i = 0; i < 4; i++) {
            String orderId = String.valueOf(i);
            Message message = new Message("topic-1", "tag-1", orderId, ("order: " + orderId).getBytes(StandardCharsets.UTF_8));
            SendResult result = producer.send(message, (list, msg, o) -> {
                int id = (int) o;
                long index = id % list.size();
                System.out.println("index: " + index);
                return list.get((int) index);
            }, 1);
            System.out.println(result);
        }
        producer.shutdown();
    }
}
