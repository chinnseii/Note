/*
 * @Date: 2021-09-02 17:24:02
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-09-02 17:25:08
 * @FilePath: \note\src\main\java\com\cloud\note\entity\LevelMaster.java
 */
package com.cloud.note.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("LevelMaster")
public class LevelMaster {
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    private String level;
    private String exp;  
}
