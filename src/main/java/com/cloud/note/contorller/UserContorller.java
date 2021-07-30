/*
 * @Date: 2021-07-15 16:24:23
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-07-30 13:02:53
 * @FilePath: \note\src\main\java\com\cloud\note\contorller\UserContorller.java
 */
package com.cloud.note.contorller;

import com.cloud.note.entity.Constant;
import com.cloud.note.entity.User;
import com.cloud.note.service.UserService;
import com.cloud.note.utils.TokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserContorller {
    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoContorller userInfoContorller;

    @Autowired
    private Constant constant;

    @PostMapping(value = "/register")
    public String register(@RequestParam("userNickName") String userNickName,
            @RequestParam("userMobile") String userMobile, @RequestParam("userPassword") String userPassword,
         Model model) {

        if (!StringUtils.hasLength(userMobile)) {
            model.addAttribute("registerErrorMsg", constant.getMOBILE_NULL_ERRORMSG());
            return "register";
        }
        if (!StringUtils.hasLength(userNickName) || !StringUtils.hasLength(userPassword)) {
            model.addAttribute("registerErrorMsg",constant.getMOBILE_OR_PASSWORD_NULL_ERRORMSG());
            return "register";
        }
        int checkExits = userService.checkMobile(userMobile);
        if (checkExits == 0) {
            int result = userService.insert(userNickName, userMobile, userPassword);
            if (result != 0) {
                if (userInfoContorller.initUserInfo(userMobile) != 1) {
                    model.addAttribute("registerErrorMsg", constant.getACCOUNT_INIT_ERRORMSG());
                    return "register";
                }
                // 新規登録成功の場合、ログイン処理を開始します。
                login(userMobile, userPassword, model);
                return "index";
            } else {
                model.addAttribute("registerErrorMsg", constant.getREGIST_ERRORMSG());
                return "/register";
            }
        } else {
            model.addAttribute("registerErrorMsg", constant.getMOBILE_EXIST_INCORRECT_ERRORMSG());
            return "/register";
        }

    }

    @PostMapping(value = "/login")
    public String login(@RequestParam("userMobile") String userMobile,
            @RequestParam("userPassword") String userPassword, Model model) {
        if (!StringUtils.hasLength(userMobile) || !StringUtils.hasLength(userPassword)) {
            model.addAttribute("loginErrorMsg", constant.getMOBILE_OR_PASSWORD_NULL_ERRORMSG());
            return "login";
        }

        // アカウント存在チェック
        int checkExits = userService.checkMobile(userMobile);
        if (checkExits == 0) {
            model.addAttribute("loginErrorMsg",constant.getMOBILE_OR_PASSWORD_INCORRECT_ERRORMSG());
            return "login";
        }
        // ログイン処理
        User user = userService.login(userMobile, userPassword);
        if (user == null) {
            model.addAttribute("loginErrorMsg", constant.getMOBILE_OR_PASSWORD_INCORRECT_ERRORMSG());
            //密码错误，锁定flg加一
            userService.updateLockFlg(userMobile, 1);
            return "login";
        } else if (Integer.valueOf(user.getLockedFlg()) == 5) {
            model.addAttribute("loginErrorMsg", "アカウントがロックされています。");
            return "login";
        } else {
            // ロックフラグをリラン
            userService.updateLockFlg(userMobile, 0);
            model.addAttribute("loginErrorMsg", "");
            // 身分証明(token)を生成
            String secretKey = constant.getSecretkey() + userMobile; // 由固定密钥+用户名组成动态密钥
            long expire = constant.getExpire();// token有効期限を取得
            model.addAttribute("token", TokenUtil.createToken(userMobile, expire, secretKey));
            model.addAttribute("userMobile", userMobile);
            model.addAttribute("nickName", user.getNickName());
            return "index";
        }
    }
}