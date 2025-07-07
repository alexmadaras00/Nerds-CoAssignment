package com.example.ssoprovider;

import org.springframework.boot.SpringApplication;

public class TestSsoProviderApplication {

    public static void main(String[] args) {
        SpringApplication.from(SsoProviderApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
