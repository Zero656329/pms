package com.sunny.pms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.pms.entity.DeadInfo;
import com.sunny.pms.rabbitmq.Sender;
import com.sunny.pms.utils.MailUtil;
import com.sunny.pms.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
/*
import org.springframework.data.redis.core.RedisTemplate;
*/
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
class PmsApplicationTests {


	@Autowired
	private ObjectMapper objectMapper;
	/*@Autowired
	private RedisTemplate redisTemplate;*/
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private Sender basicPublisher;
	@Autowired
	public MailUtil mailUtil;

	@Test
	public void test1() throws Exception{
		String msg="社会主义好";
		log.info(msg);

		mailUtil.sendAttachFileMail("hwj656329@163.com","","测试",msg);
	}


	@Test
	public void test2() throws Exception{
		/*//定义实体对象1
		DeadInfo info=new DeadInfo(2,"邵柠柠傻逼250");
		info.setAddr("hwj656329@163.com");
		//发送实体对象1消息入死信队列
		log.info("发生");
		basicPublisher.sendDeadMsg(info);
		Thread.sleep(70000);*/
	}
	@Test
	public void test3() throws Exception{
	/*	redisTemplate.opsForValue().set("test","测试Redis12");
		redisUtil.set("123445","1224565vddfgvcdee");*/
	}
}
