package com.zviproject.standalone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.zviproject" })
public class MyNetImportApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyNetImportApplication.class, args);
	}
}
