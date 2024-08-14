package com.demo.spring.security.controller;

import com.demo.spring.security.model.User;
import com.demo.spring.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WelcomeController {

	@Autowired
	private final UserService userService;

	public WelcomeController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String welcome() {
		return "Welcome!";
	}

	@GetMapping("/getAll")
	public List<User> getAll() {
		return userService.getUsers();
	}

	@GetMapping("/users")
	public String welcomeUser() {
		return "Welcome User!";
	}

	@GetMapping("/admins")
	public String welcomeAdmin() {
		return "Welcome Admin!";
	}

}
