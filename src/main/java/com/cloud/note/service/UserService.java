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
     * 更新锁定区分
     *
     * @param userMobile
     * @return
     */
    int updateLockFlg(String userMobile, int a);

}
