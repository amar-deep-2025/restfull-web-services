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
	
	@GetMapping(path="/person/header/", headers="X-API-VERSION=1")
	public PersonV1 getFirstVersionOfPersonRequesthaeader() {
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping(path="/person/header/", headers="X-API-VERSION=2")
	public PersonV2 getSecondVersionOfPersonRequest() {
		return new PersonV2(new Name("Boob", "Charlie"));
	}
	
	@GetMapping(path="/person/accept", produces="application/vnd.company.app-v1+json")
	public PersonV1 getFirstVersionOfPersonAcceptHeader() {
		return new PersonV1("Bob Charlie");
	}

	@GetMapping(path="/person/accept", produces="application/vnd.company.app-v2+json")
	public PersonV2 getSecondVersionOfPersonAcceptHeader() {
		return new PersonV2(new Name("Boob", "Charlie"));
	}

	
}
