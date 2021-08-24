/*
 * @Date: 2021-07-21 09:49:05
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-23 10:14:17
 * @FilePath: \note\src\main\java\com\cloud\note\contorller\UserInfoContorller.java
 */
package com.cloud.note.contorller;

import com.cloud.note.annotation.TokenCheck;
import com.cloud.note.entity.UserInfo;
import com.cloud.note.service.UserInfoService;
import com.cloud.note.utils.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
    log.info("ユーザー:" + mobile + " 個人情報取得開始");
    UserInfo userInfo = userInfoService.getUserInfo(mobile);
    log.info("ユーザー:" + mobile + " 個人情報取得成功");
    return userInfo;
  }

  @TokenCheck
  @ResponseBody
  @PostMapping(value = "/updateUserSignature")
  public String updateUserSignature(@RequestParam("userMobile") String userMobile,
      @RequestParam("signature") String signature) {
    log.info("ユーザー:" + userMobile + " 個人説明更新開始");
    UserInfo userInfo = new UserInfo();
    userInfo.setSignature(signature);
    userInfo.setUser_mobile(userMobile);
    JSONObject res = new JSONObject();
    try {
      if (userInfoService.updateUserSignature(userInfo) != 1) {
        log.info("ユーザー:" + userMobile + " 個人説明を更新失敗");
        res.put("res", false);
        return res.toString();
      }
      res.put("res", true);
    } catch (JSONException e) {
      log.error("ユーザー:" + userMobile + e.getMessage());
      e.printStackTrace();
    }
    log.info("ユーザー:" + userMobile + " 個人説明更新成功");
    return res.toString();
  }

  @TokenCheck
  @ResponseBody
  @PostMapping(value = "/updateProfilePhoto")
  public String updateProfilePhoto(@RequestParam("userMobile") String userMobile, @RequestParam("top") String top,
      @RequestParam("left") String left, @RequestParam("right") String right, @RequestParam("bottom") String bottom,
      @RequestParam("rotation") String rotation, @RequestParam("scale") String scale,
      @RequestParam("file") MultipartFile multipartFile) {
    log.info("ユーザー:" + userMobile + " アバター更新開始");
    JSONObject res = new JSONObject();
    String fileName = multipartFile.getOriginalFilename();
    try {
      res.put("res", false);
      if (fileName == null || fileName.length() == 0) {
        res.put("defeat", "新しいアバターを選んでください。");
        return res.toString();
      }
      JSONObject avatar = new JSONObject();
      avatar.put("top", top);
      avatar.put("left", left);
      avatar.put("right", right);
      avatar.put("bottom", bottom);
      avatar.put("rotation", rotation);
      avatar.put("scale", scale);
      avatar.put("updatetime", StringUtil.getTimeToday());
      // 古いアバターの情報を取得
      UserInfo userInfo = userInfoService.getUserInfo(userMobile);
     if(userInfo==null){
      return res.toString();
     }
      res=userInfoService.updateUserProfilePhoto(userInfo,avatar,multipartFile);
    } catch (Exception e) {
      log.error("ユーザー:" + userMobile + " アバター更新失敗しました"+e.getMessage());
      e.printStackTrace();
    }
    return res.toString();
  }
}
