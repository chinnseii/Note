/*
 * @Date: 2021-08-25 13:34:49
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-25 14:38:21
 * @FilePath: \note\src\main\java\com\cloud\note\contorller\NoteContorller.java
 */
package com.cloud.note.contorller;

import com.cloud.note.annotation.TokenCheck;
import com.cloud.note.service.NoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class NoteContorller {
    @Autowired
    private NoteService noteService;

    @TokenCheck
    @ResponseBody
    @PostMapping(value = "/uploadNote")
    public String getCategoryName(@RequestParam("jsonData") String jsonData) {
        String userMobile = "";
        JSONObject res = new JSONObject();
        try {
            JSONObject note = new JSONObject(jsonData);
            userMobile = note.getString("userMobile");
            log.info("ユーザー: " + userMobile + " ノートアプロード開始");
            res = noteService.uploadNote(note);
            log.info("ユーザー: " + userMobile + " ノートアプロード成功");
        } catch (Exception e) {
            log.error("ユーザー: " + userMobile + " ノートアプロード失敗" + e.getMessage());
            e.printStackTrace();
        }
        return res.toString();
    }
}
