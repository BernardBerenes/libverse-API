package com.libverse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LibverseApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibverseApplication.class, args);
	}

}
