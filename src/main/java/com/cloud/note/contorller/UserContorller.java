package com.cloud.note.contorller;

import javax.servlet.http.HttpSession;

import com.cloud.note.entity.User;
import com.cloud.note.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserContorller {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public String register(@RequestParam("userNickName") String userNickName,
            @RequestParam("userMobile") String userMobile, @RequestParam("userPassword") String userPassword,
            HttpSession session) {

        if (!StringUtils.hasLength(userMobile)) {
            session.setMaxInactiveInterval(1);
            session.setAttribute("registerErrorMsg", "電話番号を入力してください");
            return "register";
        }
        if (!StringUtils.hasLength(userNickName) || !StringUtils.hasLength(userPassword)) {
            session.setMaxInactiveInterval(1);
            session.setAttribute("registerErrorMsg", "ユーザー名又はパスワードを入力してください。");
            return "register";
        }
        int checkExits = userService.checkMobile(userMobile);
        if (checkExits == 0) {
            int result = userService.insert(userNickName, userMobile, userPassword);
            if (result != 0) {
                // 新規登録成功の場合、ログイン処理を開始します。
                login(userMobile, userPassword, session);
                return "redirect:/index";
            } else {
                session.setAttribute("registerErrorMsg", "新規登録失敗しました。");
                session.setMaxInactiveInterval(1);
                return "/register";
            }
        } else {
            session.setMaxInactiveInterval(1);
            session.setAttribute("registerErrorMsg", "電話番号が存在しています。");
            return "/register";
        }

    }

    @PostMapping(value = "/login")
    public String login(@RequestParam("userMobile") String userMobile,
            @RequestParam("userPassword") String userPassword, HttpSession session) {

        if (!StringUtils.hasLength(userMobile) || !StringUtils.hasLength(userPassword)) {
            session.setMaxInactiveInterval(1);
            session.setAttribute("loginErrorMsg", "ユーザー名又はパスワードを入力してください。");
            return "login";
        }
        // アカウント存在チェック
        int checkExits = userService.checkMobile(userMobile);
        if (checkExits == 0) {
            session.setMaxInactiveInterval(1);
            session.setAttribute("loginErrorMsg", "ユーザー名又はパスワード不正");
            return "login";
        }
        // ログイン処理
        User user = userService.login(userMobile, userPassword);
        if (user == null) {
            session.setMaxInactiveInterval(1);
            session.setAttribute("loginErrorMsg", "ユーザー名又はパスワード不正");
            userService.updateLockFlg(userMobile, 1);
            return "login";
        } else if (Integer.valueOf(user.getLockedFlg()) > 5) {
            session.setMaxInactiveInterval(1);
            session.setAttribute("loginErrorMsg", "アカウントがロックされています。");
            return "login";
        } else {
            // ロックフラグをリラン
            userService.updateLockFlg(userMobile, 0);
            // ログイン成功の場合session有効期限は２時間に設定
            session.setMaxInactiveInterval(60 * 60 * 2);
            session.setAttribute("loginErrorMsg", "");
            session.setAttribute("nickName",user.getNickName());
            session.setAttribute("level",user.getLevel());
            session.setAttribute("exp",user.getExp());
            return "redirect:/index";
        }
    }
}