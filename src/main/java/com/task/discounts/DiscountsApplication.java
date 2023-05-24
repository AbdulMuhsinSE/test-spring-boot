package com.task.discounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class DiscountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscountsApplication.class, args);
	}

}
