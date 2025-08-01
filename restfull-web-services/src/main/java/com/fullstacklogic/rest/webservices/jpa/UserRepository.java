package com.fullstacklogic.rest.webservices.jpa;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fullstacklogic.rest.webservices.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	void deleteById(Optional<User> user_id);

}

