package com.cloud.note.service.impl;

import javax.servlet.http.HttpSession;

import com.cloud.note.dao.UserInfoMapper;
import com.cloud.note.entity.UserInfo;
import com.cloud.note.service.UserInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public int initUserInfo(String userMobile) {
        return userInfoMapper.initUserInfo(userMobile);
    }

    @Override
    public void getUserInfo(HttpSession session) {
        String userMobile = (String) session.getAttribute("userMobile");
        UserInfo userInfo = userInfoMapper.getUserInfo(userMobile);
        if(userInfo!=null){
            session.setAttribute("profilephoto", userInfo.getProfilePhoto());
            session.setAttribute("signature", userInfo.getSignature());
            session.setAttribute("follow", userInfo.getFollow());
            session.setAttribute("fan", userInfo.getFan());
            session.setAttribute("star", userInfo.getStar());
            session.setAttribute("level", userInfo.getLevel());
            session.setAttribute("exp", userInfo.getExp());
        }else{
            session.setAttribute("loginErrorMsg","ユーザー情報を取得失敗しました。");
        }
       
    }

}
