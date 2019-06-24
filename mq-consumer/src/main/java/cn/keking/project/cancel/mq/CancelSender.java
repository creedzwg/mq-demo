package cn.keking.project.cancel.mq;

import cn.keking.project.cancel.configuration.MqConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;

/**
 * @Author: zwg15
 * @Date: 2019/6/19
 * @Version: V1.0
 * @Description: 取消消息mq发送者
 */
@Slf4j
public class CancelSender {


    private AmqpTemplate rabbitTemplate;

    public CancelSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String message) {

        this.rabbitTemplate.convertAndSend(MqConfiguration.EXCHANGE,MqConfiguration.BINDINGKEY, message);
    }

}