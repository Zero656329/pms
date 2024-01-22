package com.sunny.pms.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitmqConfig {
    private static final Logger log= LoggerFactory.getLogger(RabbitmqConfig.class);
    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;


    //单一消费者
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        return factory;
    }

    //多个消费者
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory,connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        factory.setConcurrentConsumers(10);
        factory.setMaxConcurrentConsumers(15);
        factory.setPrefetchCount(10);
        return factory;
    }
    @Bean
    public RabbitTemplate rabbitTemplate(){
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
            }
        });
        return rabbitTemplate;
    }

    @Bean
    public Queue queue(){
        return new Queue("pmsqueues");
    }
    @Bean
    public DirectExchange basicExchange(){
        return new DirectExchange("pmsexchange",true,false);
    }

    //创建绑定
    @Bean
    public Binding basicBinding(){
        return BindingBuilder.bind(queue()).to(basicExchange()).with("pmskey");
    }





    //创建死信队列
    @Bean
    public Queue basicDeadQueue() {
        Map<String, Object> args = new HashMap();
        args.put("x-dead-letter-exchange", "pms.real.exchange");
        args.put("x-dead-letter-routing-key", "pms.real.key");

        //设定TTL，单位为ms，在这里指的是10s
        args.put("x-message-ttl", 60000);
        return new Queue("pms.dead.queue", true, false, false, args);
    }

    //创建“基本消息模型”的基本交换机 - 面向生产者
    @Bean
    public TopicExchange basicProducerExchange() {
        return new TopicExchange("pms.dead.exchange", true, false);
    }

    //创建“基本消息模型”的基本绑定-基本交换机+基本路由 - 面向生产者
    @Bean
    public Binding basicProducerBinding() {
        return BindingBuilder.bind(basicDeadQueue()).to(basicProducerExchange()).with("pms.dead.key");
    }

    //创建真正队列 - 面向消费者
    @Bean
    public Queue realConsumerQueue() {
        return new Queue("pms.real.queue", true);
    }

    //创建死信交换机
    @Bean
    public TopicExchange basicDeadExchange() {
        return new TopicExchange("pms.real.exchange", true, false);
    }

    //创建死信路由及其绑定
    @Bean
    public Binding basicDeadBinding() {
        return BindingBuilder.bind(realConsumerQueue()).to(basicDeadExchange()).with("pms.real.key");
    }

}
