package com.api.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.api.user.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByEmail(String email);
	
	@Query("update user_services x set x.isvaliditate = true  where x.id = ?1")
    void findByEmailReturnStream(String email);
}

