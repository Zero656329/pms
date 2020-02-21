package com.sunny.pms.service.impl;

import com.tong.cins.dao.LsUserDao;
import com.tong.cins.entity.LsUser;
import com.tong.cins.service.LsUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LsUserServiceImpl implements LsUserService {
@Resource
private LsUserDao lsUserDao;

    @Override
    public List<LsUser> getList() {
        return lsUserDao.selectAll();
    }
}
