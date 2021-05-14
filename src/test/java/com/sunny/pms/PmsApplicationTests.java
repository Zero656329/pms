package com.sunny.pms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.pms.rabbitmq.Sender;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class PmsApplicationTests {

	private static final Logger log= LoggerFactory.getLogger(PmsApplicationTests.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private Sender basicPublisher;

	@Test
	public void test1() throws Exception{
		String msg="这是一串字符串消息123";
		basicPublisher.sendMsg(msg);
	}
}
