package cn.keking.project.cancel.configuration;

import cn.keking.project.cancel.mq.CancelReceiver;
import cn.keking.project.cancel.mq.CancelSender;
import cn.keking.project.cancel.repository.CancelRecordInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: zwg15
 * @Date: 2019/6/19
 * @Version: V1.0
 * @Description: 自动配置类
 */
@Slf4j
@Configuration
@AutoConfigureOrder(Integer.MAX_VALUE)
@EnableJpaRepositories(basePackages = {"cn.keking.project.cancel.repository"})
@EnableTransactionManagement
@Import({MqConfiguration.class})
@ComponentScan(basePackages = {"cn.keking.project.cancel.controller","cn.keking.project.cancel.service"})
@ConditionalOnClass({Repository.class,AmqpTemplate.class})
public class CancelConfiguration {


    /**
     *  cancel mq message receive
     * @return
     */
    @Bean
    public CancelReceiver receiver(CancelRecordInfoRepository cancelDataInfoRepository) {
        return new CancelReceiver(cancelDataInfoRepository);
    }

    /**
     *  cancel mq message send
     * @param amqpTemplate
     * @return
     */
    @Bean
    public CancelSender sender(AmqpTemplate amqpTemplate) {
        return new CancelSender(amqpTemplate);
    }





}
