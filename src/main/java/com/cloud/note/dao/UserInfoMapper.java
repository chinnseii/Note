package com.cloud.note.dao;

import com.cloud.note.entity.UserInfo;

public interface UserInfoMapper {
     // ユーザー情報初期化
     int initUserInfo(String userMobile);

     //ユーザー情報取得
     UserInfo getUserInfo(String userMobile);
}
