package com.sunny.pms.controller;


import com.sunny.pms.entity.LsUser;
import com.sunny.pms.entity.Student;
import com.sunny.pms.result.ResponseData;
import com.sunny.pms.service.LsUserService;
import com.sunny.pms.service.StudentService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/pms")
public class LsUserController {
    @Resource
    private LsUserService lsUserService;
    @Resource
    private StudentService studentService;
    @Resource
    private RedisTemplate redisTemplate;
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public ResponseData getList(){
        List<Student> list=studentService.getList();
        redisTemplate.opsForValue().set("Student",list);
        return new ResponseData(list);
    }
    @RequestMapping(value = "/setList", method = RequestMethod.GET)
    public ResponseData setList(){

       Object list= redisTemplate.opsForValue().get("lsuser");
        return new ResponseData(list);
    }
}
