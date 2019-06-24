package cn.keking.project.cancel.mq;

import cn.keking.project.cancel.entity.CancelRecordInfo;
import com.alibaba.fastjson.JSON;
import cn.keking.project.cancel.configuration.MqConfiguration;
import cn.keking.project.cancel.repository.CancelRecordInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;


/**
 * @Author: zwg15
 * @Date: 2019/6/19
 * @Version: V1.0
 * @Description: 取消消息mq接受者
 */
@Slf4j
@RabbitListener(queues = MqConfiguration.QUEUE)
public class CancelReceiver {



    private CancelRecordInfoRepository cancelDataInfoRepository;

    public CancelReceiver(CancelRecordInfoRepository cancelDataInfoRepository) {
        this.cancelDataInfoRepository = cancelDataInfoRepository;
    }

    @RabbitHandler
    public void process(String message) {
        log.info("receive message"+message);
        if(StringUtils.isNotBlank(message)){
            //对接收到的消息进行处理
            try{
                CancelRecordInfo cancelDataInfo = JSON.parseObject(message, CancelRecordInfo.class);
                cancelDataInfoRepository.save(cancelDataInfo);
            }catch (Exception e){
                 log.error("save cancel_data_info error ------"+e.getMessage(), e);

            }


        }




    }

}