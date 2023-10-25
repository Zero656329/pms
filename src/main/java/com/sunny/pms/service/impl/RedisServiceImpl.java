package com.sunny.pms.service.impl;

import com.sunny.pms.service.RedisService;


public class RedisServiceImpl implements RedisService {
    @Override
    public void pushBuy(String cname, Integer cout) {

    }

    @Override
    public void getBuy(String cname) {

    }
    /* @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void pushBuy(String cname, Integer cout) {
        for (int i = 0; i <cout ; i++) {
            redisTemplate.opsForList().leftPush(cname,1);
        }
        System.out.println(redisTemplate.opsForList().range(cname,0,-1));
    }

    @Override
    public void getBuy(String cname) {
        if (redisTemplate.opsForList().leftPop(cname).equals(1)) {

        }else{

        }
    }*/
}
