/*
 * @Date: 2021-07-21 09:23:19
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-18 16:06:52
 * @FilePath: \note\src\main\java\com\cloud\note\entity\UserInfo.java
 */
package com.cloud.note.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;


@Data
@TableName("UserInfo")
public class UserInfo {
    @TableId(value = "user_mobile")
    private String user_mobile;
    private String profile_photo;
    private String signature;
    private String follow;
    private String fan;
    private String star;
    private String user_level;
    private String exp;
    private String update_time;
}
