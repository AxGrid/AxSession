package com.axgrid.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@EnableAxSession
@Configuration
public class TestApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(TestApplication.class, args);
    }

}
