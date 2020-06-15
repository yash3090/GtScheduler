package com.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication
@ComponentScan("com.controllers")
public class FirstWebApp {
	
    public static void main(String[] args) {
        SpringApplication.run(FirstWebApp.class, args);
    }


}
