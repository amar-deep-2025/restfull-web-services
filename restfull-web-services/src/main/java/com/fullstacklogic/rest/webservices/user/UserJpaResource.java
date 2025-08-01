package com.fullstacklogic.rest.webservices.user;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fullstacklogic.rest.webservices.jpa.PostRepository;
import com.fullstacklogic.rest.webservices.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {

	private UserRepository userRepo;

	private PostRepository postRepo;

	@Autowired
	public UserJpaResource(UserRepository userRepo, PostRepository postRepo) {
		this.userRepo = userRepo;
		this.postRepo = postRepo;
	}

	// Get all users
	@GetMapping("jpa/users")
	public List<User> retrieveAllUsers() {
//		return service.getAllUser();
		return userRepo.findAll();
	}

	// Get one user
	@GetMapping("jpa/users/{id}")
	public EntityModel<User> getOneUser(@PathVariable int id) {
		Optional<User> user = userRepo.findById(id);
		if (user.isEmpty()) {
			throw new UserNotFoundException("id:" + id);
		}
		EntityModel<User> entityModel = EntityModel.of(user.get());
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		entityModel.add(link.withRel("all-users"));
		return entityModel;
	}

	// Create user
	@PostMapping("jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepo.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).body(savedUser);

	}

	// Delete user

	@DeleteMapping("jpa/users/{id}")
	public EntityModel<ResponseEntity<Void>> deleteUserById(@PathVariable int id) {
		userRepo.deleteById(id);
		// Build HATEOAS links
		EntityModel<ResponseEntity<Void>> resource = EntityModel.of(ResponseEntity.noContent().build());

		resource.add(linkTo(methodOn(UserJpaResource.class).retrieveAllUsers()).withRel("all-users"));
		resource.add(linkTo(methodOn(UserJpaResource.class).createUser(null)).withRel("create-user"));
		return resource;
	}

	// update User
	@PutMapping("jpa/users/{id}")
	public ResponseEntity<User> updateUser(@Valid @PathVariable int id, @RequestBody User updatedUser) {
		Optional<User> existingUserOptional = userRepo.findById(id);

		if (!existingUserOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		User existingUser = existingUserOptional.get();

		// Update only the fields (not the ID)
		existingUser.setName(updatedUser.getName());
		existingUser.setPhoneNo(updatedUser.getPhoneNo());
		existingUser.setMailId(updatedUser.getMailId());
		existingUser.setBirthDate(updatedUser.getBirthDate());

		userRepo.save(existingUser); // Save updated user (if using list, no-op or replace in list)
		System.out.println("Updated successfully");
		return ResponseEntity.ok(existingUser);

	}

	@GetMapping("jpa/users/{id}/posts")
	public List<Post> retrievePostsForUser(@PathVariable int id) {
		Optional<User> user = userRepo.findById(id);

		if (user.isEmpty()) {
			throw new UserNotFoundException("id :" + id);
		}
		return user.get().getPosts();
	}

	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post){
		
		Optional<User> user=userRepo.findById(id);
		
		if (user.isEmpty()) {
			throw new UserNotFoundException("id :"+id);
		}
		
		post.setUser(user.get());
		Post savedPost=postRepo.save(post);
		
		URI location=ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedPost.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/jpa/users/{userId}/posts/{postId}")
	public ResponseEntity<Post> retrivePostForUser(@PathVariable int userId,@PathVariable int postId){
		
		Optional<User> user=userRepo.findById(userId);
		
		if (user.isEmpty()) {
			throw new UserNotFoundException("userId :"+userId);
		}
		Optional<Post> post=postRepo.findById(postId);
		
		if (post.isEmpty()) {
			throw new PostNotFoundException("PostId :"+postId);
		}
		
		if (!post.get().getUser().getId().equals(userId)) {
			throw new PostNotFoundException("PostId "+postId+" "+"UserId :"+userId);
		}
		
		return ResponseEntity.ok(post.get());
	}
	
}
