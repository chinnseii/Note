/*
 * @Date: 2021-08-25 13:45:17
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-25 14:12:12
 * @FilePath: \note\src\main\java\com\cloud\note\service\impl\NoteServiceImpl.java
 */
package com.cloud.note.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.note.dao.CategoryMapper;
import com.cloud.note.dao.NoteMapper;
import com.cloud.note.entity.Category;
import com.cloud.note.entity.Note;
import com.cloud.note.service.NoteService;
import com.cloud.note.utils.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteMapper NoteMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
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
        note.setStar("0");
        note.setStatus(noteJson.getString("status"));
        note.setUser_mobile(userMobile);
        note.setTitle(noteJson.getString("title"));
        note.setContent(noteJson.getString("content"));
        if(NoteMapper.insert(note)==1){
            res.put("result", true);
        }else{
            res.put("result", false);
            res.put("errorMsg", "ノートアップロード失敗しました。");
            
        }
        return res;
    }

}
