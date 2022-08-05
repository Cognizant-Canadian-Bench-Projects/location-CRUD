package com.congnizant.locationCRUD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LocationCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocationCrudApplication.class, args);
	}

}
