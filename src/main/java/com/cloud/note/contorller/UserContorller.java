/*
 * @Date: 2021-07-15 16:24:23
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-05 16:10:01
 * @FilePath: \note\src\main\java\com\cloud\note\contorller\UserContorller.java
 */
package com.cloud.note.contorller;

import com.cloud.note.annotation.PassToken;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserContorller {
    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoContorller userInfoContorller;

    @Autowired
    private Constant constant;

    @PostMapping(value = "/register")
    @PassToken
    public ModelAndView register(@RequestParam("userNickName") String userNickName,
            @RequestParam("userMobile") String userMobile, @RequestParam("userPassword") String userPassword,
            ModelAndView modelAndView, Model model, RedirectAttributes redirectAttributes) {
        log.info("ユーザー:" + userMobile + " 新規登録処理を開始");
        modelAndView.setViewName("register");
        if (!StringUtils.hasLength(userMobile)) {
            model.addAttribute("registerErrorMsg", constant.getMOBILE_NULL_ERRORMSG());
            log.error(constant.getMOBILE_NULL_ERRORMSG());
            return modelAndView;
        }
        if (!StringUtils.hasLength(userNickName) || !StringUtils.hasLength(userPassword)) {
            model.addAttribute("registerErrorMsg", constant.getMOBILE_OR_PASSWORD_NULL_ERRORMSG());
            log.error(constant.getMOBILE_OR_PASSWORD_NULL_ERRORMSG());
            return modelAndView;
        }
        // アカウント存在チェック
        int checkExits = userService.checkMobile(userMobile);
        if (checkExits == 0) {
            int result = userService.insert(userNickName, userMobile, userPassword);

            if (result != 0) {
                log.info("ユーザー:" + userMobile + " 新規登録成功、アカウント初期化開始");
                // アカウント初期化
                if (userInfoContorller.initUserInfo(userMobile) != 1) {
                    model.addAttribute("registerErrorMsg", constant.getACCOUNT_INIT_ERRORMSG());
                    log.error(constant.getACCOUNT_INIT_ERRORMSG());
                    return modelAndView;
                }
                log.info("ユーザー:" + userMobile + " アカウント初期化成功");
                // 新規登録成功の場合、ログイン処理を開始します。
                return login(userMobile, userPassword, modelAndView, model, redirectAttributes);
            } else {
                log.error("ユーザー:" + userMobile + "新規登録失敗");
                return modelAndView;
            }
        } else {
            log.error("ユーザー:" + userMobile + "新規登録失敗：アカウント存在しています。");
            model.addAttribute("registerErrorMsg", constant.getMOBILE_EXIST_INCORRECT_ERRORMSG());
            return modelAndView;
        }

    }

    @PostMapping(value = "/login")
    @PassToken
    public ModelAndView login(@RequestParam("userMobile") String userMobile,
            @RequestParam("userPassword") String userPassword, ModelAndView modelAndView, Model model,
            RedirectAttributes redirectAttributes) {
        log.info("ユーザー:" + userMobile + " ログイン処理を開始");
        modelAndView.setViewName("login");
        if (!StringUtils.hasLength(userMobile) || !StringUtils.hasLength(userPassword)) {
            model.addAttribute("loginErrorMsg", constant.getMOBILE_OR_PASSWORD_NULL_ERRORMSG());
            return modelAndView;
        }
        // アカウント存在チェック
        int checkExits = userService.checkMobile(userMobile);
        if (checkExits == 0) {
            model.addAttribute("loginErrorMsg", constant.getMOBILE_OR_PASSWORD_INCORRECT_ERRORMSG());
            return modelAndView;
        }
        // ログイン処理
        User user = userService.login(userMobile, userPassword);
        if (user == null) {
            model.addAttribute("loginErrorMsg", constant.getMOBILE_OR_PASSWORD_INCORRECT_ERRORMSG());
            // 密码错误，锁定flg加一
            userService.updateLockFlg(userMobile, 1);
            return modelAndView;
        } else if (Integer.valueOf(user.getLockedFlg()) == 5) {
            model.addAttribute("loginErrorMsg", constant.getACCOUNT_LOGKED_ERRORMSG());
            return modelAndView;
        } else {
            // ロックフラグをリラン
            userService.updateLockFlg(userMobile, 0);
            // 身分証明(token)を生成
            String secretKey = constant.getSecretkey() + userMobile; // シークレットキーの作成、設定されった文字列と電話番号の組み合わせ
            long expire = constant.getExpire();// token有効期限を取得
            redirectAttributes.addFlashAttribute("token", TokenUtil.createToken(userMobile, expire, secretKey));
            redirectAttributes.addFlashAttribute("userMobile", userMobile);
            redirectAttributes.addFlashAttribute("nickName", user.getNickName());
            redirectAttributes.addFlashAttribute("loginErrorMsg", "");
            modelAndView.setViewName("redirect:index");
            log.info("ユーザー:" + userMobile + " ログイン成功");
            return modelAndView;
        }
    }
}