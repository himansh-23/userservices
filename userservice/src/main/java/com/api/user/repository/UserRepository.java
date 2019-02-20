package com.api.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.api.user.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByEmail(String email);
	
	@Query(value="select email from user_details where id IN (:id)",nativeQuery=true)
	Optional<List<String>> findEmailofUsers(@Param("id")List<Long> ids);
	
	
}

