/*
 * @Date: 2021-07-27 16:14:48
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-07-27 16:20:12
 * @FilePath: \note\src\main\java\com\cloud\note\annotation\PassToken.java
 */
package com.cloud.note.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/**
 * @description: 自定义注解:跳过验证
 */
public @interface PassToken {
    boolean required() default true;
}
