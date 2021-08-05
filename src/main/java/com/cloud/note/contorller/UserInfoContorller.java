/*
 * @Date: 2021-07-21 09:49:05
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-05 13:28:17
 * @FilePath: \note\src\main\java\com\cloud\note\contorller\UserInfoContorller.java
 */
package com.cloud.note.contorller;

import com.cloud.note.annotation.TokenCheck;
import com.cloud.note.entity.UserInfo;
import com.cloud.note.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserInfoContorller {
  @Autowired
  private UserInfoService userInfoService;

  public int initUserInfo(String userMobile) {
    return userInfoService.initUserInfo(userMobile);
  }

  // ユーザー情報を取得
  @TokenCheck
  @ResponseBody
  @PostMapping(value = "/getUserInfo")
  public UserInfo getUserInfo(@RequestParam("userMobile") String mobile,Model model) {
    return userInfoService.getUserInfo(mobile);
  }

  @TokenCheck
  @PostMapping(value = "/updateUserInfo")
  public String updateUserInfo(String mobile) {
    //getUserInfo(mobile);
    return "redirect:index";
  }
}
