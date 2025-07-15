package com.fullstacklogic.rest.webservices.user;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {
	
	private UserDaoService service;
	
	public UserResource(UserDaoService service) {
		this.service=service;
	}
	
	
	//Get All users
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return service.getAllUser();
	}
	
	
	//Get One User
	@GetMapping("/users/{id}")
	public User getOneUser(@PathVariable int id) {
		return service.findOne(id);
	}
	
	//create Post
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User savedUser=service.save(user);
		
		// /users/4 location header
		URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();     	
	
		return ResponseEntity.created(location).build();
	}
	 
	
	
}
