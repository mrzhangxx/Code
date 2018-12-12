package com.xuecheng.rabbitmq.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/12 下午7:42
 */
@Configuration
public class ReceiveHandler {

    @RabbitListener(queues = RabbitmqConfig.QUEUE_INFORM_EMAIL)
    public void send_Email(String msg, Message message, Channel channel){
        System.out.println("receive message is : "+ msg);
    }
}
