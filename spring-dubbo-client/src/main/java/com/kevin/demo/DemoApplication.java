package com.kevin.demo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.HelloService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class DemoApplication {

	@Reference(version = "1.0.0")
  	private HelloService demoService;

	public static void main(String[] args) {
		
		SpringApplication.run(DemoApplication.class, args);
	}
	
    @PostConstruct
    public void init() {
    	String sayHello = demoService.sayHello("world");
    	System.err.println(sayHello);
    }
}
