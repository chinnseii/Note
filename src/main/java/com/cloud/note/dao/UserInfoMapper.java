/*
 * @Date: 2021-07-21 10:54:29
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-05 17:32:10
 * @FilePath: \note\src\main\java\com\cloud\note\dao\UserInfoMapper.java
 */
package com.cloud.note.dao;

import com.cloud.note.entity.UserInfo;

public interface UserInfoMapper {
     // ユーザー情報初期化
     int initUserInfo(String userMobile);

     //ユーザー情報取得
     UserInfo getUserInfo(String userMobile);

     //ユーザー個人説明を更新
     int updateUserSignature(UserInfo userInfo);

}
