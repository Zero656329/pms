package com.sunny.pms.service.impl;

import com.sunny.pms.dao.LsUserDao;
import com.sunny.pms.entity.LsUser;
import com.sunny.pms.service.LsUserService;
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
