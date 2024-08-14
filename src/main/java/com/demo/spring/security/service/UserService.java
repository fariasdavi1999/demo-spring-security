package com.demo.spring.security.service;

import com.demo.spring.security.model.User;
import com.demo.spring.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
	@Autowired
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void saveUser(User user) {
		userRepository.save(user);
	}

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public User findUserByUsername(String username) {

		return userRepository.findUserByUsername(username);
	}
}
