package com.xuecheng.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/12 下午4:38
 */

/**
 * 生产者
 */
public class Producer01 {
    //队列的名称
    private static final String QUEUE = "helloword";
    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            //1.创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            //2.设置连接工程的IP地址
            factory.setHost("127.0.0.1");
            //3.设置连接工程的端口号
            factory.setPort(5672);
            //4.设置连接工程的用户名称
            factory.setUsername("guest");
            //5.设置连接工程的密码
            factory.setPassword("guest");
            //6.设置连接工程的rabbitmq的虚拟机地址
            factory.setVirtualHost("/");
            //7。获得连接rabbitmq的连接
            connection = factory.newConnection();
            //8.获得channel
            channel = connection.createChannel();

            //7.声明一个队列
            /**
             * 参数明细
             * 1、queue 队列名称
             * 2、durable 是否持久化，如果持久化，mq重启后队列还在
             * 3、exclusive 是否独占连接，队列只允许在该连接中访问，如果connection连接关闭队列则自动删除,如果将此参数设置true可用于临时队列的创建
             * 4、autoDelete 自动删除，队列不再使用时是否自动删除此队列，如果将此参数和exclusive参数设置为true就可以实现临时队列（队列不用了就自动删除）
             * 5、arguments 参数，可以设置一个队列的扩展参数，比如：可设置存活时间
             */

            channel.queueDeclare(QUEUE,true,false,false,null);
            //8.发送消息
            /**
             * 参数明细：
             * 1、exchange，交换机，如果不指定将使用mq的默认交换机（设置为""）
             * 2、routingKey，路由key，交换机根据路由key来将消息转发到指定的队列，如果使用默认交换机，routingKey设置为队列的名称
             * 3、props，消息的属性
             * 4、body，消息内容
             */
            String msg = "hello rabbitmq";
            channel.basicPublish("",QUEUE,null,msg.getBytes());
            System.out.println("send to mq : "+msg);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(channel != null){
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
