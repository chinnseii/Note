/*
 * @Date: 2021-08-18 17:06:42
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-24 11:33:05
 * @FilePath: \note\src\main\java\com\cloud\note\service\CategoryService.java
 */
package com.cloud.note.service;

import org.springframework.boot.configurationprocessor.json.JSONObject;

public interface CategoryService {

    /**
     * @description: 新建一个类目
     * @param {String} mobile
     * @param {String} categoryName
     * @param {String} status
     * @return {*}
     */
    JSONObject createCategory(String mobile, String categoryName, String status) throws Exception;

    /**
     * @description: 科目更新
     * @param {String} mobile
     * @param {String} categoryName
     * @param {String} status
     * @return {*}
     */
    JSONObject updateCategory(String mobile, String categoryName, String status) throws Exception;

    JSONObject getCategoryName(String mobile) throws Exception;
}
