package com.sunny.pms.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    /**
     * 监听并消费队列中的消息-在这里采用单一容器工厂实例即可
     */
    @RabbitListener(queues = "queue_demo")
    public void consumeMsg(@Payload byte[] msg) {
        try {
            String message = new String(msg, "utf-8");

            log.info("基本消息模型-消费者-监听消费到消息：{} ", message);


        } catch (Exception e) {
            log.error("基本消息模型-消费者-发生异常：", e.fillInStackTrace());
        }
    }
}
