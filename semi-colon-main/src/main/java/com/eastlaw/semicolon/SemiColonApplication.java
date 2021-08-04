package com.eastlaw.semicolon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.eastlaw.semicolon")
public class SemiColonApplication {
	public static void main(String[] args) {
		SpringApplication.run(SemiColonApplication.class, args);
	}
}
