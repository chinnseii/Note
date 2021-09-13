/*
 * @Date: 2021-07-21 09:23:19
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-09-07 18:09:08
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
    private int message;
    private long note;
    private long category;
    private long follow;
    private long fan;
    private long starred;
    private long star;
    private int user_level;
    private int exp;
    private String update_time;
}
