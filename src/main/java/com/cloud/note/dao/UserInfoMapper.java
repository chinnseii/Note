/*
 * @Date: 2021-07-21 10:54:29
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-10 15:52:21
 * @FilePath: \note\src\main\java\com\cloud\note\dao\UserInfoMapper.java
 */
package com.cloud.note.dao;

import com.cloud.note.entity.UserInfo;

public interface UserInfoMapper {
        /**
     * @description: ユーザー情報初期化 
     * @param {String} userMobile
     * @return {*}
     */
    int initUserInfo(String userMobile);
    
    /**
     * @description: ユーザー情報取得
     * @param {String} userMobile
     * @return {*}
     */
    UserInfo getUserInfo(String userMobile);

    /**
     * @description: ユーザー説明更新
     * @param {UserInfo} userInfo
     * @return {*}
     */
    int updateUserSignature(UserInfo userInfo);

    /**
     * @description: ユーザーアバター更新
     * @param {UserInfo} userInfo
     * @return {*}
     */
    int updateUserProfilePhoto(UserInfo userInfo);
     // // ユーザー情報初期化
     // int initUserInfo(String userMobile);

     // // ユーザー情報取得
     // UserInfo getUserInfo(String userMobile);

     // // ユーザー個人説明を更新
     // int updateUserSignature(UserInfo userInfo);

     // int updateUserProfilePhoto(UserInfo userInfo);

}
