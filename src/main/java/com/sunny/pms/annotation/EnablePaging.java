package com.sunny.pms.annotation;

import java.lang.annotation.*;

//分页注解
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnablePaging {
}