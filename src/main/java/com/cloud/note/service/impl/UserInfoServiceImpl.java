/*
 * @Date: 2021-07-21 10:53:11
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-07-27 15:16:45
 * @FilePath: \note\src\main\java\com\cloud\note\service\impl\UserInfoServiceImpl.java
 */
package com.cloud.note.service.impl;

import javax.servlet.http.HttpSession;

import com.cloud.note.dao.UserInfoMapper;
import com.cloud.note.entity.UserInfo;
import com.cloud.note.service.UserInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public int initUserInfo(String userMobile) {
        return userInfoMapper.initUserInfo(userMobile);
    }

    @Override
    public void getUserInfo(String userMobile) {
        UserInfo userInfo = userInfoMapper.getUserInfo(userMobile);
       
       
    }

}
