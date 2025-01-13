package com.example.memstagram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MemstagramApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemstagramApplication.class, args);
	}

}
