/*
 * @Date: 2021-07-15 16:24:24
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-18 13:51:42
 * @FilePath: \note\src\main\java\com\cloud\note\service\impl\UserServiceImpl.java
 */
package com.cloud.note.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cloud.note.dao.UserMapper;
import com.cloud.note.entity.User;
import com.cloud.note.service.UserService;
import com.cloud.note.utils.MD5Util;
import com.cloud.note.utils.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    /**
     * @description: ログイン
     * @param {String} userMobile
     * @param {String} password
     * @return {*}
     */
    public User login(String userMobile, String password) {
        String passwordMd5 = MD5Util.MD5Encode(userMobile + password, "UTF-8");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_mobile", userMobile);
        queryWrapper.eq("password", passwordMd5);
        User user =new User();
        user.setUser_mobile(userMobile);
        user.setLogin_time(StringUtil.getTimeHMS());
        userMapper.updateById(user);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    /**
     * @description: 新規登録
     * @param {String} userNickName
     * @param {String} userMobile
     * @param {String} userPassword
     * @return {*}
     */
    public int insert(String userNickName, String userMobile, String userPassword) {
        String passwordMd5 = MD5Util.MD5Encode(userMobile + userPassword, "UTF-8");
        User user = new User();
        user.setNick_name(userNickName);
        user.setUser_mobile(userMobile);
        user.setPassword(passwordMd5);
        user.setCreate_time(StringUtil.getTimeHMS());
        user.setLock_flg("0");
        return userMapper.insert(user);
    }

    @Override
    /**
     * @description: アカウント存在チェック
     * @param {String} userMobile
     * @return {*}
     */
    public int checkMobile(String userMobile) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_mobile", userMobile);
        return userMapper.selectCount(queryWrapper);
    }

    @Override
    /**
     * @description: ロックフラグ更新
     * @param {String} userMobile
     * @param {int} a
     * @return {*}
     */
    public int updateLockFlg(String userMobile, int a) {
        User user = new User();
        user.setUser_mobile(userMobile);
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();      
        userUpdateWrapper.eq("user_mobile", userMobile);
        if(a==0){
            userUpdateWrapper.set("lock_flg", "0");      
        }else{
            userUpdateWrapper.setSql("lock_flg=lock_flg+1");
        }      
        return userMapper.update(user, userUpdateWrapper);
    }

    @Override
    /**
     * @description: ロックフラグチェック
     * @param {String} userMobile
     * @return {*}
     */
    public int checkLockFlg(String userMobile) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("lock_flg").eq("user_mobile", userMobile);
        return Integer.parseInt(userMapper.selectOne(queryWrapper).getLock_flg());
    }


}
