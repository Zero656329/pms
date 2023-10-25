package com.sunny.pms.aop;

import com.github.pagehelper.PageHelper;

import com.sunny.pms.base.BaseDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//aop分页注解具体方法
@Aspect
@Component
public class EnablePaging {
    @Pointcut("@annotation(com.sunny.pms.annotation.EnablePaging)")
    public void serviceAspect() {

    }

    @Before(value = "serviceAspect()")
    public void Before(JoinPoint point) throws Throwable {
      /*  System.out.print("开始分页");
        Object[] args = point.getArgs();
        BaseDto baseDto = (BaseDto) args[0];
        PageHelper.startPage(baseDto.getPageNo(), baseDto.getPageSize());
        System.out.print("结束分页");*/


    }

}
