
package com.cloud.note.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class StringUtil {

    /**
     * @description: 获取文件后缀
     * @param {String} fileName
     * @return {*}
     */
    public static String getFileType(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }

    /**
     * @description: 获取时间（年月日）
     * @param {*}
     * @return {*}
     */
    public static String getTimeToday() {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyyMMdd");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        return sdf.format(date);
    }

    /**
     * @description: 获取时间（年月日时分秒）
     * @param {*}
     * @return {*}
     */
    public static String getTimeHMS() {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyyMMddHHmmss");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        return sdf.format(date);
    }
}
