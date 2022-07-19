package com.bs.helloaop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;




@SpringBootApplication
public class HelloAopApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloAopApplication.class, args);
	}

}
