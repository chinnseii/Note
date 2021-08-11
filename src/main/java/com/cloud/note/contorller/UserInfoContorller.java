/*
 * @Date: 2021-07-21 09:49:05
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-11 16:44:05
 * @FilePath: \note\src\main\java\com\cloud\note\contorller\UserInfoContorller.java
 */
package com.cloud.note.contorller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.cloud.note.annotation.TokenCheck;
import com.cloud.note.entity.Constant;
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

  @Autowired
  private Constant constant;

  public int initUserInfo(String userMobile) {
    return userInfoService.initUserInfo(userMobile);
  }

  // ユーザー情報を取得
  @TokenCheck
  @ResponseBody
  @PostMapping(value = "/getUserInfo")
  public UserInfo getUserInfo(@RequestParam("userMobile") String mobile) throws JSONException {
    log.info("ユーザー:" + mobile + "情報取得");
    UserInfo userInfo = userInfoService.getUserInfo(mobile);
    // アバターまだ初期化の状態、そのまま画面側に送る
    if (userInfo.getProfilePhoto().equals("init_profilephoto.png")) {
      return userInfo;
    }
    JSONObject jsonObject = new JSONObject(userInfo.getProfilePhoto());
    String path = jsonObject.getString("path").replaceAll(constant.getAVATAR_PATH(), "");
    userInfo.setProfilePhoto(jsonObject.put("path", path).toString());
    return userInfo;
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

  @TokenCheck
  @ResponseBody
  @PostMapping(value = "/updateProfilePhoto")
  public String updateProfilePhoto(@RequestParam("userMobile") String userMobile, @RequestParam("top") String top,
      @RequestParam("left") String left, @RequestParam("right") String right, @RequestParam("bottom") String bottom,
      @RequestParam("rotation") String rotation, @RequestParam("scale") String scale,
      @RequestParam("file") MultipartFile multipartFile) throws JSONException {
    log.info("ユーザー:" + userMobile + " アバター更新開始");
    JSONObject res = new JSONObject();
    res.put("res", false);
    String fileName = multipartFile.getOriginalFilename();
    if(fileName==null||fileName.length()==0){
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
    // アバター設定されてない場合削除処理行わない
    if (!userInfo.getProfilePhoto().equals("init_profilephoto.png")) {
      JSONObject oldProfilePhoto = new JSONObject(userInfo.getProfilePhoto());
      Path path = Paths.get(oldProfilePhoto.getString("path"));
      // 古いアバター削除
      try {
        Files.delete(path);
        String dirPath=oldProfilePhoto.getString("path").replaceAll(path.toFile().getName(), "");
        if(Paths.get(dirPath).toFile().listFiles().length==0){
          Files.delete(Paths.get(dirPath));
        }
      } catch (IOException e) {
        e.printStackTrace();
        log.error("ユーザー:" + userMobile + " 古いアバター削除失敗しました。" + e.getMessage());
        res.put("defeat", "アバター更新失敗しました");
        return res.toString();
      }    
    }
    // 日付によって、アバター保存用フォルダを作成
    String avatarDir = constant.getAVATAR_PATH() + StringUtil.getTimeToday();
    File avatarDirFile = new File(avatarDir);
    if (!avatarDirFile.isDirectory()) {
      avatarDirFile.mkdirs();
    }
    String filetype = StringUtil.getFileType(fileName);
    String avatarNewPath = avatarDir + File.separator + userMobile+"_"+StringUtil.getTimeNow() + "." + filetype;
    avatar.put("path", avatarNewPath);
    File imgFile = new File(avatarNewPath);
    try {
      BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
      if (bufferedImage == null) {
        log.info("ユーザー:" + userMobile + "アップロードのは写真ではない");
        res.put("defeat", "アップロードのは写真ではない");
        return res.toString();
      }
      // アバターファイル作成
      ImageIO.write(bufferedImage, filetype, imgFile);
    } catch (Exception e) {
      log.info("ユーザー:" + userMobile + " アバター更新処理失敗しました", e.getMessage());
      res.put("defeat", " アバター更新失敗しました");
      return res.toString();
    }
    log.info("ユーザー:" + userMobile + " アバターアップロード成功、データベース更新開始");
    userInfo.setProfilePhoto(avatar.toString());
    if (userInfoService.updateUserProfilePhoto(userInfo) != 1) {
      log.error("ユーザー:" + userMobile + " アバターDB情報更新失敗しました");
      res.put("defeat", " アバター更新失敗しました");
    }
    log.info("ユーザー:" + userMobile + " アバター更新成功");
    res.put("res", true);
    return res.toString();
  }
}
