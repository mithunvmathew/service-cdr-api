package com.mvm.servicecdrapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ServiceCdrApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceCdrApiApplication.class, args);
	}

}
