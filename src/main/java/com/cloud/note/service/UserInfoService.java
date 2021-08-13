/*
 * @Date: 2021-07-21 10:51:04
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-13 11:08:42
 * @FilePath: \note\src\main\java\com\cloud\note\service\UserInfoService.java
 */
package com.cloud.note.service;

import com.cloud.note.entity.UserInfo;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

public interface UserInfoService {
      
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
     * @throws JSONException
     * @throws Exception
     */
    JSONObject updateUserProfilePhoto(UserInfo userInfo,JSONObject avatar,MultipartFile multipartFile) throws Exception ;
}
