/*
 * @Date: 2021-07-21 10:51:04
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-04 15:05:50
 * @FilePath: \note\src\main\java\com\cloud\note\service\UserInfoService.java
 */
package com.cloud.note.service;

import com.cloud.note.entity.UserInfo;

public interface UserInfoService {
    //ユーザー情報初期化   
    int initUserInfo(String userMobile);

    //ユーザー情報取得
    UserInfo getUserInfo(String userMobile);
}
