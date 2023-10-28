package com.gorki.gorkiSpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GorkiSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(GorkiSpringBootApplication.class, args);
	}

}
