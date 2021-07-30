/*
 * @Date: 2021-07-15 16:24:24
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-07-30 13:03:39
 * @FilePath: \note\src\main\java\com\cloud\note\entity\User.java
 */
package com.cloud.note.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private String userMobile;

    private String userPassword;

    private String nickName;

    private String lockedFlg;

    private String creatTime;

    private String loginTime;

    private String updateTime;

}
