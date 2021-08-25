/*
 * @Date: 2021-08-18 14:27:12
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-25 13:30:03
 * @FilePath: \note\src\main\java\com\cloud\note\entity\Note.java
 */
package com.cloud.note.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("Note")
public class Note {
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    private String user_mobile;
    private String category_id;
    private String star;
    private String create_date;
    private String update_date;
    private String status;
    private String title;
    private String content;
}
