package com.cloud.note.contorller;



import javax.servlet.http.HttpSession;

import com.cloud.note.entity.UserInfo;
import com.cloud.note.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class UserInfoContorller {
    @Autowired
    private UserInfoService userInfoService;

    public int initUserInfo(String userMobile) {
        return userInfoService.initUserInfo(userMobile);
    }
    //ユーザー情報を取得、
    public void getUserInfo(HttpSession session) {
      //String userMobile=(String)session.getAttribute("userMobile");
      userInfoService.getUserInfo(session);     
    }
}
 