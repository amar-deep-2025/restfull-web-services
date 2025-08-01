package com.fullstacklogic.rest.webservices.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class UserDaoService {

	private static List<User> users=new ArrayList<>();
	private static int userCount=0;
	static {
		users.add(new User(++userCount,"Varun","8974844894","varun@2018gmail.com",LocalDate.now().minusYears(5)));
		users.add(new User(++userCount,"Amar","8974847839","amar@2018gmail.com",LocalDate.now().minusYears(8)));
		users.add(new User(++userCount,"Dheeredra","8974849849","dheerendra@2018gmail.com",LocalDate.now().minusYears(10)));
		users.add(new User(++userCount,"Kajal","8974768738","kajal@2018gmail.com",LocalDate.now().minusYears(3)));
	}
	
	public List<User> getAllUser(){
		return users;
	}
	
	public User findOne(int id) {
		Predicate<? super User> predicate=user->user.getId().equals(id);
		return users.stream().filter(predicate).findFirst().orElse(null);
		
	}
	
	public void deleteById(int id) {
		Predicate<? super User> predicate =user->user.getId().equals(id);
		users.removeIf(predicate);
	}
	
	public User save(User user) {
		user.setId(++userCount);
		users.add(user);
		return user;
	}
	
	public Optional<User> findById(int id) {
	    return users.stream()
	                .filter(user -> user.getId() == id)
	                .findFirst();
	}


}
