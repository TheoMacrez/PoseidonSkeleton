package com.nnk.springboot;

import com.nnk.springboot.domain.UserDomain;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application  {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}
}
