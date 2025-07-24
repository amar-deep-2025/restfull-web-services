package com.fullstacklogic.rest.webservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {

	@GetMapping("/v1/person")
	public PersonV1 getFirstVersionOfPerson() {
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping("/v2/person")
	public PersonV2 getSecondVersionOffPerson() {
		return new PersonV2(new Name("Boob", "Charlie"));
	}
	
	@GetMapping(path="/person", params="version=1")
	public PersonV1 getFirstVersionOfPersonRequestParamter() {
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping(path="/person", params="version=2")
	public PersonV2 getSecondVersionOfPersonRequestParamter() {
		return new PersonV2(new Name("Boob", "Charlie"));
	}
}
