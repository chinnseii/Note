package com.cloud.note.dao;

import com.cloud.note.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    // 新規登録
    int insert(User user);

    // ログイン
    User login(User user);

    // アカウント存在チェック
    int checkMobile(@Param("userMobile") String userMobile);

    // リセットロックフラグ
    int resetLock(@Param("userMobile") String userMobile);

    // ロックフラグ更新
    int updateLock(@Param("userMobile") String userMobile);

}
