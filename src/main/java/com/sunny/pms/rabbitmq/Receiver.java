package com.sunny.pms.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.pms.entity.DeadInfo;
import com.sunny.pms.utils.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    private static final Logger log = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    public ObjectMapper objectMapper;
    @Autowired
    public MailUtil mailUtil;

    //监听并消费队列中的消息-在这里采用单一容器工厂实例即可
    @RabbitListener(queues = "pmsqueues")
    public void consumeMsg(@Payload String message) {
        try {


            log.info("基本消息模型-消费者-监听消费到消息：{} ", message);


        } catch (Exception e) {
            log.error("基本消息模型-消费者-发生异常：", e.fillInStackTrace());
        }
    }


    @RabbitListener(queues = "pms.real.queue",containerFactory = "singleListenerContainer")
    public void deadMsg(@Payload DeadInfo info){
        try {

            mailUtil.sendAttachFileMail(info.getAddr(),"","测试",info.getMsg());

            log.info("死信队列实战-监听真正队列-消费队列中的消息,监听到消息内容为：{}",info);

            //TODO:用于执行后续的相关业务逻辑

        }catch (Exception e){
            log.error("死信队列实战-监听真正队列-消费队列中的消息 - 面向消费者-发生异常：{} ",info,e.fillInStackTrace());
        }
    }
}
