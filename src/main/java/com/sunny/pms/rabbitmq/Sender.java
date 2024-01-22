package com.sunny.pms.rabbitmq;

import com.sunny.pms.entity.DeadInfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Sender {



    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;


 //发送消息
    public void sendMsg(String message){

            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange("pmsexchange");
                rabbitTemplate.setRoutingKey("pmskey");

                Message msg= MessageBuilder.withBody(message.getBytes("utf-8"))
                        .setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();

                rabbitTemplate.convertAndSend(msg);

                log.info("基本消息模型-生产者-发送消息：{} ",message);
            }catch (Exception e){
                log.error("基本消息模型-生产者-发送消息发生异常：{} ",message,e.fillInStackTrace());
            }
        }
    //发送对象类型的消息入死信队列
    public void sendDeadMsg(DeadInfo info){
        try {
            //设置消息的传输格式-Json格式
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            //设置基本交换机
            rabbitTemplate.setExchange("pms.dead.exchange");
            //设置基本路由
            rabbitTemplate.setRoutingKey("pms.dead.key");

            //发送对象类型的消息
            rabbitTemplate.convertAndSend( info, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    //获取消息属性对象
                    MessageProperties messageProperties=message.getMessageProperties();
                    //设置消息的持久化策略
                    messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    //设置消息头-即直接指定发送的消息所属的对象类型
                    messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,DeadInfo.class);

                    //设置消息的TTL - 当消息和队列同时都设置了TTL时，则取较短时间的值
                    messageProperties.setExpiration(String.valueOf(60000));

                    return message;
                }
            });
            //打印日志
            log.info("死信队列实战-发送对象类型的消息入死信队列-内容为：{} ",info);

        }catch (Exception e){
            log.error("死信队列实战-发送对象类型的消息入死信队列-发生异常：{} ",info,e.fillInStackTrace());
        }
    }
}
