/*
 * @Date: 2021-08-18 16:59:39
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-09-08 15:43:30
 * @FilePath: \note\src\main\java\com\cloud\note\contorller\CategoryController.java
 */
package com.cloud.note.contorller;

import com.cloud.note.annotation.TokenCheck;
import com.cloud.note.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @TokenCheck
    @ResponseBody
    @PostMapping(value = "/createCategory")
    public String createCategory(@RequestParam("userMobile") String mobile,
            @RequestParam("categoryName") String categoryName, @RequestParam("status") String status) {
        log.info("ユーザー: " + mobile + " 科目 「" + categoryName + "」 作成開始");
        JSONObject res = new JSONObject();
        try {
            res = categoryService.createCategory(mobile, categoryName, status);
            if (!res.getBoolean("result")) {
                log.error("ユーザー: " + mobile + " 科目 「" + categoryName + "」 作成失敗 : " + res.getString("errorCode"));
                return res.toString();
            }
        } catch (Exception e) {
            log.error("ユーザー: " + mobile + " 科目 「" + categoryName + "」 作成失敗");
            e.printStackTrace();
            return res.toString();
        }
        log.info("ユーザー: " + mobile + " 科目 「" + categoryName + "」 作成成功");
        return res.toString();
    }

    @TokenCheck
    @ResponseBody
    @PostMapping(value = "/updateCategory")
    public String updateCategory(@RequestParam("userMobile") String mobile,
            @RequestParam("categoryName") String categoryName, @RequestParam("status") String status) {
        log.info("ユーザー: " + mobile + " 科目 「" + categoryName + "」 更新開始");
        JSONObject res = new JSONObject();
        try {
            res = categoryService.updateCategory(mobile, categoryName, status);
            if (!res.getBoolean("result")) {
                log.error("ユーザー: " + mobile + " 科目 「" + categoryName + "」 作成失敗 : " + res.getString("errorCode"));
                return res.toString();
            }
        } catch (Exception e) {
            log.error("ユーザー: " + mobile + " 科目 「" + categoryName + "」 作成失敗");
            e.printStackTrace();
            return res.toString();
        }
        log.info("ユーザー: " + mobile + " 科目 「" + categoryName + "」 作成成功");
        return res.toString();
    }

    @TokenCheck
    @ResponseBody
    @PostMapping(value = "/getCategoryName")
    public String getCategoryName(@RequestParam("jsonData") String jsonData) {
        String userMobile="";
        JSONObject res= new JSONObject();
        try {
            res.put("res", false);
            JSONObject jsonObject= new JSONObject(jsonData);
            userMobile= jsonObject.getString("userMobile");
            log.info("ユーザー: " + userMobile + " 科目名取得開始");
            res=categoryService.getCategoryName(userMobile);
            log.info("ユーザー: " + userMobile + " 科目名取得成功");
        } catch (Exception e) {
            log.error("ユーザー: " + userMobile + " 科目名取得失敗"+e.getMessage());
            e.printStackTrace();
        }    
        return res.toString();    
    }
}
