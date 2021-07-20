package com.cloud.note;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cloud.note.dao")
public class NoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteApplication.class, args);
	}

}
