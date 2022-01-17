package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.service.MyService;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	
	@Autowired
	MyService service;
	
	public static void main(String[] args) {
		 SpringApplication.run(DemoApplication.class, args);
	}

	// gets called as soon as Spring container is created
	@Override
	public void run(String... args) throws Exception {
		service.doTask();
	}

}
