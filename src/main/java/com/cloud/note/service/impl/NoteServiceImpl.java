/*
 * @Date: 2021-08-25 13:45:17
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-09-02 13:38:35
 * @FilePath: \note\src\main\java\com\cloud\note\service\impl\NoteServiceImpl.java
 */
package com.cloud.note.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.note.dao.CategoryMapper;
import com.cloud.note.dao.NoteMapper;
import com.cloud.note.entity.Category;
import com.cloud.note.entity.Note;
import com.cloud.note.service.NoteService;
import com.cloud.note.utils.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    /**
     * @description: 笔记上传
     * @param {JSONObject} noteJson
     * @return {*}
     */
    public JSONObject uploadNote(JSONObject noteJson) throws Exception {
        JSONObject res = new JSONObject();
        Note note = new Note();
        String userMobile = noteJson.getString("userMobile");
        String categoryname = noteJson.getString("subjects");
        QueryWrapper<Category> queryCategory = new QueryWrapper<>();
        queryCategory.select("id").eq("category_name", categoryname).eq("user_mobile", userMobile);
        note.setCategory_id(categoryMapper.selectOne(queryCategory).getId());
        note.setContent(noteJson.getString("content"));
        note.setCreate_date(StringUtil.getTimeHMS());
        note.setUpdate_date(StringUtil.getTimeHMS());
        note.setStar("0");
        note.setStatus(noteJson.getString("status"));
        note.setUser_mobile(userMobile);
        note.setTitle(noteJson.getString("title"));
        note.setContent(noteJson.getString("content"));
        if (noteMapper.insert(note) == 1) {
            res.put("result", true);
        } else {
            res.put("result", false);
            res.put("errorMsg", "ノートアップロード失敗しました。");

        }
        return res;
    }

    @Override
    /**
     * @description: 获取一页(3条)笔记
     * @param {JSONObject} getNote
     * @return {*}
     */
    public JSONObject getNote(JSONObject getNote) throws Exception {
        JSONObject res = new JSONObject();
        String userMobile = getNote.getString("userMobile");
        String status = getNote.getString("status");
        String page = getNote.getString("page");
        QueryWrapper<Note> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_mobile", userMobile).eq("status", status);
        int count = noteMapper.selectCount(queryWrapper);
        // 如果没有记录直接返回
        if (count == 0) {
            return res;
        }
        // 当记录大于3条时计算第N页是从第几条记录开始
        int limit = 0;
        if (count > 3) {
            limit = Integer.parseInt(page) * 3;
        }
        res.put("count", count);
        List<Note> noteList = noteMapper.getNotePage(userMobile, status, limit);
        int i = 0;
        for (Note note : noteList) {
            res.put(String.valueOf(i++), JSON.toJSONString(note));
        }
        return res;
    }

    @Override
    /**
     * @description: 删除一条笔记
     * @param {String} noteId
     * @return {*}
     */
    public JSONObject deleteNote(String noteId) throws Exception {
        JSONObject res = new JSONObject();
        int a = noteMapper.deleteById(noteId);
        res.put("result", true);
        if (a != 1) {
            res.put("result", false);
            res.put("errorMsg", "サーバー異常、ノート削除失敗しました。");
        }
        return res;
    }

    @Override
    /**
     * @description: 根据ID获取笔记信息
     * @param {String} noteId
     * @return {*}
     */
    public JSONObject getNoteById(String noteId) throws Exception {
        Note note = noteMapper.getEditNoteById(noteId);
        JSONObject res = (JSONObject) JSON.toJSON(note);
        res.put("result", true);
        if (note.getId().isEmpty()) {
            res.put("result", false);
        }
        return res;
    }

    @Override
    /**
     * @description: 更新笔记内容
     * @param {JSONObject} updateNote
     * @return {*}
     */
    public JSONObject updateNote(JSONObject updateNote) throws Exception {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_name", updateNote.getString("subjects"));
        queryWrapper.eq("user_mobile", updateNote.getString("userMobile"));
        Note note = new Note();
        note.setCategory_id(categoryMapper.selectOne(queryWrapper).getId());
        note.setTitle(updateNote.getString("title"));
        note.setContent(updateNote.getString("content"));
        note.setStatus(updateNote.getString("status"));
        note.setUpdate_date(StringUtil.getTimeHMS());
        note.setId(updateNote.getString("id"));
        JSONObject res= new JSONObject();
        res.put("result", true);
        if (noteMapper.updateById(note) != 1) {
            res.put("result", false);
        }
        return res;
    }
}
