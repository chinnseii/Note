/*
 * @Date: 2021-09-02 17:28:42
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-09-06 13:34:54
 * @FilePath: \note\src\main\java\com\cloud\note\service\impl\LevelMasterServiceImlp.java
 */
package com.cloud.note.service.impl;

import com.cloud.note.dao.LevelMasterMapper;
import com.cloud.note.service.LevelMasterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LevelMasterServiceImlp implements LevelMasterService {
@Autowired
private LevelMasterMapper levelMasterMapper;

    @Override
    public Double getExp(String level) {
        return Double.valueOf(levelMasterMapper.selectById(Integer.valueOf(level)+1).getExp());
    }

    @Override
    public void levelUp(String exp, String level) {
        // TODO Auto-generated method stub
        
    }
    
}
