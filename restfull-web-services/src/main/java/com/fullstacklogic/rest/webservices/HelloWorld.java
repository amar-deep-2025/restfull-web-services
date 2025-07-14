package com.fullstacklogic.rest.webservices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

//	@RequestMapping(method=RequestMethod.GET, path = "hello-world")
	@GetMapping(path="hello-world-bean")
	public HelloWorldBean helloWorld() {
		return new HelloWorldBean("hello-world");
	}
	
    //GET/users
	@GetMapping(path="hello-world")
	public String helloWorldBean() {
		return "hello-world";
	}
	
	
	//Get/users/{name}
 	@GetMapping(path="hello-world-bean/path-variable/{name}")
	public HelloWorldBean helloWorld(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World %s", name));
	}
}
