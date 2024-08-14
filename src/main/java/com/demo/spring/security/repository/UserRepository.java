package com.demo.spring.security.repository;

import com.demo.spring.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("SELECT e FROM User e JOIN FETCH e.roles WHERE e.username= (:username)")
	User findUserByUsername(String username);
}
