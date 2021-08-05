/*
 * @Date: 2021-07-21 09:49:05
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-05 17:46:24
 * @FilePath: \note\src\main\java\com\cloud\note\contorller\UserInfoContorller.java
 */
package com.cloud.note.contorller;

import com.cloud.note.annotation.TokenCheck;
import com.cloud.note.entity.UserInfo;
import com.cloud.note.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
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
  public UserInfo getUserInfo(@RequestParam("userMobile") String mobile) {
    log.info("ユーザー情報取得開始");
    return userInfoService.getUserInfo(mobile);
  }

  @TokenCheck
  @ResponseBody
  @PostMapping(value = "/updateUserSignature")
  public String updateUserSignature(@RequestParam("userMobile") String userMobile,
      @RequestParam("signature") String signature) throws JSONException {
    log.info("ユーザー:" + userMobile + " 個人説明を更新");
    UserInfo userInfo = new UserInfo();
    userInfo.setSignature(signature);
    userInfo.setUserMobile(userMobile);
    JSONObject res = new JSONObject();
    if (userInfoService.updateUserSignature(userInfo) != 1) {
      log.info("ユーザー:" + userMobile + " 個人説明を更新失敗");
      res.put("res", false);
      return res.toString();
    }
    res.put("res", true);
    return res.toString();
  }
}
