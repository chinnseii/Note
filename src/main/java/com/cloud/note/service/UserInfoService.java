/*
 * @Date: 2021-07-21 10:51:04
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-07-30 11:31:25
 * @FilePath: \note\src\main\java\com\cloud\note\service\UserInfoService.java
 */
package com.cloud.note.service;

public interface UserInfoService {
    //ユーザー情報初期化   
    int initUserInfo(String userMobile);

    //ユーザー情報取得
    void getUserInfo(String userMobile);
}
