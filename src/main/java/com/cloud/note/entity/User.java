/*
 * @Date: 2021-07-15 16:24:24
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-17 17:52:09
 * @FilePath: \note\src\main\java\com\cloud\note\entity\User.java
 */
package com.cloud.note.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("User")
public class User {
    @TableId(value = "user_mobile")
    private String user_mobile;
    private String password;
    private String nick_name;
    private String lock_flg;
    private String create_time;
    private String login_time;
    private String update_time;
}
