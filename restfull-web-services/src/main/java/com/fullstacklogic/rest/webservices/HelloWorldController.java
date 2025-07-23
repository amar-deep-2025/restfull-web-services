package com.fullstacklogic.rest.webservices;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	private MessageSource messageSource;
	
	public HelloWorldController(MessageSource messageSource) {
		this.messageSource=messageSource;
	}

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
 	
 	@GetMapping(path="/hello-world-internationalized")
	public String helloWorldInternationalized() {
		Locale locale=LocaleContextHolder.getLocale();
		return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
//		return "Hello World V2";
	}
}
