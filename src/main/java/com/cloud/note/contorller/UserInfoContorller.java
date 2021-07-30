/*
 * @Date: 2021-07-21 09:49:05
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-07-30 09:13:18
 * @FilePath: \note\src\main\java\com\cloud\note\contorller\UserInfoContorller.java
 */
package com.cloud.note.contorller;

import com.cloud.note.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserInfoContorller {
    @Autowired
    private UserInfoService userInfoService;

    public int initUserInfo(String userMobile) {
        return userInfoService.initUserInfo(userMobile);
    }
    //ユーザー情報を取得
    public void getUserInfo(String mobile) {
      //String userMobile=(String)session.getAttribute("userMobile");
      userInfoService.getUserInfo(mobile);   
    //   if(userInfo!=null){
    //     session.setAttribute("profilephoto", userInfo.getProfilePhoto());
    //     session.setAttribute("signature", userInfo.getSignature());
    //     session.setAttribute("follow", userInfo.getFollow());
    //     session.setAttribute("fan", userInfo.getFan());
    //     session.setAttribute("star", userInfo.getStar());
    //     session.setAttribute("level", userInfo.getLevel());
    //     session.setAttribute("exp", userInfo.getExp());
    // }else{
    //     session.setAttribute("loginErrorMsg","ユーザー情報を取得失敗しました。");
    // }  
    }

    @PostMapping(value = "/updateUserInfo")
    public String updateUserInfo(String mobile) {
      getUserInfo(mobile);
      return "redirect:/index";
    }
}
 