package com.demo.spring.security.initapp;

import com.demo.spring.security.model.User;
import com.demo.spring.security.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class OnStartApplication implements CommandLineRunner {
	/*
		OnStartApplication
		This class is responsible for initializing the application data, such as creating admin and user users.
	 */
	@Autowired
	private final BCryptPasswordEncoder passwordEncoder;
	Logger logger = Logger.getLogger(String.valueOf(OnStartApplication.class));

	@Autowired
	private final UserService userService;

	public OnStartApplication(BCryptPasswordEncoder passwordEncoder, UserService userService) {
		this.passwordEncoder = passwordEncoder;
		this.userService     = userService;
	}

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		logger.info("STARTING APPLICATION");

		User user = userService.findUserByUsername("ADMIN");
		if (user == null) {
			User newUser = new User();
			newUser.setName("adm");
			newUser.setUsername("admin");
			newUser.setPassword(passwordEncoder.encode("admin123"));
			newUser.getRoles().add("ADMIN");
			userService.saveUser(newUser);
		}
		user = userService.findUserByUsername("USER");
		if (user == null) {
			User newUser = new User();
			newUser.setName("usr");
			newUser.setUsername("user");
			newUser.setPassword(passwordEncoder.encode("user123"));
			newUser.getRoles().add("USER");
			userService.saveUser(newUser);
		}
	}
}
