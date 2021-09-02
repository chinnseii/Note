/*
 * @Date: 2021-08-25 13:54:13
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-09-01 17:53:53
 * @FilePath: \note\src\main\java\com\cloud\note\dao\NoteMapper.java
 */
package com.cloud.note.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.note.entity.Note;

import org.apache.ibatis.annotations.Select;

public interface NoteMapper extends BaseMapper<Note> {
    @Select("SELECT a.id,b.category_name,a.star, a.update_date, a.status, a.title, a.content FROM note a JOIN category b ON a.category_id = b.id WHERE a.user_mobile =#{user_mobile} and a.status = #{status} order by a.update_date desc limit #{page}, 3")
    List<Note> getNotePage(String user_mobile, String status, int page);

    @Select("SELECT a.id,b.category_name,a.star, a.update_date, a.status, a.title, a.content FROM note a JOIN category b ON a.category_id = b.id WHERE a.id =#{id}")
    Note getEditNoteById(String id);
}
