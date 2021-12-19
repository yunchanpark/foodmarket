package com.lec.foodmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FoodmarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodmarketApplication.class, args);
	}

}
