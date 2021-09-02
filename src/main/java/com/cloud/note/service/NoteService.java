/*
 * @Date: 2021-08-25 13:43:08
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-09-02 12:01:00
 * @FilePath: \note\src\main\java\com\cloud\note\service\NoteService.java
 */
package com.cloud.note.service;

import com.alibaba.fastjson.JSONObject;

public interface NoteService {
    JSONObject uploadNote(JSONObject note) throws Exception ;

    JSONObject getNote(JSONObject note) throws Exception ;

    JSONObject deleteNote(String noteId) throws Exception;

    JSONObject getNoteById(String noteId) throws Exception;

    JSONObject updateNote(JSONObject updateNote) throws Exception;
}
