package com.lsy.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author : Lo Shu-ngan
 * @Classname LoginRequired
 * @Description TODO
 * @Date 2020/04/27 22:28
 */
@Target(ElementType.METHOD) //用在方法上
@Retention(RetentionPolicy.RUNTIME) //运行时有效
public @interface LoginRequired {
}
