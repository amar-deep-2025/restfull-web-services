package com.fullstacklogic.rest.webservices.jpa;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fullstacklogic.rest.webservices.user.Post;
import com.fullstacklogic.rest.webservices.user.User;

public interface PostRepository extends JpaRepository<Post, Integer> {

	
}

