/*
 * @Date: 2021-07-15 16:20:11
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-07-28 14:32:02
 * @FilePath: \note\src\main\java\com\cloud\note\NoteApplication.java
 */
package com.cloud.note;

import com.cloud.note.entity.Constant;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan("com.cloud.note.dao")
@EnableConfigurationProperties(Constant.class)
public class NoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteApplication.class, args);
	}

}
