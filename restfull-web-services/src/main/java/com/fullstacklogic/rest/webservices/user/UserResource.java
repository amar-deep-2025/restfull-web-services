package com.fullstacklogic.rest.webservices.user;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResource {

	private final UserDaoService service;
	

	
	

	@Autowired
	public UserResource(UserDaoService service) {
		this.service = service;
	}

	// Get all users
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return service.getAllUser();
	}

	// Get one user
	@GetMapping("/users/{id}")
	public User getOneUser(@PathVariable int id) {
		User user = service.findOne(id);
		if (user == null) {
			throw new UserNotFoundException("id:" + id);
		}
		return user;
	}

	// Create user
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).body(savedUser);

	}

	// Delete user
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUserById(@PathVariable int id) {
		User user = service.findOne(id);
		if (user == null) {
			throw new UserNotFoundException("id:" + id);
		}
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	//update User
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@Valid @PathVariable int id, @RequestBody User updatedUser) {
	    Optional<User> existingUserOptional = service.findById(id);

	    if (!existingUserOptional.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }

	    User existingUser = existingUserOptional.get();

	    // Update only the fields (not the ID)
	    existingUser.setName(updatedUser.getName());
	    existingUser.setPhoneNo(updatedUser.getPhoneNo());
	    existingUser.setMailId(updatedUser.getMailId());
	    existingUser.setBirthDate(updatedUser.getBirthDate());

	    service.save(existingUser); // Save updated user (if using list, no-op or replace in list)

	    return ResponseEntity.ok(existingUser);
	}
	
	

	
}
