package com.sunny.pms.controller;


import com.sunny.pms.entity.LsUser;
import com.sunny.pms.entity.Student;
import com.sunny.pms.result.ResponseData;
import com.sunny.pms.service.LsUserService;
import com.sunny.pms.service.StudentService;
import com.sunny.pms.utils.MailUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/pms")
public class LsUserController {

}
