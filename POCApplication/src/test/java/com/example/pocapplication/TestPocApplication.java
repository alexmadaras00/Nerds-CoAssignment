package com.example.pocapplication;

import org.springframework.boot.SpringApplication;
import org.testcontainers.utility.TestcontainersConfiguration;

public class TestPocApplication {

	public static void main(String[] args) {
		SpringApplication.from(PocApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
