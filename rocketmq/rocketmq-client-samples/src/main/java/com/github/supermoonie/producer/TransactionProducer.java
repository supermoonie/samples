package com.github.supermoonie.producer;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.StandardCharsets;

/**
 * @author supermoonie
 * @since 2020-01-27
 */
public class TransactionProducer {

    public static void main(String[] args) throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer("group-1");
        producer.setNamesrvAddr("localhost:9876");
        final String[] tags = {"TagA", "TagB", "TagC"};
        producer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                if (tags[0].equals(message.getTags())) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else if (tags[1].equals(message.getTags())) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } else {
                    return LocalTransactionState.UNKNOW;
                }
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println(messageExt.getTags());
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        producer.start();
        for (int i = 0; i < tags.length; i ++) {
            Message message = new Message("topic-1", tags[i], ("Hello RocketMQ! - " + i).getBytes(StandardCharsets.UTF_8));
            SendResult result = producer.sendMessageInTransaction(message, null);
            System.out.println(result);
        }
    }
}
