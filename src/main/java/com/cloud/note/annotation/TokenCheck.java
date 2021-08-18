/*
 * @Date: 2021-07-27 16:19:17
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-07-27 16:21:16
 * @FilePath: \note\src\main\java\com\cloud\note\annotation\TokenCheck.java
 */
package com.cloud.note.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/**
 * @description: 自定义注解:检查是否登录
 */
public @interface TokenCheck {
    boolean required() default true;
}
