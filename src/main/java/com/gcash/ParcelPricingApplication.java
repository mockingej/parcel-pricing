package com.gcash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ParcelPricingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParcelPricingApplication.class, args);
    }

}
