package com.fullstacklogic.rest.webservices.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties({"field1","field2"})
@JsonFilter("SomeBeanFilter")
public class SomeBean {
	
	private String value1;
	//@JsonIgnore
	private String value2;
	//@JsonIgnore
	private String value3;
	public SomeBean(String value1, String value2, String value3) {
		
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
	}
	public String getValue1() {
		return value1;
	}
	public String getValue2() {
		return value2;
	}
	public String getValue3() {
		return value3;
	}

	
	
}
