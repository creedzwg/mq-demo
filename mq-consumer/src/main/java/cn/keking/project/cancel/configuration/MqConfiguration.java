package cn.keking.project.cancel.configuration;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zhangwengang
 * @Date: 2019/6/20
 * @Version: V1.0
 * @Description:  mq的配置类
 */
@Configuration
public class MqConfiguration {

    /** 消息交换机的名字*/
    public static final String EXCHANGE = "cancel-exchange";
    /** 队列key1*/
    public static final String QUEUE = "cancel-queue";

    /** bindingKey*/
    public static final String BINDINGKEY = "cancel-key";


    /**
     *   1.定义direct exchange，绑定queueTest
     *   2.durable="true" rabbitmq重启的时候不需要创建新的交换机
     *   3.direct交换器相对来说比较简单，匹配规则为：如果路由键匹配，消息就被投送到相关的队列
     *     fanout交换器中没有路由键的概念，他会把消息发送到所有绑定在此交换器上面的队列中。
     *     topic交换器你采用模糊匹配路由键的原则进行转发消息到队列中
     *   key: queue在该direct-exchange中的key值，当消息发送给direct-exchange中指定key为设置值时，
     *   消息将会转发给queue参数指定的消息队列
     */
    @Bean
    public DirectExchange directExchange(){
        DirectExchange directExchange = new DirectExchange(EXCHANGE,true,false);
        return directExchange;
    }
    @Bean
    public Queue cancelQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public Binding bindingCancel(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(BINDINGKEY).noargs();
    }










}
