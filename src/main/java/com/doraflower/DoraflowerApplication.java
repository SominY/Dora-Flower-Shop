package com.doraflower;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class DoraflowerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoraflowerApplication.class, args);
	}

}
