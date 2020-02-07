package com.github.supermoonie;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author supermoonie
 * @since 2020-02-06
 */
@SpringBootTest(classes = ProducerApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class ProducerApplicationTest {

    @Autowired
    private RocketMQTemplate template;

    @Test
    public void test() {
        template.convertAndSend("topic-1", "hello mq!");
        log.info("消息发送成功");
    }
}
