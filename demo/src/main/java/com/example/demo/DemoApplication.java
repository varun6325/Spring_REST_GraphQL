package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.demo.service.MyService;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
		
		MyService service = ctx.getBean("myService", MyService.class);
		service.doTask();
		
		System.out.println("********");
		
		String[] names = ctx.getBeanDefinitionNames();
		for(String name : names) {
			System.out.println(name);
		}
	}

}
