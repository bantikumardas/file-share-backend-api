package com.fileshare.file_share_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FileShareBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileShareBackendApplication.class, args);
	}

}
