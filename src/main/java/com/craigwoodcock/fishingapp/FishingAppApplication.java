package com.craigwoodcock.fishingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class FishingAppApplication {
    private static final Logger log = Logger.getLogger(FishingAppApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(FishingAppApplication.class, args);
    }
}
