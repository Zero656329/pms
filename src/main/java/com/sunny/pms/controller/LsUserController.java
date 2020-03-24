package com.sunny.pms.controller;


import com.sunny.pms.entity.LsUser;
import com.sunny.pms.result.ResponseData;
import com.sunny.pms.service.LsUserService;
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
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public ResponseData getbook(){
        List<LsUser> list=lsUserService.getList();
        return new ResponseData(list);
    }
}
