package com.healthhalo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class HealthHaloApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthHaloApplication.class, args);
	}

}
