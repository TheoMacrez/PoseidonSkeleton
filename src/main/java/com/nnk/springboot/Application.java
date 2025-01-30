package com.nnk.springboot;

import com.nnk.springboot.domain.UserDomain;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application /*implements CommandLineRunner*/ {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);


	}
//	@Override
//	public void run(String... args) throws Exception {
//		UserDomain newUser = new UserDomain();
//		UserDomain newAdminUser = new UserDomain();
//
//		newUser.setUsername("newUser");
//		newAdminUser.setUsername("newAdminUser");
//
//		newUser.setPassword("userPassword");
//		newAdminUser.setPassword("userAdminPassword");
//
//		newUser.setFullname("User");
//		newAdminUser.setFullname("Administrator");
//
//		newUser.setRole("USER");
//		newAdminUser.setRole("ADMIN");
//
//		userService.saveUser(newUser); // Assurez-vous que cette méthode existe
//		userService.saveUser(newAdminUser); // Assurez-vous que cette méthode existe
//	}
}
