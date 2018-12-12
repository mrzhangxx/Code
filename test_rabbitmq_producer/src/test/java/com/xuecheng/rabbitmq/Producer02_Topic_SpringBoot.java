package com.xuecheng.rabbitmq;

import com.xuecheng.rabbitmq.config.RabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/12 下午7:23
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer02_Topic_SpringBoot {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Test
    public void testEmail(){
        String message = "send message to user";
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM,"inform.email",message);
    }
}
