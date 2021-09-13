/*
 * @Date: 2021-07-21 10:51:04
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-09-08 16:24:06
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
     * @description: ユーザーノート数量更新
     * @param {UserInfo} userInfo
     * @return {*}
     */
    int updateUserNote(String userMobile,Boolean a,int b);

        /**
     * @description: ユーザー科目数量更新
     * @param {UserInfo} userInfo
     * @return {*}
     */
    int updateUserCategory(String userMobile,Boolean a,int b);

    /**
     * @description: ユーザーアバター更新
     * @param {UserInfo} userInfo
     * @return {*}
     * @throws JSONException
     * @throws Exception
     */
    JSONObject updateUserProfilePhoto(UserInfo userInfo, JSONObject avatar, MultipartFile multipartFile)
            throws Exception;

    /**
     * @description: 用户经验值增加
     * @param {String} userMobileS
     * @return {*}
     */
    boolean updateExp(String userMobile, int plusExp) throws Exception;

    /**
     * @description: 用户经验值减少
     * @param {String} userMobile
     * @param {int}    plusExp
     * @return {*}
     */
    boolean reduceExp(String userMobile, int plusExp) throws Exception;
}
