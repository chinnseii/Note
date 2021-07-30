/*
 * @Date: 2021-07-21 09:23:19
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-07-30 13:03:59
 * @FilePath: \note\src\main\java\com\cloud\note\entity\UserInfo.java
 */
package com.cloud.note.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfo {

    private String userMobile;
    private String profilePhoto;
    private String signature;
    private String follow;
    private String fan;
    private String star;
    private String level;
    private String exp;
    private String updateTime;

}
