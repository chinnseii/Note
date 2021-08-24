/*
 * @Date: 2021-07-21 10:53:11
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-18 13:28:05
 * @FilePath: \note\src\main\java\com\cloud\note\service\impl\UserInfoServiceImpl.java
 */
package com.cloud.note.service.impl;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import com.cloud.note.dao.UserInfoMapper;
import com.cloud.note.entity.Constant;
import com.cloud.note.entity.UserInfo;
import com.cloud.note.service.UserInfoService;
import com.cloud.note.utils.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private Constant constant;

    @Override
    public int initUserInfo(String userMobile) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_mobile(userMobile);
        userInfo.setProfile_photo(constant.getINIT_AVATAR_NAME());
        userInfo.setSignature("");
        userInfo.setExp("0");
        userInfo.setFollow("0");
        userInfo.setStar("0");
        userInfo.setFan("0");
        userInfo.setUser_level("0");
        userInfo.setUpdate_time(StringUtil.getTimeHMS());
        return userInfoMapper.insert(userInfo);
    }

    @Override
    public UserInfo getUserInfo(String userMobile) {
        return userInfoMapper.selectById(userMobile);
    }

    @Override
    public int updateUserSignature(UserInfo userInfo) {
        userInfo.setUpdate_time(StringUtil.getTimeHMS());
        return userInfoMapper.updateById(userInfo);
    }

    @Override
    public JSONObject updateUserProfilePhoto(UserInfo userInfo, JSONObject avatar, MultipartFile multipartFile)
            throws Exception {
        JSONObject res = new JSONObject();
        res.put("res", false);
        // アバター設定されてない場合削除処理行わない
        if (!userInfo.getProfile_photo().equals(constant.getINIT_AVATAR_NAME())) {
            Path path = Paths.get(constant.getAVATAR_PATH() + userInfo.getProfile_photo());
            Files.deleteIfExists(path);
            String dirPath = userInfo.getProfile_photo().replaceAll(path.toFile().getName(), "");
            // 旧アバター削除済み、空のフォルダの場合フォルダも削除
            if (Paths.get(dirPath).toFile().exists() && Paths.get(dirPath).toFile().listFiles().length == 0) {
                Files.deleteIfExists(Paths.get(dirPath));
            }
        }
        // 日付によって、アバター保存用フォルダを作成
        String avatarDir = constant.getAVATAR_PATH() + StringUtil.getTimeToday();
        File avatarDirFile = new File(avatarDir);
        if (!avatarDirFile.isDirectory()) {
            avatarDirFile.mkdirs();
        }
        String filetype = StringUtil.getFileType(multipartFile.getOriginalFilename());
        String avatarNewPath = avatarDir + File.separator + userInfo.getUser_mobile() +"." + filetype;
        avatar.put("path", avatarNewPath);
        try {
            BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
            if (bufferedImage == null) {
                log.info("ユーザー:" + userInfo.getUser_mobile() + "アップロードのは写真ではない");
                res.put("defeat", "アップロードのは写真ではない");
                return res;
            }
            double originalWidth = bufferedImage.getWidth();
            double originalHeight = bufferedImage.getHeight();
            double tmpWidth;
            double tmpHeiht;
            // 图片缩放比例,每放大一级，图片放大十五分之一倍
            double tmpscale = Float.parseFloat(avatar.getString("scale")) * Double.parseDouble("0.0666");
            // 图片按长宽300px等比缩放
            double widthWithHeight = originalWidth / originalHeight;
            if (originalWidth >= originalHeight) {
                tmpWidth = 300;
                tmpHeiht = tmpWidth / widthWithHeight;
            } else {
                tmpHeiht = 300;
                tmpWidth = tmpHeiht * widthWithHeight;
            }
            int newWidth = (int) (tmpWidth * (1 + tmpscale));
            int newHeight = (int) (tmpHeiht * (1 + tmpscale));
            // 生成一个图片并按比率裁剪
            BufferedImage tmpImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = tmpImage.createGraphics();
            tmpImage = graphics.getDeviceConfiguration().createCompatibleImage(newWidth, newHeight, 3);
            graphics = tmpImage.createGraphics();
            graphics.drawImage(bufferedImage, 0, 0, newWidth, newHeight, null);
            graphics.dispose();
            // 剪辑
            int x = Integer.parseInt(avatar.getString("left"));
            int y = Integer.parseInt(avatar.getString("top"));
            int selectWidth = Integer.parseInt(avatar.getString("right")) - x;
            int selectHeight = Integer.parseInt(avatar.getString("bottom")) - y;
            BufferedImage newImage = tmpImage.getSubimage(x, y, selectWidth, selectHeight);
            // アバターファイル作成
            ImageIO.write(newImage, filetype, new File(avatarNewPath));
        } catch (Exception e) {
            log.info("ユーザー:" + userInfo.getUser_mobile() + " アバター更新処理失敗しました", e.getMessage());
            e.printStackTrace();
            res.put("defeat", " アバター更新失敗しました");
            return res;
        }
        log.info("ユーザー:" + userInfo.getUser_mobile() + " アバターアップロード成功、データベース更新開始");
        userInfo.setProfile_photo(avatarNewPath.replace(constant.getAVATAR_PATH(), ""));
        userInfo.setUpdate_time(StringUtil.getTimeHMS());
        if (userInfoMapper.updateById(userInfo) != 1) {
            log.error("ユーザー:" + userInfo.getUser_mobile() + " アバターDB情報更新失敗しました");
            res.put("defeat", " アバター更新失敗しました");
        }
        log.info("ユーザー:" + userInfo.getUser_mobile() + " アバター更新成功");
        res.put("res", true);
        return res;
    }
}