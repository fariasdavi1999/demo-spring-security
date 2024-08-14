package com.demo.spring.security.config;

import com.demo.spring.security.model.User;
import com.demo.spring.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SecurityDatabaseConfig implements UserDetailsService {
	@Autowired
	private final UserService userService;

	private SecurityDatabaseConfig(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		User userEntity = userService.findUserByUsername(username);
		if (userEntity == null) {
			throw new UsernameNotFoundException("User not found");
		}
		Set<GrantedAuthority> authorities = new HashSet<>();
		userEntity.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		});
		return new org.springframework.security.core.userdetails.User(userEntity.getUsername(),
		                                                              userEntity.getPassword(),
		                                                              authorities);
	}
}
