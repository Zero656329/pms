package com.sunny.pms.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "queue_demo")
public class Receiver {
   @RabbitHandler
    public void QueueRecevier(String Queue1){
        System.out.println("接收："+Queue1);
    }
}
