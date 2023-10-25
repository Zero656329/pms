package com.sunny.pms.service;

import java.util.List;

public interface RedisService {
    void pushBuy(String cname, Integer cout);
    void getBuy(String cname);

}
