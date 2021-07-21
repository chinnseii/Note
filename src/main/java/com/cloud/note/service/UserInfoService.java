package com.cloud.note.service;

import javax.servlet.http.HttpSession;

public interface UserInfoService {
    //ユーザー情報初期化   
    int initUserInfo(String userMobile);

    //ユーザー情報取得
    void getUserInfo(HttpSession session);
}
