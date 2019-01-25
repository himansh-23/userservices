package com.api.user.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import com.api.user.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByEmail(String email);
	
}

