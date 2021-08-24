/*
 * @Date: 2021-08-18 17:07:02
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-24 11:58:58
 * @FilePath: \note\src\main\java\com\cloud\note\service\impl\CategoryServiceImpl.java
 */
package com.cloud.note.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cloud.note.dao.CategoryMapper;
import com.cloud.note.entity.Category;
import com.cloud.note.entity.Constant;
import com.cloud.note.service.CategoryService;
import com.cloud.note.utils.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private Constant constant;

    @Override
    public JSONObject createCategory(String mobile, String categoryName, String status) throws Exception {
        JSONObject res = new JSONObject();
        res.put("result", false);
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        if (categoryMapper.selectCount(queryWrapper.eq("user_mobile", mobile).eq("category_name", categoryName.trim())) != 0) {
            res.put("errorMsg", constant.getCATEGORY_EXIST_ERRORMSG()); 
            return res;
        }
        Category category = new Category();
        category.setUser_mobile(mobile);
        category.setStar("0");
        category.setCreate_date(StringUtil.getTimeHMS());
        category.setCategory_name(categoryName);
        category.setStatus(status);
        if(categoryMapper.insert(category)==1){
            res.put("result", true);
        }
        return res;
    }

    @Override
    public JSONObject updateCategory(String mobile, String categoryName, String status) throws Exception {
        JSONObject res= new JSONObject();
        res.put("result", false);
        Category category= new Category();
        category.setCategory_name(categoryName);
        category.setUser_mobile(mobile);
        category.setUpdate_date(StringUtil.getTimeHMS());
        categoryMapper.updateById(category);

        return null;
    }

    @Override
    public JSONObject getCategoryName(String mobile) throws Exception {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("category_name");
        List<Category> nameList=categoryMapper.selectList(queryWrapper);  
        List<String> list = new ArrayList<>();  
        if(!nameList.isEmpty()){
            nameList.forEach(item -> list.add(item.getCategory_name()));
        }
        JSONObject jsonObject=new JSONObject();
        int i=0;
        for(String name :list){
            jsonObject.put(String.valueOf(i++), name);
        }
        return jsonObject;
    }
} 
