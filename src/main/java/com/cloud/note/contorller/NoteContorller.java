/*
 * @Date: 2021-08-25 13:34:49
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-09-07 16:51:28
 * @FilePath: \note\src\main\java\com\cloud\note\contorller\NoteContorller.java
 */
package com.cloud.note.contorller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.note.annotation.TokenCheck;
import com.cloud.note.service.NoteService;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String uploadNote(@RequestParam("jsonData") String jsonData) {
        String userMobile = "";
        JSONObject res = new JSONObject();
        try {
            JSONObject note = JSON.parseObject(jsonData);
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

    @TokenCheck
    @ResponseBody
    @PostMapping(value = "/getNote")
    public String getNote(@RequestParam("jsonData") String jsonData) {
        String userMobile = "";
        String page="";
        JSONObject res = new JSONObject();
        try {
            JSONObject getNote = JSON.parseObject(jsonData);
            userMobile = getNote.getString("userMobile");
            page=getNote.getString("page");
            log.info("ユーザー: " + userMobile + " ページ："+page+" のノート情報取得開始");
            res = noteService.getNote(getNote);
            log.info("ユーザー: " + userMobile + " ページ："+page+" のノート情報取得成功");
        } catch (Exception e) {
            log.error("ユーザー: " + userMobile + " ページ："+page+" のノート情報取得失敗" + e.getMessage());
            e.printStackTrace();
        }
        return res.toString();
    }


    @TokenCheck
    @ResponseBody
    @PostMapping(value = "/deleteNote")
    public String deleteNote(@RequestParam("jsonData") String jsonData) {
        String userMobile = "";
        JSONObject res = new JSONObject();
        try {
            JSONObject deleteNote = JSON.parseObject(jsonData);
            userMobile = deleteNote.getString("userMobile");
            String noteId=deleteNote.getString("id");
            log.info("ユーザー: " + userMobile + " ノート削除開始");
            res = noteService.deleteNote(userMobile,noteId);
            if(res.getBoolean("result")){
                log.info("ユーザー: " + userMobile + " ノート削除成功");
            }       
        } catch (Exception e) {
            log.error("ユーザー: " + userMobile + " ノート削除失敗" + e.getMessage());
            e.printStackTrace();
        }
        return res.toString();
    }

    @TokenCheck
    @ResponseBody
    @PostMapping(value = "/updateNote")
    public String updateNote(@RequestParam("jsonData") String jsonData) {
        String userMobile = "";
        String noteId = "";
        JSONObject res = new JSONObject();
        try {
            JSONObject updateNote = JSON.parseObject(jsonData);
            userMobile = updateNote.getString("userMobile");
            noteId=updateNote.getString("id");
            log.info("ユーザー: " + userMobile +" ノートID:"+noteId+ " 更新開始");
            res = noteService.updateNote(updateNote);
            if(res.getBoolean("result")){
                log.info("ユーザー: " + userMobile + " ノートID:"+noteId+"情報更新成功");
            }  
        } catch (Exception e) {
            log.error("ユーザー: " + userMobile + " ノート更新失敗" + e.getMessage());
            e.printStackTrace();
        }
        return res.toString();
    }

    @TokenCheck
    @ResponseBody
    @PostMapping(value = "/getNoteById")
    public String getNoteById(@RequestParam("jsonData") String jsonData) {
        String userMobile = "";
        String noteId = "";
        JSONObject res = new JSONObject();
        try {
            JSONObject getNoteById = JSON.parseObject(jsonData);
            userMobile = getNoteById.getString("userMobile");
            noteId=getNoteById.getString("id");
            log.info("ユーザー: " + userMobile + " ノートID:"+noteId+"情報取得開始");
            res = noteService.getNoteById(noteId);
            if(res.getBoolean("result")){
                log.info("ユーザー: " + userMobile + " ノートID:"+noteId+"情報取得成功");
            }       
        } catch (Exception e) {
            log.error("ユーザー: " + userMobile + " ノートID:"+noteId+"情報取得失敗" + e.getMessage());
            e.printStackTrace();
        }
        return res.toString();
    }
}
