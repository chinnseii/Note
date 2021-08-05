/*
 * @Date: 2021-07-15 16:24:24
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-04 15:27:03
 * @FilePath: \note\src\main\java\com\cloud\note\service\impl\UserServiceImpl.java
 */
package com.cloud.note.service.impl;

import com.cloud.note.dao.UserMapper;
import com.cloud.note.entity.User;
import com.cloud.note.service.UserService;
import com.cloud.note.utils.MD5Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String userMobile, String password) {
        String passwordMd5 = MD5Util.MD5Encode(userMobile + password, "UTF-8");
        User user = new User();
        user.setUserMobile(userMobile);
        user.setUserPassword(passwordMd5);
        return userMapper.login(user);
    }

    @Override
    public int insert(String userNickName, String userMobile, String userPassword) {
        String passwordMd5 = MD5Util.MD5Encode(userMobile + userPassword, "UTF-8");
        User user = new User();
        user.setNickName(userNickName);
        user.setUserMobile(userMobile);
        user.setUserPassword(passwordMd5);
        return userMapper.insert(user);
    }

    @Override
    public int checkMobile(String userMobile) {
        return userMapper.checkMobile(userMobile);
    }


    @Override
    public int updateLockFlg(String userMobile, int a) {
        if(a==0){
            return userMapper.resetLock(userMobile);
        }
        return userMapper.updateLock(userMobile);
    }


}
