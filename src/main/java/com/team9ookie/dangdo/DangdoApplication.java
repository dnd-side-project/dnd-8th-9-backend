package com.team9ookie.dangdo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DangdoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DangdoApplication.class, args);
	}

}
