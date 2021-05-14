package com.sunny.pms.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.pms.config.RabbitmqConfig;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    private static final Logger log= LoggerFactory.getLogger(Sender.class);


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;


    /**
     * 发送消息
     * @param message
     */
    public void sendMsg(String message){

            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange("exchange_demo");
                rabbitTemplate.setRoutingKey("key_demo");

                Message msg= MessageBuilder.withBody(message.getBytes("utf-8"))
                        .setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();

                rabbitTemplate.convertAndSend(msg);

                log.info("基本消息模型-生产者-发送消息：{} ",message);
            }catch (Exception e){
                log.error("基本消息模型-生产者-发送消息发生异常：{} ",message,e.fillInStackTrace());
            }
        }

}
