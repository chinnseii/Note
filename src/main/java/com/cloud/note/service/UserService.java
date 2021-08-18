/*
 * @Date: 2021-07-15 16:24:24
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-18 09:48:13
 * @FilePath: \note\src\main\java\com\cloud\note\service\UserService.java
 */
package com.cloud.note.service;

import com.cloud.note.entity.User;

public interface UserService {
  
    /**
     * 新用户注册
     *
     * @param userNickName
     * @param userMobile
     * @param userPassword
     * @return
     */
    int insert(String userNickName, String userMobile, String userPassword);

   /**
     * 登录
     *
     * @param userMobile
     * @param userPassword
     * @return
     */
    User login( String userMobile, String userPassword);

    /**
     * 检查手机号是否存在
     *
     * @param userMobile
     * @return
     */
    int checkMobile(String userMobile);

    /**
     * 检查是否锁定
     *
     * @param userMobile
     * @return
     */
    int checkLockFlg(String userMobile);

    /**
     * @description: 更新锁定区分
     * @param {String} userMobile
     * @param {int} a 1:flg加1，0:flg重置
     * @return {*}
     */
    int updateLockFlg(String userMobile, int a);

}
