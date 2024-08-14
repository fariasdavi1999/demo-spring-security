package com.demo.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

	@Autowired
	private final EncoderConfig encoder;
	@Autowired
	private final SecurityDatabaseConfig securityDatabaseConfig;

	public SecurityConfig(EncoderConfig encoder, SecurityDatabaseConfig securityDatabaseConfig) {
		this.encoder                = encoder;
		this.securityDatabaseConfig = securityDatabaseConfig;
	}

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(securityDatabaseConfig).passwordEncoder(encoder.passwordEncoder());
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
		    .authorizeHttpRequests(
				    authorization ->
						    authorization.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
						                 .requestMatchers("/").permitAll()
						                 .requestMatchers(HttpMethod.POST, "/login").permitAll()
						                 .requestMatchers("/getAll").hasAnyRole("ADMIN")
						                 .requestMatchers("/admins").hasAnyRole("ADMIN")
						                 .requestMatchers("/users").hasAnyRole("USER", "ADMIN")
						                 .anyRequest().authenticated())
		    .httpBasic(Customizer.withDefaults())
		    .sessionManagement(
				    httpSecuritySessionManagementConfigurer ->
						    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
								    SessionCreationPolicy.STATELESS));

		return http.build();
	}



	/*
	@Bean
	public UserDetailsService userDetailsService(BCryptPasswordEncoder passwordEncoder) {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("user")
		                       .password(passwordEncoder.encode("userPass"))
		                       .roles("USER")
		                       .build());

		manager.createUser(User.withUsername("admin")
		                       .password(passwordEncoder.encode("adminPass"))
		                       .roles("USER", "ADMIN")
		                       .build());
		return manager;
	}
	 */

}
