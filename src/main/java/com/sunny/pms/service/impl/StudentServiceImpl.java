package com.sunny.pms.service.impl;

import com.sunny.pms.dao.LsUserDao;
import com.sunny.pms.dao.StudentDao;
import com.sunny.pms.entity.LsUser;
import com.sunny.pms.entity.Student;
import com.sunny.pms.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentDao studentDao;

    @Override
    public List<Student> getList() {
        return studentDao.selectAll();
    }
}
