package com.genius.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Sentinel7001Application {

    public static void main(String[] args) {
        SpringApplication.run(Sentinel7001Application.class, args);
    }

}
