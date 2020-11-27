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
    @Resource
    private MailUtil mailUtil;
    @Resource
    private StudentService studentService;
    @Resource
    private RedisTemplate redisTemplate;
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public ResponseData getList() throws MessagingException {
        List<Student> list=studentService.getList();
     String content=list.stream().map(Student::getName).toString();
        mailUtil.sendAttachFileMail("958098421@qq.com","hwj656329@163.com","cc","测试",content,null);

        return new ResponseData(list);
    }
    @RequestMapping(value = "/setList", method = RequestMethod.GET)
    public ResponseData setList(){

       Object list= redisTemplate.opsForValue().get("lsuser");
        return new ResponseData(list);
    }
}
