/*
 * @Date: 2021-08-25 13:43:08
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-25 13:44:16
 * @FilePath: \note\src\main\java\com\cloud\note\service\NoteService.java
 */
package com.cloud.note.service;

import org.springframework.boot.configurationprocessor.json.JSONObject;

public interface NoteService {
    JSONObject uploadNote(JSONObject note) throws Exception ;
}
